package it.polito.ai14.lab;

import it.polito.ai14.lab.hibernate.HibernateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Query;
import org.hibernate.Session;

public class ApplicationManager implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
    }


    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
