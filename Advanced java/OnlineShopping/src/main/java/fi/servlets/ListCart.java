package fi.servlets;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.beans.ProductItem;

@WebServlet("/ListCart")
public class ListCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		HttpSession userSession = request.getSession(false);
		if(userSession==null)
			response.sendRedirect("login.html");
		else
		{
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<body>");
			out.println("<h2>Welcome " + userSession.getAttribute("userName")+ "</h2><br/>");



			ArrayList<ProductItem> objCart = (ArrayList<ProductItem>) userSession.getAttribute("cart");
			
			if(objCart ==null || objCart.isEmpty())
				out.println("You have no Products in your Shhopping cart<br/>");
			else
			{
				out.println("<table border='1'>");
				out.println("<tr>");
				
				out.println("<th>Category</th>");
				out.println("<th>Product</th>");
				out.println("<th>Price</th>");
				out.println("</tr>");
				double total = 0.0;
				for(ProductItem item : objCart)
				{
					out.println("<tr>");
					out.println("<td>" + item.getCategoryId()+ "</td>");
					out.println("<td>" + item.getProductId()+ "</td>");
					out.println("<td>" + item.getProductPrice()+ "</td>");
					total+=item.getProductPrice();
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("<h3>Total Amount is  : Rs " + total + "</h3><br/>");
			}
			out.println("<a href ='Category'>Continue Shopping...</a><br/>");
			out.println("<a href ='Logout'>Logout</a><br/>");
			out.println("<a href ='Checkout'>Checkout</a><br/>");
			
			out.println("</body>");
			out.println("</html>");
			
			}

	}

}
