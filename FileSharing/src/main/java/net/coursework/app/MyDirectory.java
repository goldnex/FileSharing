package net.coursework.app;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@MultipartConfig
public class MyDirectory extends HttpServlet {	
	public static ArrayList<String> listFilesForFolder(final File folder) {
		ArrayList<String> filenames = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			filenames.add(fileEntry.getName());
		}
		return filenames;
	}
	
	public static void recursiveDelete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveDelete(f);
            }
        }
        file.delete();
    }
	
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
			resp.sendRedirect("\\FileSharing-1.0\\MainPage");
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
									"<form action=\"MyDirectoryPage\" method=\"POST\">" +
											"<h1>Name package</h1>" +
											"<input class=\"inputButton\" type=\"text\" name=\"namePackage\">" +
											"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"create\">" +
									"</form>" +
							"</div>" +
					"</div>");
		
		pw.println("<div class=\"body\">");
		pw.println("<form action=\"MyDirectoryPage\" enctype=\"multipart/form-data\" method=\"POST\">");
		ArrayList<Package> packages = Main.getDataBase().getPackages();		
		for (int i = 0; i < packages.size(); i++) {
			if (packages.get(i).getId() == Integer.parseInt(idSession)) {
				File file = new File("FileSharing-1.0-Repository//" + packages.get(i).getPath());
				ArrayList<String> filenames = MyDirectory.listFilesForFolder(file);
				pw.println("<div class=\"desk\">");
				pw.println("<h1>" + packages.get(i).getPath() + "</h1>");
				for (int j = 0; j < filenames.size(); j++) {
					if (filenames.get(j).lastIndexOf(".txt") != -1) {
						pw.println("<p style=\"color:#ff0000\">" + filenames.get(j) + "</p>" +
								"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"delete file\" name=\"#" + packages.get(i).getPath() + "//" + filenames.get(j) + "\">");
					} else if (filenames.get(j).lastIndexOf(".java") != -1) {
						pw.println("<p style=\"color:#00ff00\">" + filenames.get(j) + "</p>" +
								"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"delete file\" name=\"#" + packages.get(i).getPath() + "//" + filenames.get(j) + "\">");
					} else if (filenames.get(j).lastIndexOf(".class") != -1) {
						pw.println("<p style=\"color:#0000ff\">" + filenames.get(j) + "</p>" +
								"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"delete file\" name=\"#" + packages.get(i).getPath() + "//" + filenames.get(j) + "\">");
					} else {
						pw.println("<p>" + filenames.get(j) + "</p>" +
								"<input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"delete file\" name=\"#" + packages.get(i).getPath() + "//" + filenames.get(j) + "\">");
					}
					pw.println("<br>");
				}
				pw.println("<br><br><input class=\"inputButton\" type=\"submit\" value=\"upload\">");
				pw.println("<br><input class=\"inputButton\" type=\"file\" class=\"button\" value=\"add files\" name=\"" + packages.get(i).getPath() + "\">");
				pw.println("<br><br><input class=\"inputButton\" type=\"submit\" class=\"button\" value=\"delete package\" name=\"#" + packages.get(i).getPath() + "\">");
				
				pw.println("</div>");
			}
		}
		pw.println("</form>");
		pw.println("</div>");
        
        pw.println("</body>");
        pw.close();
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();

		HttpSession session = req.getSession();
		String idSession = (String)session.getAttribute("id");
		String namePackage = req.getParameter("namePackage");
		String fullNamePackage = namePackage + "-" + Integer.parseInt(idSession);
		if (namePackage != null) {
			ArrayList<Package> packages = Main.getDataBase().getPackages();
			for (int i = 0; i < packages.size(); i++) {
				if ((packages.get(i).getId() == Integer.parseInt(idSession)) && (packages.get(i).getPath() == fullNamePackage)) {
					resp.sendRedirect("\\FileSharing-1.0\\MyDirectoryPage");
					return;
				}
			}
			File userPackage = new File("FileSharing-1.0-Repository//" + fullNamePackage);
			userPackage.mkdir();
			packages.add(new Package(Integer.parseInt(idSession), fullNamePackage));
			Collections.sort(packages);
			resp.sendRedirect("\\FileSharing-1.0\\MyDirectoryPage");
			return;
		}
		
		ArrayList<Package> packages = Main.getDataBase().getPackages();
		for (int i = 0; i < packages.size(); i++) {
			if (packages.get(i).getId() == Integer.parseInt(idSession)) {
				File filesInFolder = new File("FileSharing-1.0-Repository//" + packages.get(i).getPath());
				ArrayList<String> filenames = listFilesForFolder(filesInFolder);
				for (int j = 0; j < filenames.size(); j++) {
					if (req.getParameter("#" + packages.get(i).getPath() + "//" + filenames.get(j)) != null) {
						File file = new File("FileSharing-1.0-Repository//" + packages.get(i).getPath() + "//" + filenames.get(j));
						recursiveDelete(file);
						resp.sendRedirect("\\FileSharing-1.0\\MyDirectoryPage");
						return;
					}
				}
				if (req.getParameter("#" + packages.get(i).getPath()) != null) {
					File file = new File("FileSharing-1.0-Repository//" + packages.get(i).getPath());
					recursiveDelete(file);
					File zipFile = new File("FileSharing-1.0-Zip//" + packages.get(i).getPath() + ".zip");
					recursiveDelete(zipFile);
					packages.remove(i);
					resp.sendRedirect("\\FileSharing-1.0\\MyDirectoryPage");
					return;
				}
				if (req.getPart(packages.get(i).getPath()).getSize() > 0) {
					Part part = req.getPart(packages.get(i).getPath());
					String fileName = part.getSubmittedFileName();
					String finalLocation = System.getProperty("user.dir") + "//FileSharing-1.0-Repository//" + packages.get(i).getPath() + "//" + fileName;
					part.write(finalLocation);
					resp.sendRedirect("\\FileSharing-1.0\\MyDirectoryPage");
					return;
				}
			}
		}
		resp.sendRedirect("\\FileSharing-1.0\\MyDirectoryPage");
	}
}
