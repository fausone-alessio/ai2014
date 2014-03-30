package it.polito.ai14.lab.action;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class CalendarAction extends ActionSupport {
	
	private static final long serialVersionUID = 2777557863534616656L;
	private Integer year;
	private Integer week;
	private String room;
	
	public String execute() {
		Calendar cal = Calendar.getInstance();
		
		if(week == null || week < 1 || week > 53) {
			week = cal.get(Calendar.WEEK_OF_YEAR);
		}
		
		if(year == null || year < cal.get(Calendar.YEAR)) {
			year = cal.get(Calendar.YEAR);
		}
		
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		
		Map<Integer, Map<Date, String>> slots = new TreeMap<Integer, Map<Date,String>>();
		for(int hour = 0; hour < 24; hour++) {
			Map<Date, String> days = new TreeMap<Date, String>();
			slots.put(hour, days);
			for(int day = 0; day < 7; day++) {
				cal.add(Calendar.DAY_OF_YEAR, 1);
				days.put(cal.getTime(), "");
			}
			cal.add(Calendar.DAY_OF_YEAR, -7);
		}
		ServletActionContext.getRequest().setAttribute("slots", slots);

		return SUCCESS;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
