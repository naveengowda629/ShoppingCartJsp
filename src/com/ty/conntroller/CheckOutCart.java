package com.ty.conntroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ty.dao.OrderDao;
import com.ty.model.Cart;
import com.ty.model.Order;
import com.ty.model.User;


@WebServlet("/check-out")
public class CheckOutCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try(PrintWriter out=response.getWriter()){
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				Date  date=new Date();
				//Retrive the all cart products
				ArrayList<Cart> cartlist= (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
				//user Authentication
				User auth= (User) request.getSession().getAttribute("auth");
				//to check auth and cart list
				
				if(auth !=null && cartlist!=null) {
						for(Cart c:cartlist) {
							Order order=new Order();
							order.setId(c.getId());
							order.setuId(auth.getId());
							order.setQuantity(c.getQuantity());
							order.setDate(format.format(date));
							
							OrderDao dao= new OrderDao();
							boolean result=dao.insertOrder(order);
							if(!result) break;
						}
						cartlist.clear();
						response.sendRedirect("orders.jsp");
				}else {
					if(auth==null) response.sendRedirect("login.jsp");
					else {
						response.sendRedirect("cart.jsp");
					}
			
				}
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
