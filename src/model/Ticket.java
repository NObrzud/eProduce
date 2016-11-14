package model;

import controller.eProduceDatabase;

public class Ticket {
	private User owner;
	private String description;
	private String ticketNum;
	private String followup;
	
	private eProduceDatabase db = new eProduceDatabase();
	public Ticket(String ownerEmail, String description, String ticketNum)
	{
		owner = db.getOwnerDetails(ownerEmail);
		this.description = description;
		this.ticketNum = ticketNum;
	}
	public User getOwner() {
		return owner;
	}
	public String getDescription()
	{
		return description;
	}
	public String getTicketNum()
	{
		return ticketNum;
	}
	public String getFollowup()
	{
		return followup;
	}
	public void setDescription(String description)
	{
		this.description = description; 
	}
	public void setOwner(String ownerEmail)
	{
		this.owner = db.getOwnerDetails(ownerEmail);
	}
	public void setOwner(User owner)
	{
		this.owner = owner;
	}
	public void setTicketNum(String ticketNum)
	{
		this.ticketNum = ticketNum;
	}
	public void setFollowup(String followup)
	{
		this.followup = followup;
	}
}
