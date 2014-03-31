package it.polito.ai14.lab.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CalendarAction extends ActionSupport {
	
	private static final long serialVersionUID = 2777557863534616656L;
	private Integer year;
	private Integer week;
	private Integer room;
	
	public String execute() {

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, year);
		calendario.set(Calendar.WEEK_OF_YEAR, week);
		calendario.set(Calendar.HOUR_OF_DAY, 8);
		calendario.set(Calendar.MINUTE, 00);
		
		Map <Date, String> slots = new LinkedHashMap<Date, String>();
		for (int ora = 0; ora < 15; ora++) {
			for (int giorno = 0; giorno < 7; giorno++) {
				slots.put(calendario.getTime(), "Lorem ipsum");
				calendario.add(Calendar.DAY_OF_YEAR, 1);
			}
			calendario.add(Calendar.HOUR_OF_DAY, 1);
			calendario.set(Calendar.WEEK_OF_YEAR, week);
		}
		ServletActionContext.getRequest().setAttribute("slots", slots);
		ServletActionContext.getRequest().setAttribute("nextWeek", getNextWeek());
		ServletActionContext.getRequest().setAttribute("nextYear", getNextYear());
		ServletActionContext.getRequest().setAttribute("prevWeek", getPrevWeek());
		ServletActionContext.getRequest().setAttribute("prevYear", getPrevYear());
		ActionContext.getContext().getSession().put("week", week);
		ActionContext.getContext().getSession().put("year", year);
		
		

		ServletActionContext.getRequest().setAttribute("slots", slots);
		return SUCCESS;
	}
	
	public void validate() {
		if(week == null || week < 1 || week > 53)
			week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		
		if(year == null || year < Calendar.getInstance().get(Calendar.YEAR))
			year = Calendar.getInstance().get(Calendar.YEAR);
		
		if(room == null)
			room = 1;
	}
	
	private Integer getPrevWeek() {
		if ((week - 1) <= 0){
			return getLastWeekOfYear(year - 1);
		} else {
			return week - 1;
		}
	}
	
	private Integer getPrevYear() {
		if ((week - 1) <= 0){
			return year - 1;
		} else {
			return year;
		}
	}
	
	private Integer getNextWeek() {
		if ((week + 1) > getLastWeekOfYear(year)){
			return 1;
		} else {
			return week + 1;
		}
	}
	
	private Integer getNextYear() {
		if ((week + 1) > getLastWeekOfYear(year)){
			return year + 1;
		} else {
			return year;
		}
	}
	
	private Integer getLastWeekOfYear(Integer year) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.MONTH,11);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.YEAR, year);
		return cal.get(Calendar.WEEK_OF_YEAR);
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
	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

}
