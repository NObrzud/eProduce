package model;

import java.sql.Date;
import java.sql.Time;

public class Meetup {
	private Date date;
	private Time time;
	private String owner;
	private String participants;
	private String location;
	private int meetupNum;
	
	public Meetup(Date d, Time t, String o, String p, String l, int n)
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
	public String getParticipants() {
		return participants;
	}
	public void setParticipants(String participants) {
		this.participants = participants;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getMeetupNum() {
		return meetupNum;
	}
	public void setMeetupNum(int meetupNum) {
		this.meetupNum = meetupNum;
	}
	
}
