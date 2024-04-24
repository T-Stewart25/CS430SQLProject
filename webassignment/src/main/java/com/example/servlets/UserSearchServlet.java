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

import com.example.model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class UserSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password
	private static final String JDBC_URL = "jdbc:mysql://faure.cs.colostate.edu:3306/tste";
    private static final String JDBC_USERNAME = "tste";
    private static final String JDBC_PASSWORD = "835460928";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userName = request.getParameter("userName");
    String userId = request.getParameter("userId");
    List<User> searchResult = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e){
        e.printStackTrace();
    }

    try{

        connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

        String sql = null;
        // Determine which parameter is provided and prepare SQL statement accordingly
        if (userId != null && !userId.isEmpty()) {
            sql = "SELECT * FROM Users WHERE UserID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(userId));
        } else if (userName != null && !userName.isEmpty()) {
            sql = "SELECT * FROM Users WHERE UserName LIKE ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + userName + "%");
        } else {
            // If neither is provided, handle it (possibly throw an exception or return an error)
            throw new IllegalArgumentException("No valid search parameter provided.");
        }

        // Execute the query
        resultSet = statement.executeQuery();

        // Process the results
        while (resultSet.next()) {
            int retrievedUserId = resultSet.getInt("UserID");
            String name = resultSet.getString("UserName");
            String userType = resultSet.getString("UserType");
            System.out.println("Student ID: "+ retrievedUserId + "\nStudent Name: " + name + "\nUser Type: " + userType);
            User user = new User(retrievedUserId, name, userType);
            searchResult.add(user);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Handle the case where userId is not a valid integer
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // Handle the case where no valid parameter is provided
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

