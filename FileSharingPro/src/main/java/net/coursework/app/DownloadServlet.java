package net.coursework.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.net.URLConnection;

import java.util.ArrayList;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@MultipartConfig
public class DownloadServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String packageName = req.getParameter("packageName");
		
		File folder = new File("FileSharingPro-1.0-Repository//" + packageName);
		InputStream inputStream = req.getInputStream();
		ArrayList<String> filesName = MyDirectory.listFilesForFolder(folder);
		
		try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("FileSharingPro-1.0-Zip//" + packageName + ".zip"))) {
			for (String filePath : filesName) {
				File fileToZip = new File("FileSharingPro-1.0-Repository//" + packageName + "//" + filePath);
				zipOut.putNextEntry(new ZipEntry(fileToZip.getName()));
				Files.copy(fileToZip.toPath(), zipOut);
			}
		}
		
		File file = new File("FileSharingPro-1.0-Zip//" + packageName + ".zip");
		
		OutputStream outputStream = resp.getOutputStream();
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		String contentDisposition = String.format("attachment; filename=%s", file.getName());
		int fileSize = Long.valueOf(file.length()).intValue();

		resp.setContentType(mimeType);
		resp.setHeader("Content-Disposition", contentDisposition);
		resp.setContentLength(fileSize);
		
		Files.copy(file.toPath(), outputStream);
		outputStream.flush();
	}
}
