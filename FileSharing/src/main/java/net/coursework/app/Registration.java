package net.coursework.app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Registration extends HttpServlet {
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();  
		pw.println("<head><link rel=\"stylesheet\" href=\"style.css\"></head>");

        pw.println("<body>");
        
        pw.println("<div class=\"body\">" +
							"<div class=\"desk\">" +
									"<form action=\"RegistrationPage\" method=\"POST\">" +
											"<h1>Login</h1>" +
											"<input class=\"inputButton\" type=\"text\" name=\"login\">" +
											"<h1>Password</h1>" +
											"<input class=\"inputButton\" type=\"password\" name=\"password\"><br>" +
											"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"registaration\">" +
									"</form>" +
							"</div>" +
					"</div>");
        
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
        
        pw.println("</body>");
        pw.close();
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		
		if (!login.equals("") && !password.equals("")) {
			int id = Main.getDataBase().getUsers().size();
			Main.getDataBase().getUsers().add(new User(id, login, password));
			resp.sendRedirect("\\FileSharing-1.0\\LoginPage?id=" + id + "&login=" + login);
			return;
		}
		
		resp.sendRedirect("\\FileSharing-1.0\\RegistrationPage");
	}
}
