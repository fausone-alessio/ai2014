package it.polito.ai14.lab;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Megavideo
 */
@WebServlet(description = "Pagina iniziale", urlPatterns = { "/" })
public class Megavideo extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter w = response.getWriter();
		w.println("<html>");
		w.println("<body>");
		w.println("<h1>Megavideo</h1>");
		w.print("<h2>"); w.print(getServletInfo()); w.println("</h2>");
		w.println("<ul>");

		/* Da cambiare il path, non avevo voglia di pensarci */
		File folder = new File("D:/politecnico/2014/applicazioni internet/workspace/AI_Lab1/WebContent/WEB-INF/files");
		File[] listOfFiles = folder.listFiles(); 
		String nome;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				nome = listOfFiles[i].getName();
				w.println("<li><a href=\"download.html?file="+ nome +"\">" + nome + "</a></li>");
			}
		}
		w.println("</ul>");
		w.println("</body>");
		w.println("</html>");

	}
	
	@Override
	public void init() throws ServletException {
	}
	

}
