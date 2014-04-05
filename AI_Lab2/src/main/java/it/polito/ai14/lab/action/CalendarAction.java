package it.polito.ai14.lab.action;

import it.polito.ai14.lab.Sala;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class CalendarAction extends ActionSupport {
	
	private static final long serialVersionUID = 2777557863534616656L;
	private Integer day;
	private Integer month;
	private Integer year;
	private Integer room;
	
	private String jump;
	private int spiazzamento;
	
	public String execute() {
		ServletContext servletContext = ServletActionContext.getServletContext();  
		Sala[] palazzo = (Sala[]) servletContext.getAttribute("palazzo");
		
		Calendar calendario = new GregorianCalendar();
		calendario.clear();
		calendario.set(Calendar.YEAR, year);
		calendario.set(Calendar.MONTH, month - 1);
		calendario.set(Calendar.DAY_OF_MONTH, day);
		calendario.set(Calendar.HOUR_OF_DAY, 8);
		
		calendario.add(Calendar.DAY_OF_YEAR, spiazzamento);
		year = calendario.get(Calendar.YEAR);
		month = calendario.get(Calendar.MONTH) + 1;
		day = calendario.get(Calendar.DAY_OF_MONTH);
		
		String prenotante = "";
		Map <Date, String> slots = new LinkedHashMap<Date, String>();
		for (int ora = 0; ora < 15; ora++) {
			for (int giorno = 0; giorno < 7; giorno++) {
				if (palazzo[room].getPrenotazioni().containsKey(calendario.getTime()))
					prenotante = palazzo[room].getPrenotazioni().get(calendario.getTime());
				else
					prenotante = "";
				slots.put(calendario.getTime(), prenotante);
				calendario.add(Calendar.DAY_OF_YEAR, 1);
			}
			calendario.add(Calendar.HOUR_OF_DAY, 1);
			calendario.set(Calendar.YEAR, year);
			calendario.set(Calendar.MONTH, month - 1);
			calendario.set(Calendar.DAY_OF_MONTH, day);
		}
		ServletActionContext.getRequest().setAttribute("slots", slots);
		ServletActionContext.getRequest().setAttribute("room", room);
		
		return SUCCESS;
	}
	
	public void validate() {
		Calendar oggi = Calendar.getInstance();
		
		if (jump != null && !jump.isEmpty()) {
			if (!jump.equalsIgnoreCase("prev") && !jump.equalsIgnoreCase("next"))
				spiazzamento = 0;
			if (jump.equalsIgnoreCase("prev"))
				spiazzamento = -7;
			if (jump.equalsIgnoreCase("next"))
				spiazzamento = 7;
		}
		else spiazzamento = 0;
		
		if(year == null)
			year = oggi.get(Calendar.YEAR);
		if(month == null)
			month = oggi.get(Calendar.MONTH);
		if(day == null)
			day = oggi.get(Calendar.DAY_OF_MONTH);
		
		Calendar scelta = new GregorianCalendar();
		scelta.clear();
		scelta.set(Calendar.YEAR, year);
		scelta.set(Calendar.MONTH, month);
		scelta.set(Calendar.DAY_OF_MONTH, day);
		
		if (scelta.getTimeInMillis() < oggi.getTimeInMillis()) {	
			year = oggi.get(Calendar.YEAR);
			month = oggi.get(Calendar.MONTH) + 1;
			day = oggi.get(Calendar.DAY_OF_MONTH);
		}
		
		if(room == null)
			room = 1;
	}
	


	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getJump() {
		return jump;
	}
	public void setJump(String jump) {
		this.jump = jump;
	}
	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

}
