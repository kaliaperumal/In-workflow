package com.xsltconversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;

import com.database.DatabaseConfig;
import com.database.SendMAil;


public class XSLTConversion {
	
	static String 	proofs_uploadPath;
	static String 	localUploadPath;
	static String  Downloadpicktime;
	static String 	server;
	static String 	user;
	static String 	pass;
	static String 	rootdir;
	static String 	backupPath;
	static String 	XSLTPath;
	static String 	iniPath;	
	static private String stage="XSLTConversion";	
	static int 	port;
	static int 	supplierId=1;
	
	static DatabaseConfig dbconfig	=	new DatabaseConfig();
	static SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy kk:mm:ss.sss");
	
	public static void main(String args[]) throws InterruptedException {		
		conversion();
	}

	private static void conversion() throws InterruptedException {	
		
		System.out.println("==========================================");
		System.out.println("XSLT Conversion Process running...........");
		System.out.println("==========================================");
		
		/* Get Details form ini file */
		getIniDetails();		
		
		/* Get Data set values from Checkpoint table, if value match 2 */		
		dbconfig.getID(2,stage);	
		
		Thread.sleep(7000);
		
		conversion();
	}
	
	/* Read ini file and get details */
	private static void getIniDetails() {
		
		Properties properties=new Properties();		
		try {
			properties.load(new FileInputStream(new File("path.ini")));
		} catch (IOException e) {
			System.out.println("Exception in reading ini file : "+e);			
		}		
		proofs_uploadPath	=	properties.getProperty("proofs_uploadPath");
		localUploadPath		=	properties.getProperty("Local_uploadPath");
		server				=	properties.getProperty("FTP-HOST-Server");
		user				=	properties.getProperty("FTPUser");
		pass				=	properties.getProperty("FTPPassword");
		rootdir				=	properties.getProperty("FTProotdir");
		backupPath			=	properties.getProperty("BackupPath");
		XSLTPath			=	properties.getProperty("XSLTPath");
		iniPath				=	properties.getProperty("iniPath");
		port				=	Integer.parseInt(properties.getProperty("FTPPort").toString());		
	}
	
	
	public void mainConversion(String journalId, String articleId, 
								 String dataset, String zipfileName, String MetaXMLName) throws InterruptedException {
		
		boolean conversion_status = xml2htmlConversion(journalId+articleId+"_id.xml", dataset);
		
		if(conversion_status) {	
			
			/* Folder configuration in proof folder */					
			boolean proofStatus=proofFolderConfiguration(dataset, journalId, articleId, zipfileName, MetaXMLName); 
						
			/* Proof folder configured successfully and then generate token */
			if(proofStatus) {		
			
				HashMap<String,String> map=new HashMap<String, String>();				
				/* Token generate based on journal id and article id */	
				HashMap valueMap=tokenGenerate(journalId, articleId, dataset, zipfileName,map);
			
				String token_status=valueMap.get("status").toString();
			
				if(token_status.equals("true")) {
				
					String token=valueMap.get("token").toString();
					
					System.out.println("\nToken :: "+token);
				
					boolean status=dbconfig.insertHistroyTable(dataset, "success", ""+sd.format(new Date()), stage);
				
					if(status) {
						
						dbconfig.deleteCheckpointTable(dataset);
						
						dbconfig.getDatasetstatusTable(dataset, token);
						
						SendMAil sendmailobj1 = new SendMAil();
						
						sendmailobj1.mailcall(journalId, articleId, token, "success");
					}					
				}
			}
		}
				
	}
	
	private boolean proofFolderConfiguration(String dataset, String journalId,
			String articleId, String zipfileName, String metaXMLName) {
		
		boolean proofStatus=true;
		
		String message="";
		
		try {
			File file1=new File(proofs_uploadPath+journalId);
			if(!file1.exists()) {
				file1.mkdir();
			}

			File file2=new File(proofs_uploadPath+journalId+"/"+articleId);
			if(!file2.exists()) {
				file2.mkdir();
			}	
			File srcDir=new File(localUploadPath+dataset);
			File destDir=new File(proofs_uploadPath+journalId+"/"+articleId);											
			File srcFile=new File(localUploadPath+metaXMLName);											
			File srcFile1=new File(localUploadPath+zipfileName);									
			file1.setWritable(true, false);
			file2.setWritable(true, false);
		
			boolean createDestDir=false;				
			try{			
				FileUtils.moveDirectoryToDirectory(srcDir, destDir, createDestDir);  //move extracted folder
				FileUtils.moveFileToDirectory(srcFile, destDir, createDestDir);   	 //Move Meta XML file move
				FileUtils.moveFileToDirectory(srcFile1, destDir,createDestDir);      //Move dataset file			
			} catch(Exception ex1) {
			
				proofStatus=false;			
				System.out.println("\nProof File and Folder Copy exceptions :"+ex1);			        	
				//Update error message in database dataset table
				message=""+ex1;		   		   
				boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);			
				if(status) {
					dbconfig.deleteCheckpointTable(dataset);
				}			
			}	
		} catch (Exception e1) {
			proofStatus=false;
			message=""+e1;		   	
			System.out.println("Proof File and Folder Copy exceptions :"+e1);
			boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);
			
			if(status) {
				dbconfig.deleteCheckpointTable(dataset);
			}
	}		
	return proofStatus;		
	}

	private HashMap tokenGenerate(String journalId, String articleId,
			String dataset, String zipfileName, HashMap map) {

		String message="";
		StringBuffer sb = new StringBuffer();
		String original=journalId+articleId;
		
		if(original.trim().length()==0) {			
			System.out.println("\nToken Pass Length is zero ");
			//Update error message in database dataset table
			message="Token Pass (Jouranl id + article id) Length is zero";			
			map.put("token", "Failure");
			map.put("status", "false");
			boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);			
			if(status) {
				dbconfig.deleteCheckpointTable(dataset);
			}
		}
		else
		{		
			try {
				
			MessageDigest md = null;			
			md = MessageDigest.getInstance("MD5");			
			md.update(original.getBytes());
			byte[] digest = md.digest();			
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}			
			System.out.println("\nToken Pass		::: "+original +" ==  "+ "MD5	::: "+sb.toString());
			map.put("token", sb.toString());
			map.put("status", "true");
		     
			} catch (Exception e) {			
				System.out.println("Exception in Token Creation (MD5Token.j	ava): "+e);	
				message="Exception in Token Creation :: "+e;				
				boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);			
				map.put("token", "Failure");
				map.put("status", "false");
				if(status) {
					dbconfig.deleteCheckpointTable(dataset);
				}
			}
		}
		//return sb.toString();
		return map;
	}

	private boolean xml2htmlConversion(String idXML, String dataset) {
		
		String message="";
		
		boolean conversion_result=true;
		
		try {
			
			String new_localUploadPath=localUploadPath+dataset+"/";			   
		   
			String filename1=idXML.replace("_id.xml",".html");	   
			
			System.setProperty("javax.xml.transform.TransformerFactory","net.sf.saxon.TransformerFactoryImpl");
		   

			conversion_result=myTransformer (new_localUploadPath+idXML, XSLTPath,localUploadPath+dataset+"/"+filename1, dataset, conversion_result);

		   
		   	} catch (Exception ex) {				   
		   		
		   	   conversion_result=false;
		   	   System.out.println("EXCEPTION IN HTML Conversion: " + ex);
		   	   
			   //Update error message in database dataset table
			   message=""+ex;			   		   
			   boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);
				
				if(status) {
					dbconfig.deleteCheckpointTable(dataset);
				}			 		   
		   }
		   return conversion_result;
	}
	
	 public static boolean myTransformer (String sourceID, String xslID,String htmlFile, String dataset, boolean conversion_result)				 
	 {		   
		   
		 	File xsltFile=new File(xslID);
		 	File htmlFile1=new File(htmlFile);
		 	if(!xsltFile.exists())
		 	{
		 		conversion_result=false;
		 		String message="XSLT file missing in following path :"+xslID;			   		   
	 			boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);
	 			
	 			if(status) {
	 				dbconfig.deleteCheckpointTable(dataset);
	 			}
		 		
		 	} else {
		 		
		 		try{ 
		 			TransformerFactory tfactory = TransformerFactory.newInstance();
		 			Transformer transformer = tfactory.newTransformer(new StreamSource(new File(xslID)));
		 			transformer.transform(new StreamSource(new File(sourceID)),new StreamResult(new File(htmlFile)));
		 		} catch(Exception e) {
		 			System.out.println("Exception in xx :"+dataset);
		 			conversion_result=false;
		 			//Update error message in database dataset table
		 			String message=""+e;			   		   
		 			boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);
		 			
		 			if(status) {
		 				dbconfig.deleteCheckpointTable(dataset);
		 			}
		 		}
		 	}
		 	return conversion_result;
		}

	/* Initialize article table values insert */
	public void initiateArticleTableValues(String dataset, int datasetStatusId, String jid,
			String aid, String articletitle, String downloadTime, String token) {
		
		    String filePath=proofs_uploadPath+jid+"/"+aid+"/"+dataset+"/"+jid+aid+".html";
		
		   dbconfig.insertArticleTableValues(datasetStatusId, jid,
					aid, articletitle, downloadTime, token);
		   
		   dbconfig.getArticleTablePrimaryKey(dataset, datasetStatusId, jid,
					aid, articletitle, downloadTime, token, filePath);		   
		
	}	 
}

