package it.polito.ai14.lab;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(description = "Si occupa di reperire i file da disco e di restituirli all'utente", urlPatterns = { "/download.html" })
public class Downloader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger=Logger.getLogger("it.polito.ai14.lab.Downloader");

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Questo pezzo poi si può anche togliere.
		ServletContext sc = getServletContext();
		if (sc.getAttribute("accessi") != null) {
			logger.info("Ritrovata la mappa accessi");			
		}
		
		String file = request.getParameter("file");
		/* Il parametro file esiste? */
		if (file == null) {
			response.sendRedirect("");
			return;			
		}
		/* Il parametro file esiste e non è vuoto? */
		if (file.equals("")) {
			response.sendRedirect("");
			return;
		}
		/* Il parametro file esiste e punta a un file esistente? */
		File controllo = new File ("D:/politecnico/2014/applicazioni internet/workspace/AI_Lab1/WebContent/WEB-INF/files/" + file); 
		if (!controllo.exists()) {
			response.sendRedirect("");
			return;			
		}
		request.getSession(true).setAttribute("file", file);
		
		PrintWriter w = response.getWriter();
		w.println("<html>");
		w.println("<body>");
		w.println("<form name=\"validator\" action=\"download.html\" method=\"POST\">");
		w.println("<img src=\"captcha/captcha.png\"><br>");
		w.println("<input type=\"text\" name=\"risposta\">");
		w.println("<input type=\"submit\" value=\"Scarica il file selezionato\">");
		w.println("</form>");
		w.println("</body>");
		w.println("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession s = request.getSession();
		if (s == null) {
			response.sendRedirect("");
			return;
		}
		
		String domanda = (String) s.getAttribute("captcha");
		String risposta = (String) request.getParameter("risposta");
		
		if (domanda != null && risposta != null && !domanda.equals(risposta)) {
			response.sendRedirect("");
			return;	
		}
		
		ServletContext sc = getServletContext();
		@SuppressWarnings("unchecked")
		ConcurrentHashMap<String, Date> accessi = (ConcurrentHashMap<String, Date>) sc.getAttribute("accessi");
		String richiedente = getIpAddr(request);
		Date ultima;
		try {
			ultima = accessi.putIfAbsent(richiedente, new Date());
		} catch (NullPointerException npe) {
			ultima = null;
		}
		if ((ultima == null) || (ultima != null && new Date().getTime() - ultima.getTime() > (60*60*1000))) {
			String file = (String) request.getSession().getAttribute("file");
			if (file == null) {
				response.sendRedirect("");
				return;
			}
			
			String mimetype = sc.getMimeType("D:/politecnico/2014/applicazioni internet/workspace/AI_Lab1/WebContent/WEB-INF/files/" + file);
			response.setContentType(mimetype);
			response.setHeader("Content-disposition","attachment; filename=" + file);
			
			File richiesto = new File("D:/politecnico/2014/applicazioni internet/workspace/AI_Lab1/WebContent/WEB-INF/files/" + file);
			Long dimensione = richiesto.length();
			response.setHeader("Content-Length", String.valueOf(dimensione));
			
			logger.info("Dimensione del file richiesto: " + dimensione);	
			
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(richiesto);
			byte[] buffer = new byte[65536];
			int length;
			
			/* Download con banda limitata. Guardo quanti nanosecondi ci metto a 
			 * trasferire 65536 B (64 KB) e dormo per il tempo restante fino ad
			 * arrivare a 1 secondo. */
			/* http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-seconds */
			long tick = 0;
			long durata = 0;
			while ((length = in.read(buffer)) > 0){
				tick = System.nanoTime();
				out.write(buffer, 0, length);
				durata = System.nanoTime() - tick;
				
				try {
				    Thread.sleep(1000 - durata/(1000*1000));
				} catch (InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
			}
			in.close();
			out.flush();
		    s.invalidate();
		    return;
		}
		else {
			response.sendRedirect("");
			return;
		}
	}
	
	@Override
	public void init() throws ServletException {
		ServletContext sc = getServletContext();
		if (sc.getAttribute("accessi") == null) {
			ConcurrentHashMap<String, Date> accessi = new ConcurrentHashMap<String, Date>();
			sc.setAttribute("accessi", accessi);
			logger.info("Allocata una nuova mappa accessi");
		}
	}
	
	private String getIpAddr(HttpServletRequest request) {      
		   String ip = request.getHeader("x-forwarded-for");      
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
		       ip = request.getHeader("Proxy-Client-IP");      
		   }      
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
		       ip = request.getHeader("WL-Proxy-Client-IP");      
		   }      
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
		       ip = request.getRemoteAddr();      
		   }      
		   return ip;      
	} 

}
