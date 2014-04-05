package it.polito.ai14.lab.action;

import it.polito.ai14.lab.Sala;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PrenotazioneAction extends ActionSupport {
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
    	int r = Integer.parseInt(room);
    	
		Calendar calendario = new GregorianCalendar();
		calendario.clear();
		calendario.set(Calendar.YEAR, Integer.parseInt(year));
		calendario.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		calendario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		calendario.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime));
		
		ConcurrentHashMap <Date, String> prenotazioni = palazzo[r].getPrenotazioni();
    	prenotazioni.putIfAbsent(calendario.getTime(), utente);
		return SUCCESS;
	}
	
	public void validate() {
		// TODO Cazziare prenotazioni in slot pieni
		ServletContext servletContext = ServletActionContext.getServletContext();  
    	Map<String, Object> session = ActionContext.getContext().getSession();
    	
    	String utente = (String) session.get("username");
    	Sala[] palazzo = (Sala[]) servletContext.getAttribute("palazzo");
    	Date oggi = Calendar.getInstance().getTime();
    	
    	int nPrenotazioni = 0;
    	for (Sala s: palazzo) {
    		ConcurrentHashMap <Date, String> prenotazioni = s.getPrenotazioni();
    		Set <Map.Entry<Date, String>> log = prenotazioni.entrySet();
    		Iterator <Map.Entry<Date, String>> it = log.iterator();
    		while (it.hasNext()) {
    			Map.Entry<Date, String> riga = it.next();
    			Date tempo = riga.getKey(); 
    			String prenotante = riga.getValue();
    			
    			// TODO Sbagliato, devo guardarlo insieme alle prenotazioni, il cazziamento del passato si può fare fuori
    			if (tempo.compareTo(oggi) < 0) {
    				System.out.println("WHADDUP!");
    				addActionError("Non puoi effettuare una prenotazione nel passato.");
    				return;
    			}
    			else {
    				// TODO Cazziare prenotazioni nel passato
    				if (prenotante.equals(utente))
    					nPrenotazioni++;
    				if (nPrenotazioni >= 4) {
        				addActionError("Hai più di quattro prenotazioni pendenti");
        				return;
    				}
    			}
    		}
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
