package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Uses;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addUsage")
public class UsageAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://faure.cs.colostate.edu:3306/tste";
    private static final String JDBC_USERNAME = "tste";
    private static final String JDBC_PASSWORD = "835460928";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String deviceId = request.getParameter("deviceId");
        String usageDate = request.getParameter("usageDate");
        String usageDuration = request.getParameter("usageDuration");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Uses> searchResult = new ArrayList<>(); // Declaration and initialization of searchResult

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Prepare SQL statement
            String sql = "INSERT INTO Uses (UserID, DeviceId, UsageDate, UsageDuration) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, deviceId);
            statement.setString(3, usageDate);
            statement.setString(4, usageDuration);

            // Execute update (not query)
            int rowsAffected = statement.executeUpdate(); // Use executeUpdate for INSERT statements
            
            if (rowsAffected > 0) {
                // If insertion successful, create User object and add to search result
                Uses uses = new Uses(Integer.parseInt(userId), Integer.parseInt(deviceId), usageDate, usageDuration);
                searchResult.add(uses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (connection != null) connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Convert searchResult to JSON and send as response
        Gson gson = new Gson();
        String jsonResult = gson.toJson(searchResult);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }
}
