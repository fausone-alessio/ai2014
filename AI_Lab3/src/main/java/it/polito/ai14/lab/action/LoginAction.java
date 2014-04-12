package it.polito.ai14.lab.action;

import it.polito.ai14.lab.Costanti;
import it.polito.ai14.lab.entities.User;
import it.polito.ai14.lab.hibernate.HibernateUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final String FORCE = "force";

	private String username;
	private String password;

	public String execute() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (session.containsKey("orario"))
			return SUCCESS;

		return autenticazione();
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

	private String autenticazione() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		try {
			Session dbsession = HibernateUtils.getSessionFactory().getCurrentSession();
			Query q = dbsession.createQuery("from it.polito.ai14.lab.entities.User as u where u.username = :username and u.password = :password");
			q.setString("username", username);
			q.setString("password", password);
			@SuppressWarnings("unchecked")
			List <User> users = (List <User>) q.list();
			if (users.size() > 0) {
				long orario = (new Date().getTime())/1000;
				// TODO Chiedere se si può salvare l'oggetto
				session.put("username", username);
				session.put("ruolo", "A");
				session.put("orario", orario);
				return SUCCESS;
			}

		}
		catch (Exception e) {
			return ERROR;
		}
		return ERROR;
	}
}
