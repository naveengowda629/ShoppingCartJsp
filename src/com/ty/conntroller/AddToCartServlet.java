package com.ty.conntroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ty.model.Cart;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		try (PrintWriter out = response.getWriter()) {
			ArrayList<Cart> cartlist = new ArrayList<Cart>();

			int id = Integer.parseInt(request.getParameter("id"));
			Cart cart = new Cart();
			cart.setId(id);
			cart.setQuantity(1);

			HttpSession Session = request.getSession();
			ArrayList<Cart> cl = (ArrayList<Cart>) Session.getAttribute("cart-list");

			if (cl == null) {
				cartlist.add(cart);
				Session.setAttribute("cart-list", cartlist);
//				out.println("session created and added to the list");

			} else {
				cartlist = cl;
				boolean exist = false;
				for (Cart c : cartlist) {
					if (c.getId() == id) {
						exist = true;
						out.println("<h3 style='color:crimson; text-align:center;'>Item already exist in Cart."
								+ "<a href='cart.jsp'>Go to Cart page</a></h3>");
					}
				}
				if (!exist) {
					cartlist.add(cart);
					response.sendRedirect("index.jsp");
				}

				
			}
		}
	}

}
