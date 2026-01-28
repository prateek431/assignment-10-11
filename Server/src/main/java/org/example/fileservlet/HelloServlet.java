package org.example.fileservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


import java.io.*;
import java.nio.file.*;
import java.time.Instant;

@MultipartConfig
public class HelloServlet extends HttpServlet {
    private static final String AUTH_TOKEN = "1403";
    private static final String UPLOAD_DIR = "C:\\Servlet_Projects\\HTTPCLient\\src\\directory";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.equals("prateek " + AUTH_TOKEN)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized");
            return;
        }

        Part filePart = request.getPart("file");
        String originalFileName = Paths.get(filePart.getSubmittedFileName())
                .getFileName().toString();
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        Path targetFile = Paths.get(UPLOAD_DIR, originalFileName);
        if (Files.exists(targetFile)) {
            String timestamp = String.valueOf(Instant.now().toEpochMilli());
            String newName = timestamp + "-" + originalFileName;
            targetFile = Paths.get(UPLOAD_DIR, newName);
        }
        try (InputStream in = filePart.getInputStream()) {
            Files.copy(in, targetFile, StandardCopyOption.REPLACE_EXISTING);
        }
        response.getWriter().println("File saved as: " + targetFile.getFileName());
    }
}
