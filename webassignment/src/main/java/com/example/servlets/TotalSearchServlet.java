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

import com.example.model.Total;
import com.example.model.Device;
import com.example.model.User;
import com.example.model.Uses;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class TotalSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password
	private static final String JDBC_URL = "jdbc:mysql://faure.cs.colostate.edu:3306/tste";
    private static final String JDBC_USERNAME = "tste";
    private static final String JDBC_PASSWORD = "835460928";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = request.getParameter("userId");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    List<Total> searchResult = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    try {
        // Establish database connection
         connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

        // Prepare SQL statement
        String sql = "SELECT u.UserID, u.UserName, u.UserType, d.DeviceName, d.DeviceType, SUM(us.UsageDuration) AS TotalUsageDuration " +
                         "FROM Users u " +
                         "JOIN Uses us ON u.UserID = us.UserID " +
                         "JOIN Devices d ON us.DeviceID = d.DeviceID " +
                         "WHERE u.UserID = ? AND us.UsageDate BETWEEN ? AND ? " +
                         "GROUP BY u.UserID, d.DeviceID";
        statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + userId + "%");
        statement.setString(2, startDate);
        statement.setString(3, endDate);

        // Execute query
        resultSet = statement.executeQuery();

        // Process result set
        while (resultSet.next()) {
            int retrievedUserId = resultSet.getInt("UserID");
            String name = resultSet.getString("UserName");
            String userType = resultSet.getString("UserType");
            String deviceName = resultSet.getString("DeviceName");
            String deviceType = resultSet.getString("DeviceType");
            int totalUsageDuration = resultSet.getInt("TotalUsageDuration");
            System.out.println("UserID: " + retrievedUserId + "\nUserName: " + name + "\nUserType: " + userType + "\nDeviceName: " + deviceName + "\nDeviceType: " + deviceType + "\nTotal Usage (minutes): " + totalUsageDuration);
            // Create User object and add to search result
            Total total = new Total(retrievedUserId, name, deviceName, deviceType, totalUsageDuration);
            searchResult.add(total);
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
