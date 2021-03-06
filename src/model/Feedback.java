package model;

public class Feedback {
	private User owner;
	private String content;
	private String feedbackNum;
	
	public Feedback(User owner, String content, String listingNum)
	{
		this.owner = owner;
		this.content = content;
		this.feedbackNum = listingNum;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
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
