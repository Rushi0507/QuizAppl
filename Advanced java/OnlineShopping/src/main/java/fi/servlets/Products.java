package fi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Products")
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection;
	PreparedStatement psProducts;

	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);

		try 
		{
			ServletContext application = getServletContext();
			connection = (Connection) application.getAttribute("globalConnection");
			psProducts = connection.prepareStatement("select * from products where catId=?");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new ServletException("Failed to intialize the Database connection " + e.getMessage());
		}
	}

	public void destroy() 
	{
		try {
			if (psProducts != null)
				psProducts.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String tmpCatId = request.getParameter("catId");
		int catId = Integer.parseInt(tmpCatId);
		
		PrintWriter out = response.getWriter();
		
		HttpSession userSession = request.getSession(false);
		if(userSession==null)
			response.sendRedirect("login.html");
		else
		{
			try
			{
				psProducts.clearParameters();
				psProducts.setInt(1,catId);
				try (ResultSet result = psProducts.executeQuery()) 
				{
					out.println("<html>");
					out.println("<body>");
					out.println("<h2>Welcome " + userSession.getAttribute("userName")+ "</h2><br/>");

					out.println("<table border='1'>");
					out.println("<tr>");
					
					out.println("<th>Name</th>");
					out.println("<th>Descripton</th>");
					out.println("<th>Image</th>");
					out.println("<th>Price</th>");
					
					
					out.println("</tr>");
					
					while (result.next()) 
						
						
					{
						out.println("<tr>");
						
						
						out.println("<td>" + result.getString("prodName") + "</td>");
						out.println("<td>" + result.getString("prodDesc") + "</td>");
						out.println("<td><img src='Image/" + result.getString("prodImage")+ "' width='75px' height='75px' alt='No-Image'/> </td>");
						out.println("<td>" + result.getString("prodPrice") + "</td>");
						
						out.println("<td><a href='AddCart?catId=" + result.getInt("catId") + "&prodId=" + result.getInt("prodId") + "&price=" + result.getFloat("prodPrice") +"'>Add to Cart</a></td>");
						
						out.println("</tr>");
					}
					out.println("</table>");
					out.println("</body>");
					out.println("</html>");
				
			}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
				