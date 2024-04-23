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

@WebServlet("/change")
public class ChangeUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://faure.cs.colostate.edu:3306/tste";
    private static final String JDBC_USERNAME = "tste";
    private static final String JDBC_PASSWORD = "835460928";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUserName = request.getParameter("newUserName");
        String oldUserID = request.getParameter("oldUserID");
        String newUserType = request.getParameter("newUserType");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> searchResult = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            // Set auto-commit mode
            connection.setAutoCommit(true);

            // Prepare SQL statement
            String sql = "UPDATE Users SET UserName = ?, UserType = ? WHERE UserID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, newUserName);
            statement.setString(2, newUserType);
            statement.setString(3, oldUserID);

            // Execute update
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // If update successful, retrieve updated user details
                sql = "SELECT * FROM Users WHERE UserID = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, oldUserID);
                resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    int userId = resultSet.getInt("UserID");
                    String userName = resultSet.getString("UserName");
                    String userType = resultSet.getString("UserType");
                    User user = new User(userId, userName, userType);
                    searchResult.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
                // Rollback the transaction if an error occurs
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
