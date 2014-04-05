package it.polito.ai14.lab.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PrenotazioneAction extends ActionSupport {
	private String room;
	private String day;
	private String month;
	private String year;
	private String startTime;
	
	public String execute() {
    	Map<String, Object> session = ActionContext.getContext().getSession();
    	String utente = (String) session.get("username");
		System.out.println(utente + " " + day + " " + month + " " + year + " " + startTime + " " + room);
		return SUCCESS;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
