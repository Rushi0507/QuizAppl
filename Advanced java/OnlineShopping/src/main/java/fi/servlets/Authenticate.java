package fi.servlets;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection;
	PreparedStatement psAuthenticate;

	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);

		try 
		{
			System.out.println("Authenticate INIT method fired.......");
			ServletContext application = getServletContext();
			String driverClass = application.getInitParameter("driverClass");
			String url = application.getInitParameter("dbUrl");
			String dbUser = application.getInitParameter("dbUser");
			String dbPass = application.getInitParameter("dbPass");
			
			Class.forName(driverClass);
			connection = DriverManager.getConnection(url, dbUser, dbPass);
			application.setAttribute("globalConnection", connection);
			
			psAuthenticate = connection.prepareStatement("select * from userinfo where userName=? and password=?");
		} 
		catch (SQLException | ClassNotFoundException e) 
		{
			e.printStackTrace();
			throw new ServletException("Failed to intialize the Database connection " + e.getMessage());
		}
	}
			

	@Override
	public void destroy() 
	{
		try {
			if (psAuthenticate != null)
				psAuthenticate.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
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
				HttpSession userSession = request.getSession(true);
				userSession.setAttribute("userName", user);
				response.sendRedirect("Category");
			}				
			else
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
				out.println("<font color='red'>Invalid username/password</font><br/>");
				dispatcher.include(request, response);
			}
		
		} catch (SQLException sql) 
		{
			sql.printStackTrace();
		}

		finally {
			
			try {
				if (result != null)
					result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
