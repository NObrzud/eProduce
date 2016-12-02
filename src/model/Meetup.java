package model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Meetup {
	private Date date;
	private Time time;
	private String owner;
	private ArrayList<User> participants;
	private String location;
	private String meetupNum;
	
	public Meetup(Date d, Time t, String o, ArrayList<User> p, String l, String n)
	{
		date = d;
		time = t;
		owner = o;
		participants = p;
		location = l;
		meetupNum = n;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public ArrayList<User> getParticipants() {
		return participants;
	}
	public void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}
	public void addParticipant(User participant){
		participants.add(participant);
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMeetupNum() {
		return meetupNum;
	}
	public void setMeetupNum(String meetupNum) {
		this.meetupNum = meetupNum;
	}
	
}
