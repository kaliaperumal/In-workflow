package pojo;

import java.util.Date;
import pojo.Article;

public class Users {

	private Integer sno;
	private Article article;	
	private String customer;
	private String jid;
	private String aid;
	private String password;
	private String filepath;
	private String pdfpath;
	private String size	;
	private Date opened;
	private Date modified;
	private String status;
	private String token;
	private String uname;
	private int SignIn;
	private String ip;
	private int CheckSaved;
	
	
	public Users(Article article, String customer, String jid, String aid, String password, 
			     String filepath, String pdfpath, String size, Date opened, Date modified, 
			     String status, String token, String uname, int SignIn, String ip, int CheckSaved) {
		
		this.article=article;
		this.customer=customer;
		this.jid=jid;
		this.aid=aid;
		this.password=password;
		this.filepath=filepath;
		this.pdfpath=pdfpath;
		this.size=size;
		this.opened=opened;
		this.modified=modified;
		this.status=status;
		this.token=token;
		this.uname=uname;
		this.SignIn=SignIn;
		this.ip=ip;
		this.CheckSaved=CheckSaved;
	}
	public Users() {
		
	}
	
	public Users(Article article) {
		this.article=article;
	}

	public Article getArticle() {
		return article;
	}


	public void setArticle(Article article) {
		this.article = article;
	}

	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getPdfpath() {
		return pdfpath;
	}
	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Date getOpened() {
		return opened;
	}
	public void setOpened(Date opened) {
		this.opened = opened;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getSignIn() {
		return SignIn;
	}
	public void setSignIn(int signIn) {
		SignIn = signIn;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getCheckSaved() {
		return CheckSaved;
	}
	public void setCheckSaved(int checkSaved) {
		CheckSaved = checkSaved;
	}
	
	
}

