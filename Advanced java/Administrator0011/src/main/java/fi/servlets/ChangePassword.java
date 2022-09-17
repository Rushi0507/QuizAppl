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

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		final String connectionURL ="jdbc:mysql://localhost/user";
		final String dbUser="root";
		final String dbPassword="Mysql@2022";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(Connection connection = DriverManager.getConnection(connectionURL, dbUser,dbPassword);
				PreparedStatement psInsert= connection.prepareStatement("update userinfo set password=? where (userName=? and password=?)");
				//Scanner scn= new Scanner(System.in);
				)
		{
			PrintWriter out= response.getWriter();
			String userName=request.getParameter("userName");
			String password=request.getParameter("password");
			String newpassword=request.getParameter("newpassword");
			
			psInsert.setString(1, newpassword);
			psInsert.setString(2, userName);
			psInsert.setString(3, password);

			
			psInsert.executeUpdate();
		}
		catch(SQLException e) 
		{
		e.printStackTrace();
		}
}


}
