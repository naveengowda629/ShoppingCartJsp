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


@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out=response.getWriter()){
			
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			Date  date=new Date();
			
			User auth=(User) request.getSession().getAttribute("auth");
			if(auth!=null) {
				String productId=request.getParameter("id");
				int productQuantity= Integer.parseInt(request.getParameter("quantity"));
				if(productQuantity<=0) {
					productQuantity=1;
				}
				
				
				Order orderModel=new Order();
				orderModel.setId(Integer.parseInt(productId));
				orderModel.setuId(auth.getId());
				orderModel.setQuantity(productQuantity);
				orderModel.setDate(format.format(date));
				
				OrderDao orderDao=new OrderDao();
				boolean result=orderDao.insertOrder(orderModel);
				if(result) {
					ArrayList<Cart> list= (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
					if(list !=null) {
						for(Cart c:list) {
							if(c.getId()== Integer.parseInt(productId)) {
								list.remove(list.indexOf(c));
								break;
							}
						}
					}
					response.sendRedirect("orders.jsp");
				}else {
					out.println("order failed");
				}
				
			}else {
				response.sendRedirect("login.jsp");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
