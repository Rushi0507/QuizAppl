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

@WebServlet("/admin/Products")
public class ShowProducts0011 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String driverClass = "com.mysql.cj.jdbc.Driver";
	String connectionURL = "jdbc:mysql://localhost/user";
	String dbUser = "root";
	String dbPassword = "Mysql@2022";
	Connection connection;
	PreparedStatement psProduct;

	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);

		try {
			Class.forName(driverClass);
			connection = DriverManager.getConnection(connectionURL, dbUser, dbPassword);
			psProduct = connection.prepareStatement("select * from products");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ServletException("Failed to intialize the Database connection " + e.getMessage());
		}
	}

	public void destroy() 
	{
		try {
			if (psProduct != null)
				psProduct.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		
		try (ResultSet result = psProduct.executeQuery()) 
		{
			out.println("<html>");
			out.println("<body>");
			out.println("<table border='1'>");
			out.println("<tr>");
			out.println("<th>CatId</th>");
			out.println("<th>ProdId</th>");
			out.println("<th>Name</th>");
			out.println("<th>Descripton</th>");
			out.println("<th>Image</th>");
			out.println("<th>Price</th>");
			out.println("</tr>");

			while (result.next()) 
				
				
			{
				out.println("<tr>");
				out.println("<td>" + result.getInt(1)+ "</td>");
				out.println("<td>" + result.getInt(2)+ "</td>");
				out.println("<td>" + result.getString(3) + "</td>");
				out.println("<td>" + result.getString(4) + "</td>");
				out.println("<td><img src='Image/" + result.getString(5)+ "' width='75px' height='75px' alt='No-Image'/> </td>");
				out.println("<td>" + result.getFloat(6)+ "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body>");
			out.println("</html>");
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
