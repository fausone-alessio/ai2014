package it.polito.ai14.lab.action;

import it.polito.ai14.lab.Sala;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    	Date oggi = Calendar.getInstance().getTime();
    
		Calendar scelta = new GregorianCalendar();
		scelta.clear();
		scelta.set(Calendar.YEAR, Integer.parseInt(year));
		scelta.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		scelta.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		scelta.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime));
		
		// Scarta le prenotazioni nel passato
    	if (oggi.compareTo(scelta.getTime()) > 0) {
			return ERROR;	
    	}
    	
    	// Scarta le prenotazioni fatte in uno slot già occupato
    	ConcurrentHashMap <Date, String> tmpPrenotazioni = palazzo[Integer.parseInt(room)].getPrenotazioni();
    	if (tmpPrenotazioni.containsKey(scelta.getTime())) {
			return ERROR;
    	}
    	
    	int nPrenotazioni = 0;
    	for (Sala s: palazzo) {
    		ConcurrentHashMap <Date, String> prenotazioni = s.getPrenotazioni();
    		Set <Map.Entry<Date, String>> log = prenotazioni.entrySet();
    		Iterator <Map.Entry<Date, String>> it = log.iterator();
    		while (it.hasNext()) {
    			Map.Entry<Date, String> riga = it.next();
    			Date dataPrenotazione = riga.getKey(); 
    			String utentePrenotazione = riga.getValue();
    			
    			if (utentePrenotazione.equals(utente) && dataPrenotazione.compareTo(oggi) > 0) {
    				nPrenotazioni++;
    				if (nPrenotazioni == 4) {
    					addActionError("Hai più di quattro prenotazioni pendenti");
    					return ERROR;
    				}
    			}

    		}
    	}
    	
		ConcurrentHashMap <Date, String> prenotazioni = palazzo[Integer.parseInt(room)].getPrenotazioni();
    	prenotazioni.putIfAbsent(scelta.getTime(), utente);
    	palazzo[Integer.parseInt(room)].setPrenotazioni(prenotazioni);
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
			addActionError("Data non valida");
		}
		
		if (Integer.parseInt(room) < 0 || Integer.parseInt(room) > 3) {
			addActionError("Stanza non valida");
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
