package fi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/admin/AddProduct")
public class AddProduct0011 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	
		final String connectionURL ="jdbc:mysql://localhost/user";
		final String dbUser="root";
		final String dbPassword="Mysql@2022";
		try(Connection connection = DriverManager.getConnection(connectionURL, dbUser,dbPassword);
				PreparedStatement psInsert= connection.prepareStatement("insert into products values(?,?,?,?,?,?)");
			)		
		{
			PrintWriter out= response.getWriter();
			
			String id=request.getParameter("catId");
			String pid=request.getParameter("prodId");
			String proname=request.getParameter("prodName");
			String prodsc=request.getParameter("prodDesc");
			String proimg=request.getParameter("prodImage");
			String proprc=request.getParameter("prodPrice");
	
			psInsert.setString(1, id);
			psInsert.setString(2, pid);
			psInsert.setString(3, proname);
			psInsert.setString(4, prodsc);
			psInsert.setString(5, proimg);
			psInsert.setString(6, proprc);
			
		psInsert.executeUpdate();
		out.println("*****Products Added Successfully***");
		
		}
		catch(SQLException e) 
		{
		e.printStackTrace();
		}
}

}