package com.sequence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.database.DatabaseConfig;

public class SequenceId {

	static String 	proofs_uploadPath;
	static String 	localUploadPath;
	static String  Downloadpicktime;
	static String 	server;
	static String 	user;
	static String 	pass;
	static String 	rootdir;
	static String 	backupPath;
	static String message;
	static String 	XSLTPath;
	static String 	DTDPath;
	static String 	iniPath;	
	static String stage="SequenceID";	
	static int 	port;
	static int 	supplierId=1;
	
	static DatabaseConfig dbconfig	=	new DatabaseConfig();	
	static SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy kk:mm:ss.sss");
	
	public static void main(String[] args) throws InterruptedException {		
		IDInsert();
	}

	private static void IDInsert() throws InterruptedException {	
		
		System.out.println("\n==============================================");
		System.out.println("ID Insert Process Running");
		System.out.println("==============================================\n");
		/* Get Details form ini file */
		getIniDetails();	
		
		/* Get Data set values from Checkpoint table , if value is one (1)* and set current stage 
		 * 				is sequence id or FTP or XSLT conversion		
		 */
		dbconfig.getID(1,"SequenceID");
		
		Thread.sleep(10000);
		
		IDInsert();
	}

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
		DTDPath				=	properties.getProperty("DTDPath");
		iniPath				=	properties.getProperty("iniPath");
		port				=	Integer.parseInt(properties.getProperty("FTPPort").toString());
	}
	
	public void getDatasetID(String journalId, 
			String articleId, String dataset, String zipfileName) {	
		
		boolean token_result=true;
		boolean id_result=true;
			
		
		/* XML ID and Span tag replacement using java */
		try {
		
			id_result=xmlIdInsertion(journalId, articleId, dataset, zipfileName);
		
		}catch (Exception e) {			
			
			System.out.println("\nException in XML ID Insertion :"+e);
			
			message="Exception in XML ID Insertion : "+e;		
			id_result=false;			
			boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);			
			if(status) {
				dbconfig.deleteCheckpointTable(dataset);
			}
		}	
		
		/* update checkpoint table status value if process failure or success - both update*/
		dbconfig.setStatus(dataset, 2);
		
		if(token_result && id_result) {	
			
			dbconfig.insertHistroyTable(dataset, "success", ""+sd.format(new Date()), stage);
		}
		
	}

	private static String tokenGenerate(String journalId, String articleId, 
					String dataset, String zipfileName) {
		
		String message="";
		String original=journalId+articleId;
		
		if(original.trim().length()==0) {			
			System.out.println("Token Pass Length is zero ");
			//Update error message in database dataset table
			message="Token Pass (Jouranl id + article id) Length is zero";				
		}		
		StringBuffer sb = new StringBuffer();
		
		try {
			MessageDigest md = null;			
			md = MessageDigest.getInstance("MD5");			
			md.update(original.getBytes());
			byte[] digest = md.digest();			
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}			
			System.out.println("\n\nToken Pass		::: "+original +" ==  "+ "MD5	::: "+sb.toString());			
		     
		} catch (Exception e) {			
			System.out.println("Exception in Token Creation (MD5Token.java): "+e);	
			message="Exception in Token Creation :: "+e;			
					
			boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);
			
			if(status) {
				dbconfig.deleteCheckpointTable(dataset);
			}
		}
		return sb.toString();
	}
	
	
	/* XML ID Insertion and Span Tag Replacement */
	
	public static boolean xmlIdInsertion(String journalId, String articleId, String dataset, String zipfileName)	{
		
		boolean id_result=true; 	
		
		String message="";
		try { 
			
			File f1 = new File(localUploadPath+dataset+"/");
			
			if(f1.exists()) { 	
			
			File[] f2 = f1.listFiles();
			for (int i = 0; i < f2.length; i++) {
			
				if (f2[i].isFile()) {
				
				 if(f2[i].getName().toString().equalsIgnoreCase(journalId+articleId+".xml")) {				 
					 
					 String filename = "", line = "";
					 filename = f2[i].getName();
					 int count = 0;
					 					 
					 if (filename.indexOf("_id") == -1	&& filename.toLowerCase().endsWith(".xml")) {
						 String index = filename.substring(0, filename.lastIndexOf(".xml"));
						 String out_filename = filename.replaceAll("(.xml|.XML)","_id.xml");						 
						 BufferedReader br = new BufferedReader(new FileReader(localUploadPath+dataset+"/"+filename));
						 PrintWriter pw = new PrintWriter(new FileWriter(localUploadPath+dataset+"/"+out_filename));
						 StringBuffer title_buffer=new StringBuffer();
						 while ((line = br.readLine()) != null) {	 
							 
							 title_buffer=title_buffer.append(line);
							
							 
							 // xlink:href="http://arxiv.org/abs/astro&hyphen;ph/9708014"						
							 if(line.indexOf("<!DOCTYPE article")!=-1)
							 {								 
								 String dtdline=dtdReplacement(line,journalId, iniPath, journalId, articleId, dataset);
								 line=line.replaceAll("<!DOCTYPE article.+?\\.dtd\">",dtdline);
							 }				
						
							 Pattern pat4 = Pattern.compile("\\s*xmlns\\:xlink=\"([^\"]+?)\"");
							 Matcher mat4 = pat4.matcher(line);
							 while (mat4.find()) {
								 String s9 = "";
								 s9 = mat4.group();
								 if (s9.indexOf("<") == -1 || s9.indexOf(">") == -1) {
									 line = line.replace(s9, "");
								 }
							 }
							 
							 Pattern pat1 = Pattern.compile("(\\<[A-z][^>]*?)(\\/\\>|\\>)");
							 Matcher mat1 = pat1.matcher(line);
							 while (mat1.find()) {
								 count++;
								 String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";
								 s1 = mat1.group();
								 s2 = mat1.group();
								 s3 = mat1.group(1);
								 s5 = mat1.group(2);
								 s3 = s3.replaceAll("(.+)", "$1 name=\"TRS_ID_"+ count + "\"");
								 s4 = mat1.group(1);
								 s1 = s1.replace(s4, s3);
								 line = line.replace(s2, s1);
							 }

							 if (line.indexOf("<ext-link") != -1) {
								 line = line.replaceAll("(<ext-link.+?\"\\>.+?)(\\&[A-z]{1,7}\\;)(.+?</ext-link>)",
											"$1<span class=\"unicode-char\">$2</span>$3");
							 } else {
								 Pattern pat2 = Pattern.compile("(\\&[A-z]{1,7}\\;)");
								 Matcher mat2 = pat2.matcher(line);
								 if (mat2.find()) {
									 String s6 = "", s7 = "";
									 s6 = mat2.group();
									 s7 = mat2.group();
									 	if (s6.indexOf("<") == -1|| s6.indexOf("<") == -1) {
									 		s6 = s6.replace(s6,"<span class=\"unicode-char\">"+ s6 + "</span>");
									 		line = line.replace(s7, s6);
									 	}
								 }
							 }
						pw.println(line);
						 }
					pw.close();
					br.close();
					
					try {
					if(title_buffer.toString().length()!=0 ||title_buffer.toString()!=null) {
						
						 Pattern pn1=Pattern.compile("<article-meta.+?</article-meta>");
			    		 Matcher mn1=pn1.matcher(title_buffer.toString());
			    		 if(mn1.find()) {
			    			 
			    			 Pattern pn2=Pattern.compile("<article-title(.*?)>(.+?)</article-title>");
				    		 Matcher mn2=pn2.matcher(mn1.group());
				    		 
				    		 while(mn2.find()) {
				    			 System.out.println("Matcher Main  :: "+mn2.group(2));
				    			 String title=mn2.group(2).toString().trim();			    			 
				    			 dbconfig.insertTitle(dataset,title);
				    			 title_buffer=new StringBuffer();
				    		 }
			    			 
			    		 }
					}
					} catch(Exception ex12) {
						 title_buffer=new StringBuffer();
						 System.out.println("Exception in get Article title :"+ex12);
					}
				}
			  }
			}
		}
		} else {
			message="Dataset folder not available in Local folder";
			id_result=false;
			
			System.out.println(message);
			
			boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);
			
			if(status) {
				dbconfig.deleteCheckpointTable(dataset);
			}
		}			
		} catch(Exception e) {
			
			System.out.println("XML Sequence Number Insertion Error :"+e);	
			//Update error message in database dataset table
			message="XML Sequence Number Insertion Error :	"+e;
			id_result=false;
			
			boolean status=dbconfig.insertHistroyTable(dataset, message, ""+sd.format(new Date()), stage);
			
			if(status) {
				dbconfig.deleteCheckpointTable(dataset);
			}
			
		}
		return id_result;
	}
	
	public static String dtdReplacement(String line, String journalDTDType, String iniPath,
			String journalId, String articleId, String dataset) throws FileNotFoundException, IOException
	{					
			String s1="";		
			System.out.println("DTD Replacement Match	:::	"+line);							
			
			Pattern pat3=Pattern.compile("(.+\\s+\")(.+?\\.dtd\">)");
			Matcher mat3=pat3.matcher(line);
			if (mat3.find()) {
				String s2="",s3="";			
				s1=mat3.group();
				s2=mat3.group(1);
				s3=mat3.group(1);			
			
				if (journalDTDType.equalsIgnoreCase("epl"))	{
					
					/* DTD Path Define */					
					String DTDPath1=DTDPath+journalId+"/";					
					s3=s3.replaceAll("(.+)", "$1"+DTDPath1);
					s1=s1.replace(s2, s3);
					System.out.println("DTD Replaced  Match	:::	"+s1);
				} else {
					s1=s1.replace("(.*)", "<!--$1-->");
				}			
			}
				
		return s1;
	}
}