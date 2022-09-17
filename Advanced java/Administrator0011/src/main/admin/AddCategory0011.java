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


@WebServlet("/admin/AddCategory")
public class AddCategory0011 extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	
		final String connectionURL ="jdbc:mysql://localhost/user";    //...jdbc connection by diver manager
		final String dbUser="root";
		final String dbPassword="Mysql@2022";
		try(Connection connection = DriverManager.getConnection(connectionURL, dbUser,dbPassword);
				PreparedStatement psInsert= connection.prepareStatement("insert into category values(?,?,?,?)");
				//Scanner scn= new Scanner(System.in); instead of scanner here we use -> ?
				)
		{
			PrintWriter out= response.getWriter();              //......get and set parameters
			String id=request.getParameter("catId");
			String catname=request.getParameter("catName");
			String catdsc=request.getParameter("Description");
			String catimg=request.getParameter("Image");                    
																	//While we using try catch so DON'T NEED TO CLOSE THE CONNECTION
			psInsert.setString(1, id);
			psInsert.setString(2, catname);
			psInsert.setString(3, catdsc);
			psInsert.setString(4, catimg);
			
		psInsert.executeUpdate();                             //....we inserted so executeUPDATE OTHERWISE executeQUERY
			
			out.println("*****Categories Added Successfully***");
				
		}
			catch(SQLException e) 
			{
			e.printStackTrace();
			}
	}

}