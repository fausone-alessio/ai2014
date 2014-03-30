package it.polito.ai14.lab;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationManager implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        // per accederci: get application context get attribute modello
    	Sala[] palazzo = new Sala[4];
    	palazzo[0] = new Sala();
    	palazzo[0].setNome("S1");
    	palazzo[1] = new Sala();
    	palazzo[1].setNome("S2");
    	palazzo[2] = new Sala();
    	palazzo[2].setNome("S3");
    	palazzo[3] = new Sala();
    	palazzo[3].setNome("S4");
    	
        sce.getServletContext().setAttribute("palazzo", palazzo);
    }


    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
