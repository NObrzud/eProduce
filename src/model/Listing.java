package model;

import java.util.regex.Pattern;

public class Listing {
	private String owner;
	private int listingNum;
	private String content;
	private String[] tags;
	private String title;

	public Listing(String owner, String title, String content, String tags, int listNum) {
		this.owner = owner;
		this.title = title;
		this.content = content;
		this.tags = tags.split(",");
		this.listingNum = listNum;
	}
	public Listing() {
		owner = "";
		listingNum = 0;
		content = "";
		tags = new String[0];
		title = "";
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
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
			returnString += tags[i] + ", ";
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
