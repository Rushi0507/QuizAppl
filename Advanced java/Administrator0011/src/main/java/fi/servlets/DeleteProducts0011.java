package fi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/DeleteProducts")
public class DeleteProducts0011 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String driverClass = "com.mysql.cj.jdbc.Driver";
	String connectionURL = "jdbc:mysql://localhost/user";
	String dbUser = "root";
	String dbPassword = "Mysql@2022";
	Connection connection;
	//PreparedStatement psDeletecat;
	PreparedStatement psDeleteproduct;
    
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
		
		try
		{
			Class.forName(driverClass);
			connection = DriverManager.getConnection(connectionURL, dbUser, dbPassword);
			//psDeletecat = connection.prepareStatement("Delete from category where catId=?");
		    psDeleteproduct = connection.prepareStatement("Delete from products where prodId=?");

		}
		catch(SQLException | ClassNotFoundException  e)
		{
			e.printStackTrace();
			
			throw new ServletException("Failed TO initialisez the database connection"+ e.getMessage());
		}
	}


	public void destroy() {
		try
		{
			if(connection!=null)
				connection.close();
			
			if(psDeleteproduct!=null)
				psDeleteproduct.close();
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			PrintWriter out = response.getWriter();
			
			String prodId = request.getParameter("prodId");
			
			psDeleteproduct.setString(1, prodId);
			//psDeletecat.setString(1, catId);
			
			int count = psDeleteproduct.executeUpdate();
			if(count==0)
				out.println("You enter invalid Product Id");
			
			else
			out.println("<h3>Recored Successfully Deleted....</h3>");
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
	}

}
