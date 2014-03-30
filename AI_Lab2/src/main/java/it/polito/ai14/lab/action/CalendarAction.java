package it.polito.ai14.lab.action;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class CalendarAction extends ActionSupport {
	private static final long serialVersionUID = 2777557863534616656L;
	private Integer startTime;
	private Integer endTime;
	private Integer day;
	private Integer month;
	private Integer year;
	private Integer week;
	
	public String execute() {

		if(week == null || year == null || week < 1 || week > 53) {
			return ERROR;
		}
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		
		Map<Integer, Map<Date, String>> slots = new TreeMap<Integer, Map<Date,String>>();
		for(int hour=0;hour<24;hour++) {
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

	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
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

}
