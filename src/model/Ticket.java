package model;

import controller.eProduceDatabase;

public class Ticket {
	private User owner;
	private String description;
	private String ticketNum;
	private String response;
	
	private eProduceDatabase db = new eProduceDatabase();
	public Ticket(String ownerEmail, String description, String response, String ticketNum)
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
	public String getResponse()
	{
		return response;
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
	public void setResponse(String response)
	{
		this.response = response;
	}
}
