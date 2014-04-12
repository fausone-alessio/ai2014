package it.polito.ai14.lab.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import it.polito.ai14.lab.entities.User;
import it.polito.ai14.lab.hibernate.HibernateUtils;

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
		try {
			Session dbsession = HibernateUtils.getSessionFactory().getCurrentSession();
			Query q = dbsession.createQuery("from it.polito.ai14.lab.entities.User as u where u.username = :username");
			q.setString("username", username);
			@SuppressWarnings("unchecked")
			List <User> users = (List <User>) q.list();
			if (users.size() == 1) {
				String oldPassword = users.get(0).getPassword();
				if (oldPassword.compareTo(newPassword) != 0) {
					users.get(0).setPassword(newPassword);
					// TODO Settare la nuova data nel database
					// TODO Settare il resto dei parametri nella sessione
					session.put("orario", new Date().getTime() / 1000);
				}
				else return INPUT;
			}
			else return ERROR;
		}
		catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

}
