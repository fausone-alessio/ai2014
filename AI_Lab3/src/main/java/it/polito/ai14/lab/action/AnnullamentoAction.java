package it.polito.ai14.lab.action;

import it.polito.ai14.lab.Sala;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AnnullamentoAction extends ActionSupport {
	private String room;
	private String day;
	private String month;
	private String year;
	private String startTime;
	
	public String execute() {
		ServletContext servletContext = ServletActionContext.getServletContext();  
    	Map<String, Object> session = ActionContext.getContext().getSession();
    	
    	String utente = (String) session.get("username");
    	Sala[] palazzo = (Sala[]) servletContext.getAttribute("palazzo");
    
		Calendar scelta = new GregorianCalendar();
		scelta.clear();
		scelta.set(Calendar.YEAR, Integer.parseInt(year));
		scelta.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		scelta.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		scelta.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime));
		
		ConcurrentHashMap <Date, String> prenotazioni = palazzo[Integer.parseInt(room)].getPrenotazioni();
		if (prenotazioni.containsKey(scelta.getTime())) {
			String utentePrenotazione = prenotazioni.get(scelta.getTime());
			if (utentePrenotazione.equals(utente)) {
				prenotazioni.remove(scelta.getTime());
				palazzo[Integer.parseInt(room)].setPrenotazioni(prenotazioni);
			}
		}
		return SUCCESS;
	}
	
	public void validate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		sdf.setLenient(false);
		try {
			@SuppressWarnings("unused")
			Date date = sdf.parse(day + "/" + month + "/" + year);
		}
		catch (ParseException e) {
			addFieldError("year", "Data non valida");
		}
		
		if (Integer.parseInt(room) < 0 || Integer.parseInt(room) > 3) {
			System.out.println("Ciao belli");
			addFieldError("room", "Stanza non valida");
		}		
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
