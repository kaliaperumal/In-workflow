package com.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sequence.SequenceId;
import com.xsltconversion.XSLTConversion;

import pojo.Article;
import pojo.ArticleEnum;
import pojo.Checkpoint;
import pojo.Datasethistory;
import pojo.Datasetstatus;
import pojo.Journal;
import pojo.Users;

public class DatabaseConfig {	
	
	static SequenceId seqObj=new SequenceId();
	static XSLTConversion xsltObj=new XSLTConversion();
	
	
	/* Insert values to check point table using FTP Download class */
	public boolean insertCheckPointValue(String metaxml_journalid, String metaxml_articleid, 
						String metaxml_dataset, String metaxzipfilename, int status, String metaXMLFileName) {
		
		boolean exception_status=true;	
		Session session=null;
		Transaction tx=null;
		
		try {
		session=HibernateUtil.getSessionFactory().openSession();
		tx=session.beginTransaction();	
		
		Checkpoint obj=new Checkpoint();
		obj.setJournalId(metaxml_journalid);
		obj.setArticleId(metaxml_articleid);
		obj.setDataset(metaxml_dataset);
		obj.setMetaXMLName(metaXMLFileName);
		obj.setZipfileName(metaxzipfilename);
		obj.setStatus(status);		
		session.save(obj);		
		tx.commit();
		
		} catch (Exception e) {
			exception_status=false;
			System.out.println("\nHibernate Check point table values insert exception :"+e);
		} finally {
			session.clear();
			session.close();
		}
		return exception_status;
	}
	
	/* Insert values to dataset status table using FTP Download class  - Used in FTP Download Class*/
	public boolean insertDataSetStatusRecords(int supplierId, String meta_dataset,String status, 
					String DownloadPickUpTime, String StartDate, String EndDate,String message, 
					String journalId, String articleId, String fileName) {
		
		boolean exception_status=true;
		
		Session session=null;
		Transaction tx=null;
		try {
			SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
			
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();
			//String q1="INSERT INTO datasetstatus(SupplierID,DatasetiD,Status,DownloadPickUpTime,StartDate,EndDate) VALUES(?,?,?,?,?,?)";
			String q1="INSERT INTO datasetstatus(SupplierID, DatasetiD, JournalID, ArticleID, "
					+ "Status, MetaXMLName, DownloadPickUpTime, StartDate,EndDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			Query query=session.createSQLQuery(q1);			
			query.setParameter(0, supplierId);	
			query.setParameter(1, meta_dataset);
			query.setParameter(2, journalId);
			query.setParameter(3, articleId);
			query.setParameter(4, status);
			query.setParameter(5, fileName);
			query.setParameter(6, sd.parse(DownloadPickUpTime));
			query.setParameter(7,sd.parse(StartDate));
			query.setParameter(8,sd.parse(EndDate));			
			int result=query.executeUpdate();
			tx.commit();		
			if(result==1)
			{
				System.out.println("");
			}
			else {
				System.out.println("\nDatasetstatus record Insert Failed");
				exception_status=false;
			}
		} catch(Exception e1) {
			exception_status=false;
			System.out.println("Exception in DataSetStatus table records insert : "+e1);
		}
		finally {			
			session.clear();
			session.close();
		}
		return exception_status;
	}
	
	/* Add Article title in dataset status table using sequence id class */
    public void insertTitle(String dataset, String title) {    	
    	Session session=null;
		Transaction tx=null;
		try {
			
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();					
			String q1 = "UPDATE Datasetstatus set ArticleTitle=:ArticleTitle where datasetId = '"+dataset+"'";
			Query query=session.createQuery(q1).setParameter("ArticleTitle", title);			
			int result=query.executeUpdate();
			tx.commit();
			if(result==1) {
				System.out.println("Title column Row Affected in datasetstatus table : "+result);
			} else {
				System.out.println("Title column Row Not Affected in datasetstatus table : "+result);
			}			
		} catch (Exception e1) {
			System.out.println("Exception in insertTitle inside dataset status tabel :"+e1);
		} finally {
			session.close();
		}
    }
	
 
	
	/* Insert values to datasethistroy table using FTP Download class */
	public boolean updateHistroyTable(String meta_dataset, String message,
			String downloadPickUpTime, String stage) {
		
		boolean exception_status=true;			
		Session session=null;
		Transaction tx=null;
		try {		
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();		
			//List<Integer> dobj=(List<Integer>) session.createSQLQuery("SELECT ds.datasetstatusID " + "from datasetstatus ds " +"where ds.datasetId = :dataSetId").setParameter("dataSetId", meta_dataset).list();				
			String q1 = "from Datasetstatus d where d.datasetId = :meta_dataset";
			Query query=session.createQuery(q1).setParameter("meta_dataset", meta_dataset);
			List<Datasetstatus> dobj=query.list();
			for(Datasetstatus dobj1: dobj) {				
				int key=Integer.parseInt(dobj1.getDatasetStatusId().toString());			
				updateHistroyTable(meta_dataset,key,message,downloadPickUpTime,stage);
			}
		}catch(Exception e1) {
			exception_status=false;
			System.out.println("Exception in histroy table foreign key insert Method : "+e1);
		}
		finally {
			session.clear();
			session.close();			
		}
		return exception_status;
	}

	/* Insert values datasethistroy table using dataset status table based on foreign key value */
	private static void updateHistroyTable(String datasetid, int datasetStatusId, String message, 
											 String processTime, String stage) {		
		Session session=null;
		Transaction tx=null;
		SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
		
		try {			
		
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();			
			Datasethistory historyObj=new Datasethistory();			
			historyObj.setMessage(message);
			historyObj.setDatasetid(datasetid);
			historyObj.setStage(stage);
			historyObj.setProcessTime(sd.parse(processTime));
			Datasetstatus statusObj=(Datasetstatus)session.get(Datasetstatus.class, datasetStatusId);
			historyObj.setDatasetstatus(statusObj);			
			session.save(historyObj);
			tx.commit();									
		
		} catch(Exception e) {
			System.out.println("\nException in datasethistory table insert  ::"+e);
		} finally {
			session.clear();
			session.close();
		}		
	}
	
	/* Get details from checkpoint table , Stage like SequenceID or XSLTConversion */
	public void getID(int id, String stage) throws InterruptedException
	{	
		Session session=null;
		Transaction tx=null;		
		try {
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();		
			String q1="FROM Checkpoint c where c.status='"+id+"'";
			Query query=session.createQuery(q1);
			List<Checkpoint> results =(List<Checkpoint>)query.list();
			tx.commit();
			session.clear();
			session.close();
			
			System.out.println("Total Records :: "+results.size());				
			for(Checkpoint obj: results) {
					
				System.out.println("Journal ID :: "+obj.getJournalId());
				System.out.println("Article ID :: "+obj.getArticleId());
				System.out.println("Dataset    :: "+obj.getDataset());				
				System.out.println("MetaXMLName    :: "+obj.getMetaXMLName()+"\n");				
				Thread.sleep(9000);
				
				if(stage.equals("SequenceID")) {
					/* XML Sequence id insert id get */
					seqObj.getDatasetID(obj.getJournalId(), obj.getArticleId(), 
							obj.getDataset(), obj.getZipfileName());
				}
				else if(stage.equals("XSLTConversion")) {
					
					xsltObj.mainConversion(obj.getJournalId(), obj.getArticleId(), 
							obj.getDataset(), obj.getZipfileName(), obj.getMetaXMLName());
				}
			}			
				
		} catch(Exception e) {			
			System.out.println("Exception in get dataset from checkpoint table : "+e);			
		}
	}
	
	/* Set status 1 to 2 in check point table */	
	public void setStatus(String dataset, int status) {
		
		Session session=null;
		Transaction tx=null;		
		try {
		session=HibernateUtil.getSessionFactory().openSession();
		tx=session.beginTransaction();	
		
		String q1="Update Checkpoint set status=:status where dataset ='"+dataset+"'";
		Query query=session.createQuery(q1).setParameter("status", status);
		int result=query.executeUpdate();
		System.out.println("Check Point table status column Affected  :: "+result);		
		tx.commit();		
		
		if(result==1) {
			session.clear();
			session.close();	
		} else {
			session.clear();
			session.close();
		}		
		 
		Thread.sleep(9000);
		
		} catch (Exception e) {
			System.out.println("Hibernate Check point table status set exception :"+e);
		} finally {			
			
		}		
	}

	/* Insert values data set histroy table without using dataset status table */
	public boolean insertHistroyTable(String dataset, String message, 
			String processtime, String stage) {
		
		boolean status=true;		
		Session session=null;
		Transaction tx=null;
		try {
		int datasetstatusID = 0;
		session=HibernateUtil.getSessionFactory().openSession();
		tx=session.beginTransaction();
		String q1="From Datasethistory d where d.Datasetid='"+dataset+"'";
		Query query=session.createQuery(q1);
		List<Datasethistory> obj=(List<Datasethistory>)query.list();
		for(Datasethistory obj1 :obj) {		
			System.out.println("ID is  :: "+obj1.getDatasetstatus().getDatasetStatusId());
			datasetstatusID=obj1.getDatasetstatus().getDatasetStatusId();
		}
		if(datasetstatusID==0) {
			
			status=false; 
		}
		else {
			updateHistroyTable(dataset, datasetstatusID, message, processtime, stage);	
		}
		} catch (Exception e) {
			System.out.println("Exception in get datasetstatusID from histroy table :"+e);
			status=false;
		}
		return status; 
	}
	
	
	/* Delete row if process failure in checkpoint table*/
	public boolean deleteCheckpointTable(String dataset) {		
		boolean status=true;		
		Session session=null;
		Transaction tx=null;
		
		try {
		session	=HibernateUtil.getSessionFactory().openSession();
		tx=session.beginTransaction();
		String hql="DELETE FROM Checkpoint WHERE dataset =:dataset";
		Query query=session.createQuery(hql);
		query.setParameter("dataset", dataset);		
		
		int result=query.executeUpdate();
		tx.commit();
		System.out.println("deleteCheckpointTable Rows Affected : "+result);
		
		SendMAil sendmailobj=new SendMAil();
		
		} catch(Exception e) {
			status=false;
			System.out.println("Exception record delete in checkpointTable "+e);
		}
		return status;	
	}
	
	public void getDatasetstatusTable(String dataset, String token) {
		
		Session session=null;
		Transaction tx=null;
		try {		
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();		
			//List<Integer> dobj=(List<Integer>) session.createSQLQuery("SELECT ds.datasetstatusID " + "from datasetstatus ds " +"where ds.datasetId = :dataSetId").setParameter("dataSetId", meta_dataset).list();				
			String q1 = "from Datasetstatus d where d.datasetId = :dataset";
			Query query=session.createQuery(q1).setParameter("dataset", dataset);
			List<Datasetstatus> dobj=query.list();
			
			for(Datasetstatus dobj1: dobj) {				
				int DatasetStatusId=Integer.parseInt(dobj1.getDatasetStatusId().toString());
				String jid=dobj1.getJournalId();
				String aid=dobj1.getArticleId();
				String articletitle=dobj1.getArticleTitle();
				String downloadTime=dobj1.getDownloadPickUpTime().toString();				
				
				xsltObj.initiateArticleTableValues(dataset, DatasetStatusId, jid, aid, articletitle, downloadTime, token);			
			}
		}catch(Exception e1) {
			
			System.out.println("Exception in Foreign key insert Method : "+e1);
		}
		finally {
			session.clear();
			session.close();			
		}
	}

	public void insertArticleTableValues(int datasetStatusId, String jid,
			String aid, String articletitle, String downloadTime, String token) {		
		Session session=null;
		Transaction tx=null;	
		
		try {			
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();		
			Article obj=new Article();	
			obj.setAid(aid);
			obj.setJid(jid);		
			obj.setDatasetStatusID(datasetStatusId);
			obj.setTitle(articletitle);
			obj.setIsDowngradeAccess(1);	
			obj.setEdited(ArticleEnum.No);
			obj.setStage("Final");
			obj.setStatusId(2);		
			obj.setPurpose(ArticleEnum.Demo);	
			session.save(obj);
			tx.commit();		
		}catch (Exception e) {
			System.out.println("Exception in insertArticleTableValues values :"+e);
		} finally {
			session.close();
		}
	}
	
	/* Insert values to datasethistroy table using FTP Download class */
	public boolean getArticleTablePrimaryKey(String dataset, int datasetStatusId, String jid, String aid, 
			String articletitle, String downloadTime, String token, String filePath) {
		
		boolean exception_status=true;	
		
		Session session=null;
		Transaction tx=null;
		try {		
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();		
			//List<Integer> dobj=(List<Integer>) session.createSQLQuery("SELECT ds.datasetstatusID " + "from datasetstatus ds " +"where ds.datasetId = :dataSetId").setParameter("dataSetId", meta_dataset).list();				
			String q1 = "From Article a where a.DatasetStatusID = :datasetStatusId";
			Query query=session.createQuery(q1).setParameter("datasetStatusId", datasetStatusId);
			List<Article> obj=query.list();
			for(Article articleobj: obj) {				
				int articlekey=articleobj.getArticleKey();				
				
				/* Insert User table values */
				insertUserTable(articlekey, datasetStatusId, jid, aid, articletitle, downloadTime, token, filePath);
			}
		}catch(Exception e1) {
			exception_status=false;
			System.out.println("Exception in Foreign key get Method in article table : "+e1);
		}
		finally {
			session.clear();
			session.close();			
		}
		return exception_status;
	}

	private void insertUserTable(int articlekey, int datasetStatusId,
			String jid, String aid, String articletitle, String downloadTime,
			String token, String filePath) {
		
		Session session=null;
		Transaction tx=null;		
		try {			
			session=HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();			
			Article statusObj=(Article)session.get(Article.class, articlekey);
			Users userObj=new Users();
			userObj.setAid(aid);
			userObj.setCustomer(jid);
			userObj.setFilepath(filePath);
			userObj.setJid(jid);
			userObj.setToken(token);
			userObj.setPassword("pass");
			userObj.setArticle(statusObj);		
			session.save(userObj);			
			tx.commit();									
		} catch(Exception e) {
			System.out.println("Exception in Users table insert  ::"+e);
		} finally {
			session.clear();
			session.close();
		}		
	}

	public boolean checkTokenAlreadyExists(String metaxml_journalid,
					String metaxml_articleid, String token) {
		
		boolean checkTokenExists=false;				
		Session session=null;
		Transaction tx=null;		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx=session.beginTransaction();
			String hql="FROM Users u WHERE u.token= :token";
			Query query=session.createQuery(hql).setParameter("token", token);
			List<Users> userObj=(List<Users>)query.list(); 
			//Users user=(Users)query.uniqueResult();
			if(userObj.size()!=0) {
				System.out.println("Token already Found :: "+token);
				checkTokenExists=true;
			}
			else {
				System.out.println("Token Not Found in user table "+token);
			}
		
		} catch(Exception e) {
			System.out.println("Exception in token value check :"+e);
		}
		return checkTokenExists;		
	}

	public boolean checkSuppliedJournal(String metaxml_journalid) {
		
		boolean checkJournalExists=false;
		 
		Session session=null;
		Transaction tx=null;
		try {
			session=HibernateUtil.getSessionFactory().openSession();
			String hql="From Journal j where j.Jid=:jid";
			Query query=session.createQuery(hql).setParameter("jid", metaxml_journalid.toLowerCase());
			Journal journal=(Journal)query.uniqueResult();
			
			if(journal!=null) {
				checkJournalExists=true;
				System.out.println("Client Supplied Artivcel Found :: "+metaxml_journalid);
			}
			else {
				
			}
			session.close();
		} catch(Exception e) {
			System.out.println("Exception in checkSuppliedJournal table : "+e);
		}
		return checkJournalExists;
	}
}