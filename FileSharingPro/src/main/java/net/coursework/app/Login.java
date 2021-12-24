package net.coursework.app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

public class Login extends HttpServlet {
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        
        String id = req.getParameter("id");
		String login = req.getParameter("login");
		
		if (id == null) {
			id = "";
		}
		
		if (login == null) {
			login = "";
		}
        
        PrintWriter pw = resp.getWriter();
		pw.println("<head><link rel=\"stylesheet\" href=\"style.css\"></head>");

        pw.println("<body>");
        
        pw.println("<div class=\"body\">" +
							"<div class=\"desk\">" +
									"<form action=\"LoginPage\" method=\"POST\">" +
											"<h1>Id</h1>" +
											"<input class=\"inputButton\" type=\"text\" name=\"id\" value=\"" + id + "\">" +
											"<h1>Login</h1>" +
											"<input class=\"inputButton\" type=\"text\" name=\"login\" value=\"" + login + "\">" +
											"<h1>Password</h1>" +
											"<input class=\"inputButton\" type=\"password\" name=\"password\"><br>" +
											"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"login\">" +
									"</form>" +
							"</div>" +
					"</div>");
        
        pw.println("<div id=\"head\">");
		pw.println("<a href=\"MainPage\">MainPage</a>");
		pw.println("<a href=\"GeneralDirectoryPage\">GeneralDirectoryPage</a>");
		pw.println("<a href=\"LoginPage\">LoginPage</a>");
		pw.println("<a href=\"RegistrationPage\">RegistrationPage</a>");
		pw.println("</div>");
        
        pw.println("</body>");
        pw.close();
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		String id = req.getParameter("id");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
			
		ArrayList<User> users = Main.getDataBase().getUsers(); 
		for (int i = 0; i < users.size(); i++) {
			if (id.equals(Integer.toString(users.get(i).getId())) && login.equals(users.get(i).getName()) && password.equals(users.get(i).getPassword())) {
				HttpSession session = req.getSession();
				session.setAttribute("id", id);
				session.setAttribute("login", login);
			}
		}
		resp.sendRedirect("\\FileSharingPro-1.0\\MainPage");
	}
}
