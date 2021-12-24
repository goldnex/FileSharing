package net.coursework.app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeneralDirectory extends HttpServlet {
	public static String getFileChecksum(MessageDigest digest, File file) throws IOException {
		//Get file input stream for reading the file content
		FileInputStream fis = new FileInputStream(file);
		 
		//Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0; 
		  
		//Read file data and update in message digest
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}
		 
		//close the stream; We don't need it now.
		fis.close();
		 
		//Get the hash's bytes
		byte[] bytes = digest.digest();
		 
		//This bytes[] has bytes in decimal format;
		//Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for(int i=0; i< bytes.length ;i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		 
		//return complete hash
	   return sb.toString();
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

		String filter = req.getParameter("filter");
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
		
		pw.println("<div class=\"desk\">" +
						"<form action=\"GeneralDirectoryPage\" method=\"POST\">" +
							"<h1>Search package</h1>" +
								"<input class=\"inputButton\" type=\"text\" name=\"searchPackage\">" +
								"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"search\">" +
						"</form>" +
					"</div>");
		ArrayList<Package> packages = Main.getDataBase().getPackages();
		if (filter != null) {
			for (int i = 0; i < packages.size(); i++) {
				if (packages.get(i).getPath().lastIndexOf(filter) != -1) {
					if (packages.get(i).getPath().lastIndexOf("[hidden]") != -1) {
						continue;
					}
					pw.println("<div class=\"desk\">");
					pw.println("<h1>" + Main.getDataBase().getUserById(packages.get(i).getId()) + "@" + packages.get(i).getId() + ": " + packages.get(i).getPath() + "</h1>");
					File file = new File("FileSharingPro-1.0-Zip//" + packages.get(i).getPath() + ".zip");
					if(file.exists()) {
						try {
							MessageDigest md5 = MessageDigest.getInstance("MD5");
							MessageDigest sha256 = MessageDigest.getInstance("SHA256");
							String md5CheckSum = getFileChecksum(md5, file);
							String sha256CheckSum = getFileChecksum(sha256, file);
							pw.println("<p>MD5: " + md5CheckSum + "<p>");
							pw.println("<p>SHA256: " + sha256CheckSum + "<p>");
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
					}
					pw.println("<a href=\"DownloadServletPage?packageName=" + packages.get(i).getPath() + "\">download</a>");
					pw.println("</div>");
				}
			}
		} else {
			for (int i = 0; i < packages.size(); i++) {
				pw.println("<div class=\"desk\">");
				pw.println("<h1>" + Main.getDataBase().getUserById(packages.get(i).getId()) + "@" + packages.get(i).getId() + ": " + packages.get(i).getPath() + "</h1>");
				File file = new File("FileSharingPro-1.0-Zip//" + packages.get(i).getPath() + ".zip");
				if(file.exists()) {
					try {
						MessageDigest md5 = MessageDigest.getInstance("MD5");
						MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
						String md5CheckSum = getFileChecksum(md5, file);
						String sha256CheckSum = getFileChecksum(sha256, file);
						pw.println("<p>MD5: " + md5CheckSum + "<p>");
						pw.println("<p>SHA256: " + sha256CheckSum + "<p>");
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				}
				pw.println("<a href=\"DownloadServletPage?packageName=" + packages.get(i).getPath() + "\">download</a>");
				pw.println("</div>");
			}
		}
		pw.println("</div>");
		
		pw.println("</body>");
        pw.close();
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String searchPackage = req.getParameter("searchPackage");
		if (searchPackage != null) {
			resp.sendRedirect("\\FileSharingPro-1.0\\GeneralDirectoryPage?filter=" + searchPackage);
			return;
		}
		resp.sendRedirect("\\FileSharingPro-1.0\\GeneralDirectoryPage");
	}
}
