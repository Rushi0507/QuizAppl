package fi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Authenticate")
public class Authenticate0011 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String driverClass = "com.mysql.cj.jdbc.Driver";
	String connectionURL = "jdbc:mysql://localhost/user";
	String dbUser = "root";
	String dbPassword = "Mysql@2022";
	Connection connection;
	PreparedStatement psAuthenticate;

	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);

		try {
			Class.forName(driverClass);
			connection = DriverManager.getConnection(connectionURL, dbUser, dbPassword);
			psAuthenticate = connection.prepareStatement("select * from userinfo where userName=? and password=?");
		} catch (SQLException | ClassNotFoundException e) 
		{
			e.printStackTrace();
			throw new ServletException("Failed to intialize the Database connection " + e.getMessage());
		}
	}

	@Override
	public void destroy() {                          //JUST SELECT THIS METHOD DESTROY AUTOGN
		try {
			if (psAuthenticate != null)
				psAuthenticate.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResultSet result = null;

		try {
			PrintWriter out = response.getWriter();
			String user = request.getParameter("userName");
			String pass = request.getParameter("password");

			psAuthenticate.clearParameters();
			if (user != null)
				psAuthenticate.setString(1, user);
			if (pass != null)
				psAuthenticate.setString(2, pass);

			result = psAuthenticate.executeQuery();

			if (result.next())
			{
				if(user.equals("admin"))								//...FOR ONLY ADMIN GOTO WELCOME PAGE 
					response.sendRedirect("admin/welcome.html");
				else
					response.sendRedirect("showcategory.html");			//...FOR USER 
			}
				
			else
				out.println("Authentication failure");
			} catch (SQLException sql) 
			{
				sql.printStackTrace();
			}

			finally {
				try {
					if (result != null)
						result.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
