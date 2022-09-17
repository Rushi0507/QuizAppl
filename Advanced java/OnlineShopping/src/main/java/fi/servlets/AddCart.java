package fi.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.beans.ProductItem;

@WebServlet("/AddCart")
public class AddCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		String tmp = request.getParameter("catId");
		int catId = Integer.parseInt(tmp);
		
	    tmp = request.getParameter("prodId");
		int prodId = Integer.parseInt(tmp);
		
		tmp = request.getParameter("price");
		float price = Float.parseFloat(tmp);
		
		HttpSession userSession = request.getSession(false);
		if(userSession==null)
			response.sendRedirect("login.html");
		else
		{
			ProductItem item = new ProductItem(catId,prodId,price,1, prodId);
			ArrayList<ProductItem> objCart = (ArrayList<ProductItem>) userSession.getAttribute("cart");
			
			if(objCart ==null)
			{
				objCart = new ArrayList<>();
				userSession.setAttribute("cart", objCart);
				
			}
			objCart.add(item);
			response.sendRedirect("ListCart");
		}
		
	}

}
