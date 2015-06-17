package pojo;

public class Journal {
	
	private Integer JKey;
	private String Jid;
	private String JExpansion;	
	private String MailId;
	
	public Journal() {
		
	}
	
	public Journal(String Jid, String JExpansion, String MailId) {
		this.Jid=Jid;
		this.JExpansion=JExpansion;
		this.MailId=MailId;
	}
	
	public Integer getJKey() {
		return this.JKey;
	}
	
	public void setJKey(Integer JKey) {
		this.JKey=JKey;
	}
	
	public String getJid() {
		return this.Jid;
	}
	
	public void setJid(String Jid) {
		this.Jid=Jid;
	}
	
	public String getJExpansion() {
		return JExpansion;
	}

	public void setJExpansion(String jExpansion) {
		JExpansion = jExpansion;
	}

	public String getMailId() {
		return MailId;
	}

	public void setMailId(String mailId) {
		MailId = mailId;
	}

}