package model;

import java.util.regex.Pattern;

public class Listing {
	private String owner;
	private int listingNumb;
	private String content;
	private String[] tags;
	public Listing(String owner, String content, String tags) {
		this.owner = owner;
		this.content = content;
		this.tags = tags.split(",");
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getListingNumb() {
		return listingNumb;
	}
	public void setListingNumb(int listingNumb) {
		this.listingNumb = listingNumb;
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
			returnString += tags[i] + " ";
		return returnString;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
