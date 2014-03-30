package it.polito.ai.es1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns="/download/*")
public class FileDownloader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FILE_PATH = "/WEB-INF/files/";
	private static final int BUF_SIZE = 64*1024;
	
    public FileDownloader() {
    
    }
    
	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init(ServletConfig c) throws ServletException {
		super.init();
		c.getServletContext().setAttribute("access", new ConcurrentHashMap<String,Calendar>());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("filename");
		if(filename == null || filename.length() == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				System.out.println("SC_BAD_REQUEST");
			return;
		}
		
		HttpSession session = request.getSession();
		if (session == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String domanda = (String) session.getAttribute("domanda");
		String risposta = request.getParameter("captcha");
		if (domanda != null && risposta != null && !domanda.equals(risposta)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;	
		}
		
		InputStream input = request.getServletContext().getResourceAsStream(FILE_PATH+filename);
		System.out.println(filename);
		if(input == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			System.out.println("SC_NOT_FOUND");
			return;
		}
		
		ConcurrentMap<String,Calendar> access = (ConcurrentMap<String,Calendar>) request.getServletContext().getAttribute("access");
		String ip = request.getRemoteAddr();
		Calendar anHourAgo = Calendar.getInstance();
		anHourAgo.add(Calendar.HOUR, -1);
		if(access.containsKey(ip) && access.get(ip).after(anHourAgo)) {
			PrintWriter writer = response.getWriter();
			Long wait = access.get(ip).getTimeInMillis()-anHourAgo.getTimeInMillis();
			wait = wait/(1000*60);
			writer.println("IP: "+ip+" will have to wait "+wait+" minutes to make another download");
			writer.flush();
			return;
		} else {
			access.put(ip, Calendar.getInstance());
		}
		
		response.setContentType("application/force-download");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
		
		OutputStream out = response.getOutputStream();
		byte[] buf = new byte[BUF_SIZE];
		int length;
		while((length = input.read(buf)) > 0) {
			out.write(buf, 0, length);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			out.flush();
		}
		input.close();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
