package it.polito.ai14.lab.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	public String execute() {
    	Map<String, Object> session = ActionContext.getContext().getSession();
    	if (session.containsKey("orario")) {
    		session.remove("username");
    		session.remove("ruolo");
    		session.remove("orario");
    	}
    	return SUCCESS;
    }
	
}
