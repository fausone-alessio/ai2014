package it.polito.ai14.lab.action;

import it.polito.ai14.lab.Costanti;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport  {
	private static final long 		serialVersionUID = 1L;
	private static final String 	FORCE = "force";
	
	private String 	username;
	private String	password;
	
    public String execute() {
    	Map<String, Object> session = ActionContext.getContext().getSession();
    	if (session.containsKey("orario"))
    		return SUCCESS;
    	
    	return autenticazione(username, password);	
    }
       
	public void validate() {
		if (username == null || username.trim().length() == 0)
			addFieldError("username", "Inserisci il nome utente");
		if (password == null || password.trim().length() == 0)
			addFieldError("password", "Inserisci la password");
	}
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String autenticazione(String username, String password) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		boolean 	trovato = false;
		String		risultato = ERROR;
		String		utente;
		String[]	tupla;
		String		ruolo = "U";
		
		try {
			InputStream fis = ServletActionContext.getServletContext().getResourceAsStream(Costanti.dbUtenti);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while (!trovato && (utente = br.readLine()) != null) {
				tupla = utente.split(";");
				if (tupla[Costanti.UTENTE].equals(username)) {
					trovato = true;
					ruolo = tupla[Costanti.RUOLO];
					if (tupla[Costanti.PWD].equals(password)) {
						if (tupla[Costanti.RUOLO].equalsIgnoreCase("A")) {
							long oggi = (new Date().getTime())/1000;
							long setup = (Long.parseLong(tupla[Costanti.PWDTIME]))/1000;
							
							long delta = oggi - setup;
							long maxdelta = 30*24*60*60;
							if (delta > maxdelta) {
								risultato = FORCE;
							}
							else
								risultato = SUCCESS;
						}
						else
							risultato = SUCCESS;
					}
				}
			}
			br.close();
			
			if (risultato.equals(SUCCESS)) {
				long orario = (new Date().getTime())/1000;
				session.put("username", username);
				session.put("ruolo", ruolo);
				session.put("orario", orario);
			}
			
			if (risultato.equals(FORCE)) {
				session.put("username", username);
			}
			
			if (risultato.equals(ERROR))
				addActionError("Credenziali errate");
			
			return risultato;
		}
		catch (Exception e) {
			return ERROR;
		}
	}




	
	
}
