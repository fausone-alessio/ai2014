package it.polito.ai14.lab;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Un semplice esempio di captcha", urlPatterns = { "/captcha/captcha.png" })
public class SimpleCaptcha extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final int width = 100;
    private static final int height = 33;
    private static final int DIM = 5;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String testo = "0the1quick2brown3fox4jumps5over6the7lazy8dog9";
		String codice = "";
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    GradientPaint gp = new GradientPaint(0, 0, new Color(63, 72, 204), 0, 33/2, new Color(0, 162, 232), true);
	    g.setPaint(gp);
	    g.fillRect(0, 0, width, height);
	    
	    g.setColor(new Color(255, (int) (255*Math.random()), (int) (255*Math.random())));
	    g.setFont(new Font("Arial", Font.BOLD, 18));
	    for (int i = 0; i < DIM; i++) {
	    	String carattere =  Character.toString(testo.charAt((int) (Math.random() * testo.length())));
	    	codice = codice + carattere;
	    	g.drawString(carattere, 10 + 15*i, height/2 + (int) (height/4 * Math.random()));
	    }
	    g.dispose();
	    request.getSession(true).setAttribute("captcha", codice);
	    response.setContentType("image/png");
	    ImageIO.write(bi, "PNG", response.getOutputStream());
	}

}
