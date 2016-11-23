package model;

public class Feedback {
	String owner;
	String content;
	String feedbackNum;
	
	public Feedback(String owner, String content, String listingNum)
	{
		this.owner = owner;
		this.content = content;
		this.feedbackNum = listingNum;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFeedbackNum() {
		return feedbackNum;
	}
	public void setFeedbackNum(String feedbackNum) {
		this.feedbackNum = feedbackNum;
	}
	
}
