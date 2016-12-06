package model;

import controller.eProduceDatabase;

//Simple model class for Ticket
public class Ticket {
	private User owner;
	private String description;
	private String response;
	private String ticketNum;
	
	public Ticket(String ownerEmail, String description, String response, String ticketNum)
	{
		owner = eProduceDatabase.getOwnerDetails(ownerEmail);
		this.description = description;
		this.ticketNum = ticketNum;
		this.response = response;
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
		this.owner = eProduceDatabase.getOwnerDetails(ownerEmail);
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
