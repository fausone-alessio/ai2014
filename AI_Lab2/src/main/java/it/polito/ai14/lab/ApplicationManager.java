package it.polito.ai14.lab;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationManager implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
    	Sala[] palazzo = new Sala[4];
    	palazzo[0] = new Sala();
    	palazzo[0].setNome("S1");
    	palazzo[1] = new Sala();
    	palazzo[1].setNome("S2");
    	palazzo[2] = new Sala();
    	palazzo[2].setNome("S3");
    	palazzo[3] = new Sala();
    	palazzo[3].setNome("S4");
    	
    	// Ho messo una prenotazione di Alessio in sala 4 alle 11 del 2/04/2014
    	// per fare un po' di prove.
    	ConcurrentHashMap <Date, String> p = palazzo[3].getPrenotazioni();
		Calendar calendario = new GregorianCalendar();
		calendario.clear();
		calendario.set(Calendar.YEAR, 2014);
		calendario.set(Calendar.MONTH, Calendar.APRIL);
		calendario.set(Calendar.DAY_OF_MONTH, 2);
		calendario.set(Calendar.HOUR_OF_DAY, 11);
		calendario.set(Calendar.MINUTE, 00);
		p.put(calendario.getTime(), "alessio");
		palazzo[3].setPrenotazioni(p);
		
        sce.getServletContext().setAttribute("palazzo", palazzo);
    }


    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
