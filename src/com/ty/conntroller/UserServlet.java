package com.ty.conntroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ty.dao.UserDao;
import com.ty.model.User;


@WebServlet("/user-login")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void init() {
		UserDao dao=new UserDao();
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try(PrintWriter out=response.getWriter()){
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			
			try{
				UserDao dao=new UserDao();
				
				User user=dao.userLogin(email, password);
				if(user!=null) {
					request.getSession().setAttribute("auth", user);
					System.out.println("session created");
					response.sendRedirect("index.jsp");
				}
				else {
					out.print("login failed");
				}
				
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
