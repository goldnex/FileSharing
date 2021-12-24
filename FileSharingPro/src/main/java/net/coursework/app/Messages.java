package net.coursework.app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;

public class Messages extends HttpServlet {	
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
		pw.println("<head><link rel=\"stylesheet\" href=\"style.css\"></head>");
        
        pw.println("<body>");
        HttpSession session = req.getSession();
        String idSession = (String)session.getAttribute("id");
		String loginSession = (String)session.getAttribute("login");
		if (idSession == null || loginSession == null) {
			resp.sendRedirect("\\FileSharingPro-1.0\\MainPage");
		}
		
		pw.println("<body>");
		pw.println("<div id=\"head\">" +
					"<a href=\"MainPage\">MainPage</a>" +
					"<a href=\"GeneralDirectoryPage\">GeneralDirectoryPage</a>" +
					"<a href=\"LogoutPage\">LogoutPage</a>" +
					"<a href=\"MyDirectoryPage\">MyDirectoryPage</a>" +
					"<a href=\"MessagesPage\">MessagesPage</a>" +
					"<h2>Hello, " + loginSession + "</h2>" +
					"</div>");
		
        
        pw.println("<div class=\"body\">" +
							"<div class=\"desk\">" +
									"<form action=\"MessagesPage\" method=\"POST\">" +
											"<h1>id</h1>" +
											"<input class=\"inputButton\" type=\"text\" name=\"id\">" +
											"<h1>Message</h1>" +
											"<input class=\"inputButton\" type=\"text\" name=\"message\"><br>" +
											"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"send\">" +
									"</form>" +
							"</div>" +
					"</div>");
		
		ArrayList<Chat> chats = Main.getDataBase().getChats();		
		for (int i = 0; i < chats.size(); i++) {
			ArrayList<Integer> participants = chats.get(i).getParticipants();
			if (participants.contains(Integer.parseInt(idSession))) {
				String participantsString = "Members: ";
				for (int j = 0; j < participants.size(); j++) {
					participantsString += Main.getDataBase().getUserById(participants.get(j)) + "@" + Integer.toString(participants.get(j)) + " ";
				}
				
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
        
        pw.println("</body>");
        pw.close();
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");

		HttpSession session = req.getSession();
		String idSession = (String)session.getAttribute("id");
		String id = req.getParameter("id");
		String message = req.getParameter("message");
		String fullMessage = Main.getDataBase().getUserById(Integer.parseInt(idSession)) + "@" + idSession + ": " + message;
		
		if (!id.equals("") && !message.equals("") && (!id.equals(idSession) || idSession.equals("0")) && (!Main.getDataBase().getUserById(Integer.parseInt(id)).equals(""))) {
			ArrayList<Chat> chats = Main.getDataBase().getChats();
			for (int i = 0; i < chats.size(); i++) {
				ArrayList<Integer> participants = chats.get(i).getParticipants();
				if (participants.contains(Integer.parseInt(idSession)) && participants.contains(Integer.parseInt(id))) {
					Date date = new Date();
					chats.get(i).addMessage(Integer.parseInt(idSession), fullMessage, date.toString());
					resp.sendRedirect("\\FileSharingPro-1.0\\MessagesPage");
					return;
				}
			}
			ArrayList<Integer> participants = new ArrayList<Integer>();
			participants.add(Integer.parseInt(idSession));
			participants.add(Integer.parseInt(id));
			Chat chat = new Chat(participants);
			Date date = new Date();
			chat.addMessage(Integer.parseInt(idSession), fullMessage, date.toString());
			chats.add(chat);
		}
		
		resp.sendRedirect("\\FileSharingPro-1.0\\MessagesPage");
	}
}
