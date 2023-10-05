package com.ty.conntroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ty.model.Cart;


@WebServlet("/remove-cart-item")
public class RemoveCartItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try(PrintWriter out=response.getWriter()) {
			String id=request.getParameter("id");
			out.println("Product is : "+id);
			if(id!=null) {
				ArrayList<Cart> list= (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
				if(list !=null) {
					for(Cart c:list) {
						if(c.getId()== Integer.parseInt(id)) {
							list.remove(list.indexOf(c));
							break;
						}
					}
					response.sendRedirect("cart.jsp");
				}
			}else {
				response.sendRedirect("cart.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
