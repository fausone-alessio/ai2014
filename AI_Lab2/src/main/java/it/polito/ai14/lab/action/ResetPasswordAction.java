package it.polito.ai14.lab.action;

import it.polito.ai14.lab.Costanti;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String password1;
	private String password2;
	
	public String execute() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		if (session.containsKey("orario")) 
			return SUCCESS;
		return cambiaPassword((String) session.get("username"), password1);
	}
	
	public void validate() {
		if (password1 == null || password1.trim().length() == 0)
			addFieldError("password1", "Password non corretta");
		if (password2 == null || password1.trim().length() == 0)
			addFieldError("password2", "Password non corretta");
		if (password1 != null && !password1.equals(password2))
			addActionError("reject");
	}
	
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	public String cambiaPassword(String username, String newPassword) {
    	Map<String, Object> session = ActionContext.getContext().getSession();
		ArrayList<String> lines = new ArrayList<String>();
		String line = "";
		String[] tupla;
		String risultato = INPUT;
		String ruolo = "A";
		
		try {
			File f = new File(Costanti.dbUtenti);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				tupla = line.split(";");
				if (tupla[Costanti.UTENTE].equals(username)) {
					if (tupla[Costanti.PWD].equals(newPassword)) {
						risultato = INPUT;
						lines.add(line);
					}	
					else {
						String credenziali = tupla[Costanti.UTENTE] + ";" + newPassword + ";" + new Date().getTime() + ";" + tupla[Costanti.RUOLO];
						lines.add(credenziali);
						long orario = new Date().getTime() / 1000;
						session.put("username", username);
						session.put("ruolo", ruolo);
						session.put("orario", orario);
						risultato = SUCCESS;
					}
				}
				else
					lines.add(line);
			}	
			
			fr.close();
			br.close();
			
			FileWriter fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                 out.write(String.format(s + "%n"));
            
            out.flush();
            out.close();
            
            return risultato;
            
		} catch (Exception e) {
			risultato = INPUT;
		}
		
		return risultato;
	}

}
