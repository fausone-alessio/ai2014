package it.polito.ai14.lab.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class SecurityInterceptor implements Interceptor {
	private static final long serialVersionUID = 5367228552868778914L;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
    	Map<String, Object> session = ActionContext.getContext().getSession();
    	
    	String utente = (String) session.get("username");

	    if (utente == null) {
	        return ActionSupport.LOGIN;
	    } else {
	        return invocation.invoke ();
	    }
	}

}
