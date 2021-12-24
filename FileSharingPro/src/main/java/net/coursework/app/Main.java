package net.coursework.app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.util.ArrayList;

public class Main extends HttpServlet {
	private static DataBase dataBase;
  
	@Override
	public void init() {
		dataBase = new DataBase("FileSharingPro-1.0-DataBase.xml");
		File repository = new File("FileSharingPro-1.0-Repository");
		if(!repository.exists()) {
			repository.mkdir();
		}
		File zip = new File("FileSharingPro-1.0-Zip");
		if(!zip.exists()) {
			zip.mkdir();
		}
	}
  
	@Override
	public void destroy() {
		dataBase.saveDataBase();
	}
  
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.println("<head><link rel=\"stylesheet\" href=\"style.css\"></head>");
    
		pw.println("<body>");
    
		pw.println("<div id=\"head\">");
		pw.println("<a href=\"MainPage\">MainPage</a>");
		pw.println("<a href=\"GeneralDirectoryPage\">GeneralDirectoryPage</a>");
    
		HttpSession session = req.getSession();
		String login = (String)session.getAttribute("login");
		if (login != null) {
			pw.println("<a href=\"LogoutPage\">LogoutPage</a>" +
						"<a href=\"MyDirectoryPage\">MyDirectoryPage</a>" +
						"<a href=\"MessagesPage\">MessagesPage</a>");
			pw.println("<h2>Hello, " + login + "</h2>");
		} else {
			pw.println("<a href=\"LoginPage\">LoginPage</a>");
			pw.println("<a href=\"RegistrationPage\">RegistrationPage</a>");
		}
		pw.println("</div>");
		
		pw.println("<div class=\"body\">");
		ArrayList<Chat> chats = Main.getDataBase().getChats();		
		for (int i = 0; i < chats.size(); i++) {
			ArrayList<Integer> participants = chats.get(i).getParticipants();
			if (participants.contains(0)) {
				String participantsString = "Announcement";
				
				ArrayList<ChatObject> chatObjects = chats.get(i).getChatObjects();
				for (int j = 0; j < chatObjects.size(); j++) {
					pw.println("<div class=\"desk\">" +
									"<h1>" + participantsString + "</h1>" +
									"<h6>" + chatObjects.get(j).getTime() + "</h6>" +
									"<p>" + chatObjects.get(j).getMessage() + "</p>" +
								"</div>");
				}
			}
		}
		pw.println("</div>");
		
		pw.println("</body>");
		pw.close();
	}
  
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		HttpSession session = req.getSession();
		String login = (String)session.getAttribute("login");
		if (login == null) { 
			resp.sendRedirect("\\FileSharingPro-1.0\\MainPage");
			return; 
		}
			
		resp.sendRedirect("\\FileSharingPro-1.0\\MainPage");
	}
  
	public static DataBase getDataBase() {
		return dataBase;
	}
}
