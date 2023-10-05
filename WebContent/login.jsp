<%@page import="com.ty.model.Cart"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.* "%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="com.ty.model.User" %>
	<%
	User auth= (User)request.getSession().getAttribute("auth");
	if(auth != null){
		request.setAttribute("auth", auth);
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
<title>Login page</title>
<%@include file="includes/header.jsp"%>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<jsp:include page="footer.jsp"></jsp:include>
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">User Login</div>
			<div class="card-body">
				<form action="user-login" method="post">
					<div class="form-group">
						<label>Email Address</label> <input type="email"
							class="form-control" name="email"
							placeholder="Enter your email address" required> <label>Password</label>
						<input type="password" class="form-control" name="password"
							placeholder="********" required>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>