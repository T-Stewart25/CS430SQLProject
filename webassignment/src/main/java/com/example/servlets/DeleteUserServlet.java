package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://faure.cs.colostate.edu:3306/tste";
    private static final String JDBC_USERNAME = "tste";
    private static final String JDBC_PASSWORD = "835460928";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = request.getParameter("userID");
        boolean searchSuccess = false;
        boolean deletionSuccess = false;
        boolean deletionSuccessFinal = false;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Prepare SQL statement for search
            String sqlSearch = "SELECT * FROM Users WHERE UserID = ?";
            statement = connection.prepareStatement(sqlSearch);
            statement.setString(1, userID);

            // Execute search query
            resultSet = statement.executeQuery();

            searchSuccess = resultSet.next(); // Check if user exists

            if (searchSuccess) {
                // Prepare SQL statement for search
                sqlSearch = "SELECT * FROM Uses WHERE UserID = ?";
                statement = connection.prepareStatement(sqlSearch);
                statement.setString(1, userID);

                // Execute search query
                resultSet = statement.executeQuery();

                boolean searchSuccess2 = resultSet.next(); // Check if user exists
                if (searchSuccess2) {
                    // Prepare SQL statement for deletion
                    String sqlDelete = "DELETE FROM Uses WHERE UserID = ?";
                    statement = connection.prepareStatement(sqlDelete);
                    statement.setString(1, userID);

                    // Execute deletion query
                    int rowsAffected = statement.executeUpdate();
                }


                // Prepare SQL statement for deletion
                String sqlDelete = "DELETE FROM Users WHERE UserID = ?";
                statement = connection.prepareStatement(sqlDelete);
                statement.setString(1, userID);

                // Execute deletion query
                int rowsAffected = statement.executeUpdate();
                deletionSuccessFinal = rowsAffected > 0; // Check if deletion was successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"searchSuccess\": " + searchSuccess + ", \"deletionSuccessFinal\": " + deletionSuccessFinal + "}");
        out.flush();
    }
}
