package com.ftpdownload;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Zip Extract support class */
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;





/* FTP third party API */
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;











/* Database insert and retrieve */
import com.database.DatabaseConfig;
import com.database.SendMAil;

public class FtpDownload {	
	static String 	proofs_uploadPath;
	static String 	localUploadPath;
	static String Downloadpicktime;
	static String 	server;
	static String 	user;
	static String 	pass;
	static String 	rootdir;
	static String 	backupPath;
	static String 	XSLTPath;
	static String 	iniPath;	
	static String  stage="FTP";
	
	static int 	port;
	static int 	supplierId=1;
	static FTPClient ftpClient 		= 	new FTPClient();
	static DatabaseConfig dbconfig	=	new DatabaseConfig();
	static SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy kk:mm:ss.sss");	
	
	public static void main(String[] args) throws InterruptedException {	
		     inWorkflow();		  	
	}

	private static void inWorkflow() throws InterruptedException {
		
		System.out.println("FTP InWorkflow Run..........\n");
		
		/* Get Details form ini file */
		getIniDetails();
	   
	   /* FTP Connection establishment*/
	   boolean status = false;
	   try {
		   status = ftpConnect(ftpClient);
	   } catch (Exception e) {
		   System.out.println("Exception in FTP Connection :: "+e);
		   e.printStackTrace();
	   }
	   
	   Thread.sleep(9000);
	   
	   /* If ftp connected success */
	   if(status) {		   
		    /* Download meta XML file from ftp */			   
		   downloadMetaXML();		   
	   }		
	}

	/* Download meta XML file from ftp */
	private static void downloadMetaXML() throws InterruptedException {
		
		
	try {
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);		
		ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		FTPFile[] files = ftpClient.listFiles(rootdir);
		
		for (FTPFile file : files) {
			
			if (file.isFile()) {			
				
				Downloadpicktime=sd.format(new Date());
				String fileName = file.getName();
				
				System.out.println("FTP FileNames		::: "+fileName);
				
				if (fileName.toLowerCase().endsWith(".xml")) {
					
					boolean metaXMLSuccess=downloadFTPFile(fileName);
					
					/* If meta xml file download success */
					if(metaXMLSuccess) {
						
						System.out.println("\nMeta XML download completed :: "+fileName+"\n");
						
						HashMap<String, String> metaxml_map=new HashMap<String,String>();
						
						metaxml_map	=	metaXMLInfo(fileName);
						
						String 	metaxml_journalid="", metaxml_articleid="", metaxml_dataset="", metaxml_zipfilename="";
						
						metaxml_journalid	=	metaxml_map.get("jid").toString().trim();
						metaxml_articleid	=	metaxml_map.get("artid").toString().trim();
						metaxml_dataset		=	metaxml_map.get("dataset").toString().trim();
						metaxml_zipfilename	=	metaxml_map.get("filename").toString().trim();
						
						System.out.println("\nJournal ID		:: " +metaxml_journalid);
						System.out.println("Article ID		:: " +metaxml_articleid);
						System.out.println("Data Set		:: " +metaxml_dataset);
						System.out.println("Zip Filename	:: " +metaxml_zipfilename+"\n");					
						
						/* Download zip file based on meta xml dataset */
						if(!metaxml_zipfilename.toLowerCase().endsWith(".zip")) {
							
							metaxml_zipfilename=metaxml_zipfilename+".zip";
						}						
						
						boolean zipsuccess=downloadFTPFile(metaxml_zipfilename);
						
						/* If zip file download success */
						if(zipsuccess) {
							System.out.println("\nZip file download completed :: "+metaxml_zipfilename+"\n");
											
							
							boolean isMetaXMLBackup=fileBackup(localUploadPath+fileName,fileName);							
							
							boolean isZipBackup=fileBackup(localUploadPath+metaxml_zipfilename,metaxml_zipfilename);
							
							/* If backup file taken success */
							if(isMetaXMLBackup && isZipBackup) {
								
								System.out.println("Backup completed :: "+metaxml_zipfilename +" and "+fileName);
								
								
								/* Data set Status table value insert */
								boolean insertdb_check = dbconfig.insertDataSetStatusRecords(supplierId, metaxml_dataset, "ReadyFound", 
										Downloadpicktime, sd.format(new Date()), sd.format(new Date()), 
										"success",metaxml_journalid, metaxml_articleid, fileName);
								
								if(insertdb_check)
								{		
									System.out.println("Dataset Status table values inserted successfully :: "+fileName);
									
									boolean unzip_result=unZip(metaxml_journalid, metaxml_articleid, metaxml_dataset, 
											metaxml_zipfilename, localUploadPath);						
								
								/* If unzip success */
								if(unzip_result) {									
									System.out.println("Unzip completed 	::"+metaxml_zipfilename);									
									
									HashMap<String, String> valueMap=new HashMap<String,String>();
									valueMap=tokenGenerate(metaxml_journalid, metaxml_articleid);
									
									String token_status=valueMap.get("status").toString();									
									
									/* If token generated successfully further steps run*/
									if(token_status.equals("true")) {										
										String token=valueMap.get("token").toString();
										
										
										/* Check supplied Journal is eligible to process */
										boolean checkJournalExists = dbconfig.checkSuppliedJournal(metaxml_journalid);
										
										if(!checkJournalExists) {						
											
											/* Insert Journal already processed values in data set history table */
											boolean db_result=dbconfig.updateHistroyTable(metaxml_dataset, 
													"Supplied file Journal Name not found in Journal table list", Downloadpicktime, stage);
											
											if(db_result) {
												ftpClient.deleteFile(rootdir+fileName);	
												ftpClient.deleteFile(rootdir+metaxml_zipfilename);
												System.out.println("FTP Files deleted	:: " +metaxml_zipfilename);
											}
										}
										
										/* If Journal Exists in Journal table */
										else {
											
											/* Check current journal already processed or not*/
											boolean tokenExists = dbconfig.checkTokenAlreadyExists(metaxml_journalid, 
													metaxml_articleid, token);
											
											/* If Journal already processed */
											if(tokenExists) {
												SendMAil sendmailobj1 = new SendMAil();
												sendmailobj1.mailcall("empty", "empty", metaxml_zipfilename, "already-generated");	
												/* Insert tokenExists values in data set history table */
												boolean db_result1=dbconfig.updateHistroyTable(metaxml_dataset, 
														"File Already Processed (Token Exists)", Downloadpicktime, stage);											
												if(db_result1) {
													ftpClient.deleteFile(rootdir+fileName);	
													ftpClient.deleteFile(rootdir+metaxml_zipfilename);
													System.out.println("FTP Files deleted	:: " +metaxml_zipfilename);
												}
											}											
											/* If journal not processed before, first time process*/
											else 
											{									
												/* Checkpoint table value insert */									
												boolean db_check = dbconfig.insertCheckPointValue(metaxml_journalid, metaxml_articleid, 
														metaxml_dataset, metaxml_zipfilename,1,fileName);																		
												
												
												/* Insert values in data set histroy table */
												boolean db_check2=dbconfig.updateHistroyTable(metaxml_dataset, "success", Downloadpicktime, stage);

												
												/* Delete FTP files based on successfully table insertion without exception- validation*/
												if(db_check && db_check2) {										
													ftpClient.deleteFile(rootdir+fileName);	
													ftpClient.deleteFile(rootdir+metaxml_zipfilename);
													System.out.println("FTP Files deleted	:: " +metaxml_zipfilename);
												}
											}											
										}			
									}
									
									/* If exception occur in token generation */
									else {
										
										/* Insert Zip extract failed values in data set history table */
										boolean db_result=dbconfig.updateHistroyTable(metaxml_dataset, "Token Generation Error", 
															Downloadpicktime, stage);
										
										if(db_result) {
											ftpClient.deleteFile(rootdir+fileName);	
											ftpClient.deleteFile(rootdir+metaxml_zipfilename);
											System.out.println("FTP Files deleted	:: " +metaxml_zipfilename);
										}
									}
								}
								else {
									System.out.println("Unzip process Failed");
									
									/* Insert Zip extract failed values in data set history table */
									boolean db_result=dbconfig.updateHistroyTable(metaxml_dataset, "Zip Extract Failed", 
														Downloadpicktime, stage);
									
									if(db_result) {
										ftpClient.deleteFile(rootdir+fileName);	
										ftpClient.deleteFile(rootdir+metaxml_zipfilename);
										System.out.println("FTP Files deleted	:: " +metaxml_zipfilename);
									}
									
								}
							} else {
								System.out.println("Dataset table values inserted Failed  ::" +fileName);
							}
								
							}								
						}
					}
					
					Thread.sleep(2000);
				}
				else {
					
				}
					
			}		
		}
		
		/* No meta XML files available in FTP */			
		System.out.println("===========================================================");
		System.out.println("Meta XML file not found in ftp folder..... Process restart");
		System.out.println("===========================================================");
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}				
		
		inWorkflow();
		} catch (IOException e1) {
			System.out.println("FTP file download exception  :: "+e1);			
		}		
	}

	private static HashMap tokenGenerate(String journalId, String articleId) {

		HashMap<String, String> map=new HashMap<String,String>();
		String message="";
		StringBuffer sb = new StringBuffer();
		String original=journalId+articleId;
		
		if(original.trim().length()==0) {			
			System.out.println("\nToken Pass Length is zero ");		
			map.put("token", "Failure");
			map.put("status", "false");
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
				map.put("token", "Failure");
				map.put("status", "false");				
			}
		}
		//return sb.toString();
		return map;
	}

	/* ZIP file extract implentation */
	private static boolean unZip(String metaxml_journalid,
			String metaxml_articleid, String metaxml_dataset,
			String metaxml_filename, String localUploadPath) {
	
		boolean unzip_result=true;
		
		String message="";		
		try {
			File dir = new File(localUploadPath);
			
			/* create output directory if it doesn't exist */
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileInputStream fis;
			/* buffer for read and write data to file */
			byte[] buffer = new byte[1024];        
            fis = new FileInputStream(localUploadPath+metaxml_filename);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            
            while (ze != null) {            	
            	if (ze.isDirectory()) {
                	File f1=new File(ze.getName());
            		if(!f1.exists()) {            			
            			//f1.mkdir();
            		}
                }
            	else {
            		String fileName = ze.getName();                    
                    File newFile = new File(localUploadPath + File.separator + fileName);                         
                    //create directories for sub directories in zip
                    new File(newFile.getParent()).mkdirs();                    
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    
                    while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                    }
                    fos.close();
            	}            	                
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (Exception e) {
        	unzip_result=false;
        	System.out.println("File unzip exception :: "+e);
        }	
		return unzip_result;
	}

	
	/* File backup implementation */
	private static boolean fileBackup(String string,
			String fileName) {
		
		
		boolean result=true;		
		SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy");
		String date1=sd.format(new Date());
		
		File dst_path=new File(backupPath+date1+"/");
		File src_path=new File(localUploadPath+fileName);
		if(!dst_path.exists())
		{
		  	dst_path.mkdir();
		   	File dst_path1=new File(backupPath+date1+"/"+fileName);
		   	
		   	try {		   		
		   		copyFileUsingFileChannels(src_path,dst_path1);		   
		   		
			} catch (IOException e) {
				result=false;
				System.out.println("Backup file creation exception in XML_Read class :: "+e);
				e.printStackTrace();
			}	  	
		}
		else
		{
		  	File dst_path1=new File(backupPath+date1+"/"+fileName);
		   	try {
		   		
		   		copyFileUsingFileChannels(src_path,dst_path1);
			
		   	} catch (IOException e) {
				result=false;
				
				System.out.println("Backup file creation exception in XML_Read class :: "+e);
				e.printStackTrace();
			}		   	
		}
		return result;		
		
	}

	/* File copy implementation */
	private static void copyFileUsingFileChannels(File src_path, File dst_path1) throws IOException {
		
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		
		try {
			inputChannel = new FileInputStream(src_path).getChannel();
			outputChannel = new FileOutputStream(dst_path1).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}		
	}

	/* Read meta xml file in local folder get details */
	private static HashMap metaXMLInfo(String fileName) {	
		
		HashMap<String, String> map=new HashMap<String,String>();
		map.clear();
		
		//System.out.println("artInfo	Method Reached::: ");
		
		String s1="";
	    BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(localUploadPath+"/"+fileName));
		
			while((s1=br.readLine())!=null)
			{	    
				//Article ID
				Pattern pattern=Pattern.compile("<dataset>([^<]+?)</dataset>");
				Matcher matcher=pattern.matcher(s1);
				while(matcher.find())
				{	    								
					map.put("dataset",matcher.group(1));   					
				}
			
				Pattern pattern1=Pattern.compile("<article-id>([^<]+?)</article-id>");
				Matcher matcher1=pattern1.matcher(s1);
				while(matcher1.find())
				{	    					   					
					map.put("artid",matcher1.group(1));
				}
						
				Pattern pattern2=Pattern.compile("<journal-id>([^<]+?)</journal-id>");
				Matcher matcher2=pattern2.matcher(s1);
				while(matcher2.find())
				{	    						
					map.put("jid",matcher2.group(1));
				}
				
				Pattern pattern3=Pattern.compile("<filename>([^<]+?)</filename>");
				Matcher matcher3=pattern3.matcher(s1);
				while(matcher3.find())
				{	    						
					map.put("filename",matcher3.group(1));					
				}
			}
	
			br.close();
		} catch (IOException e) {
			
			System.out.println("Meta XML file read exception or file missing "+e);			
			//dbconfig.insertRecords(supplierId,meta_dataset,"MetaXMLFormatError",DownloadPickUpTime,startTime, endTime,message);						
			//System.out.println("XML Read class artInfo method Exception : "+ex1);
		}	
	    return map;		
	}

	/* FTP File download implementation */
	private static boolean downloadFTPFile(String fileName) throws IOException {	
		
		boolean status=false;
		OutputStream outputStream = null;		
		File downloadFile = new File(localUploadPath + "/" + fileName);
		outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));	
		
		try {
			status = ftpClient.retrieveFile(rootdir.equals(".") ? fileName : rootdir + 
					 fileName, outputStream);
			
		} catch (Exception ex) {
			System.out.println("Exception in file download, check upload path :"+ ex);
		}
		
		outputStream.close();		
		return status;
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
		
		System.out.println("Local UploadPath	:: "+localUploadPath);
		System.out.println("Proofs uploadPath	:: "+proofs_uploadPath);
		System.out.println("Backup Path		:: "+backupPath);
		System.out.println("XSLT Path		:: "+XSLTPath);
		System.out.println("FTP Rootdir path	:: "+rootdir+"\n");
	}	
	
	public static boolean ftpConnect(FTPClient ftpClient) throws InterruptedException {
		
		boolean status=false;
		try {		
		ftpClient = connectFTPLogin(ftpClient);		
		boolean login_success = ftpClient.login(user, pass);		
		if (login_success) {
			status=true;			
		}
		else {			
			System.out.println("Failed to connect FTP login");
			Thread.sleep(1000);
			inWorkflow();			
		}			
		} catch (Exception e) {			
			System.out.println("Failed to FTP Login :: "+e);
			inWorkflow();		
		}
		System.out.println("FTP Status		:: "+status);
		return status;
	}

	private static FTPClient connectFTPLogin(FTPClient ftpClient) {		
		try {
			ftpClient.connect(server, port);
			return ftpClient;
		} catch (Exception e) {
			System.out.println("Failed to connect FTP : " + e);
			return ftpClient;			
		}
	}
}
