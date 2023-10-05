<%@page import="com.ty.model.Order"%>
<%@page import="java.util.List"%>
<%@page import="com.ty.dao.OrderDao"%>
<%@page import="com.ty.model.Cart"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ty.dao.UserDao"%>
<%@page import="com.ty.model.User" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%
	List<Order> orders=null;
	User auth= (User)request.getSession().getAttribute("auth");
	if(auth != null){
		request.setAttribute("auth", auth);
			 orders=new OrderDao().userOrders(auth.getId());
	}else{
		 response.sendRedirect("login.jsp"); 
	}
	
	ArrayList<Cart> cartlist=(ArrayList<Cart>) session.getAttribute("cart-list");
	if(cartlist != null){
		request.setAttribute("cartlist", cartlist);
		}
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Orders page</title>
<%@include file="includes/header.jsp"%>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	
	<div class="container">
		<div class="card-header my-3">All Orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Quantity</th>
					<th scope="col">Price</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			
			<tbody>
			<%
				if(orders!=null){
					for(Order o:orders){%>
					<tr>
							<td><%=o.getDate() %></td>
							<td><%=o.getName() %></td>
							<td><%=o.getCategory() %></td>
							<td><%=o.getQuantity() %></td>
							<td><%=o.getPrice() %></td>
							<td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%=o.getOrderId() %>">Cancel</a> </td>
					</tr>		
					<%}
					
				}
			%>
			</tbody>
			</table>
	</div>		
	
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>