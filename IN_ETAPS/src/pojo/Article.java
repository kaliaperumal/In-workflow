package pojo;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Article {

	private Integer ArticleKey;
	private String aid;
	private String jid;
	private int StatusId;
	private String Site;
	private String Title;
	private String CorrespondingAuthorName;
	private String CorrespondingAuthorEmail;
	private Date DateArticlePosted;
	private String OPTURL;
	private int ExpiredForExternalAccess;
	private String Stage;
	private int DatasetStatusID;
	private int IsDowngradeAccess;
	private int 	OfflineStatus;
	private String OUTXMLStatus;
	private ArticleEnum Edited;
	private ArticleEnum Purpose;	
	private Users users;
	
	
	public Article() {
		
	}
	
	public Article(Integer articleKey, String aid, String jid, int statusId,
			String site, String title, String correspondingAuthorName,
			String correspondingAuthorEmail, Date dateArticlePosted,
			String oPTURL, int expiredForExternalAccess, String stage,
			int datasetStatusID, int isDowngradeAccess, int offlineStatus,
			String oUTXMLStatus, ArticleEnum edited, ArticleEnum purpose,
			Users users) {
		super();
		ArticleKey = articleKey;
		this.aid = aid;
		this.jid = jid;
		StatusId = statusId;
		Site = site;
		Title = title;
		CorrespondingAuthorName = correspondingAuthorName;
		CorrespondingAuthorEmail = correspondingAuthorEmail;
		DateArticlePosted = dateArticlePosted;
		OPTURL = oPTURL;
		ExpiredForExternalAccess = expiredForExternalAccess;
		Stage = stage;
		DatasetStatusID = datasetStatusID;
		IsDowngradeAccess = isDowngradeAccess;
		OfflineStatus = offlineStatus;
		OUTXMLStatus = oUTXMLStatus;
		Edited = edited;
		Purpose = purpose;
		this.users = users;
	}
	
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public Integer getArticleKey() {
		return ArticleKey;
	}
	public void setArticleKey(Integer articleKey) {
		ArticleKey = articleKey;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public int getStatusId() {
		return StatusId;
	}
	public void setStatusId(int statusId) {
		StatusId = statusId;
	}
	public String getSite() {
		return Site;
	}
	public void setSite(String site) {
		Site = site;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getCorrespondingAuthorName() {
		return CorrespondingAuthorName;
	}
	public void setCorrespondingAuthorName(String correspondingAuthorName) {
		CorrespondingAuthorName = correspondingAuthorName;
	}
	public String getCorrespondingAuthorEmail() {
		return CorrespondingAuthorEmail;
	}
	public void setCorrespondingAuthorEmail(String correspondingAuthorEmail) {
		CorrespondingAuthorEmail = correspondingAuthorEmail;
	}
	public Date getDateArticlePosted() {
		return DateArticlePosted;
	}
	public void setDateArticlePosted(Date dateArticlePosted) {
		DateArticlePosted = dateArticlePosted;
	}
	public String getOPTURL() {
		return OPTURL;
	}
	public void setOPTURL(String oPTURL) {
		OPTURL = oPTURL;
	}
	public int getExpiredForExternalAccess() {
		return ExpiredForExternalAccess;
	}
	public void setExpiredForExternalAccess(int expiredForExternalAccess) {
		ExpiredForExternalAccess = expiredForExternalAccess;
	}
	public String getStage() {
		return Stage;
	}
	public void setStage(String stage) {
		Stage = stage;
	}
	public int getDatasetStatusID() {
		return DatasetStatusID;
	}
	public void setDatasetStatusID(int datasetStatusID) {
		DatasetStatusID = datasetStatusID;
	}
	public int getIsDowngradeAccess() {
		return IsDowngradeAccess;
	}
	public void setIsDowngradeAccess(int isDowngradeAccess) {
		IsDowngradeAccess = isDowngradeAccess;
	}
	public int getOfflineStatus() {
		return OfflineStatus;
	}
	public void setOfflineStatus(int offlineStatus) {
		OfflineStatus = offlineStatus;
	}
	public String getOUTXMLStatus() {
		return OUTXMLStatus;
	}
	public void setOUTXMLStatus(String oUTXMLStatus) {
		OUTXMLStatus = oUTXMLStatus;
	}
	
	@Enumerated(EnumType.STRING)
	public ArticleEnum getEdited() {
		return Edited;
	}
	public void setEdited(ArticleEnum edited) {
		Edited = edited;
	}
	
	@Enumerated(EnumType.STRING)
	public Enum getPurpose() {
		return Purpose;
	}
	public void setPurpose(ArticleEnum purpose) {
		Purpose = purpose;
	}
}