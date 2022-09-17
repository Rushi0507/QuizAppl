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
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet( initParams ={@WebInitParam(name = "query", value="select * from category"),
		},name = "CategoryServlet", value="/Category")

public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection;
	PreparedStatement psCategory;

	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);

		try 
		{
			ServletContext application = getServletContext();
			connection = (Connection) application.getAttribute("globalConnection");
			
			String query = config.getInitParameter("query");
			psCategory = connection.prepareStatement(query);
		}
		 catch (SQLException  e) 
		{
			e.printStackTrace();
			throw new ServletException("Failed to intialize the Database connection " + e.getMessage());
		}
	}

	public void destroy() 
	{
		try {
			if (psCategory != null)
				psCategory.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		
		HttpSession userSession = request.getSession(false);
		if(userSession==null)
			response.sendRedirect("login.html");
		else
		{
			try (ResultSet result = psCategory.executeQuery()) 
			{
				out.println("<html>");
				out.println("<body>");
				out.println("<i>Welcome " + userSession.getAttribute("userName")+ "</i><br/>");
				
				out.println("<table border='1'>");
				out.println("<tr>");
				out.println("<th>Name</th>");
				out.println("<th>Descripton</th>");
				out.println("<th>Image</th>");
				out.println("</tr>");
				
				while (result.next()) 
					
				{
					out.println("<tr>");
					out.println("<td><a href='Products?catId=" +result.getInt(1) + "'>" + result.getString(2) +"</a></td>");
					out.println("<td>" + result.getString(3) + "</td>");
					out.println("<td><img src='Image/" + result.getString(4)+ "' width='75px' height='75px' alt='No-Image'/> </td>");
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
			
		}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
		}
	}

}
