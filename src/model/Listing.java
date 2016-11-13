package model;

import java.util.regex.Pattern;

import controller.eProduceDatabase;

public class Listing {
	private User owner;
	private int listingNum;
	private String content;
	private String[] tags;
	private String title;
	private eProduceDatabase db = new eProduceDatabase();

	public Listing(String owner, String title, String content, String tags, int listNum) {
		this.owner = db.getOwnerDetails(owner);
		this.title = title;
		this.content = content;
		this.tags = tags.split(",");
		this.listingNum = listNum;
	}
	public Listing() {
		owner = new User();
		listingNum = 0;
		content = "";
		tags = new String[0];
		title = "";
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public void setOwner(String owner)
	{
		this.owner = db.getOwnerDetails(owner);
	}
	public int getListingNum() {
		return listingNum;
	}
	public void setListingNum(int listingNumb) {
		this.listingNum = listingNumb;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTags() {
		String returnString = "";
		for(int i = 0; i < tags.length; i++)
		{
			returnString += tags[i];
			if(i != tags.length-1)
			{
				returnString += ",";
			}
		}
		return returnString;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
