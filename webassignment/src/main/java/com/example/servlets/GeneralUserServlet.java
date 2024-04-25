import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.example.model.User;
import com.example.model.Uses;
import com.example.model.Device;
import com.example.model.AllData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/generalSearch")
public class GeneralUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://faure.cs.colostate.edu:3306/tste";
    private static final String JDBC_USERNAME = "tste";
    private static final String JDBC_PASSWORD = "835460928";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");
        List<AllData> searchResult = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Prepare SQL statement
            String sql = "SELECT Users.UserID, Users.UserName, Devices.DeviceID, Devices.DeviceName, Devices.DeviceType, Uses.UsageDate, Uses.UsageDuration " +
                        "FROM Users " +
                        "JOIN Uses ON Users.UserID = Uses.UserID " +
                        "JOIN Devices ON Uses.DeviceID = Devices.DeviceID " +
                        "WHERE Users.UserID = ? " +
                        "AND Uses.UsageDate BETWEEN ? AND ?";
            statement = connection.prepareStatement(sql);

            // Set parameters
            int userId = Integer.parseInt(userIdStr);
            statement.setInt(1, userId);
            statement.setString(2, date1);
            statement.setString(3, date2);

            // Execute query
            resultSet = statement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                int userIdResult = resultSet.getInt("UserID");
                String name = resultSet.getString("UserName");
                int deviceId = resultSet.getInt("DeviceID");
                String deviceName = resultSet.getString("DeviceName");
                String deviceType = resultSet.getString("DeviceType");
                String usageDate = resultSet.getString("UsageDate");
                int usageDuration = resultSet.getInt("UsageDuration");

                // Create objects and add to search result
                User user = new User(userIdResult, name, "");
                Device device = new Device(deviceId, deviceName, deviceType);
                Uses uses = new Uses(userIdResult, deviceId, usageDate, String.valueOf(usageDuration));
                AllData everything = new AllData(user, uses, device);
                searchResult.add(everything);
            }
        } catch (SQLException | ClassNotFoundException e) {
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
