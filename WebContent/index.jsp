<%@page import="com.ty.model.Cart"%>
<%@page import="com.ty.dao.ProductsDao"%>
<%@page import="java.util.*"%>
<%@page import="com.ty.model.Products"%>
<%@page import="com.ty.dao.UserDao"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	ProductsDao dao = new ProductsDao();
	List<Products> products = dao.getAllProducts();
	
	ArrayList<Cart> cartlist=(ArrayList<Cart>) session.getAttribute("cart-list");
	if(cartlist != null){
		request.setAttribute("cartlist", cartlist);
	}
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome to Shopping Cart</title>
<%@include file="includes/header.jsp"%>

</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>

	<div class="container">
		<div class="card-header my-3">All Products</div>
		<div class="row">
			<%
				if (!products.isEmpty()) {
					for (Products p : products) {
			%>
			<div class="col-md-3 my-3">
				<div class="card w-100" style="width: 18rem;">
					<img class="card-img-top" alt="card img cap"
						src="<%out.println(p.getImage());%>">
					<div class="card-body">
						<h5 class="card-title">
							<%
								out.println(p.getName());
							%>
						</h5>
						<h6 class="price">
							Price: $<%
							out.println(p.getPrice());
						%>
						</h6>
						<h6 class="category">
							Category:<%
							out.println(p.getCategory());
						%>
						</h6>
						<div class="mt-3 d-flex justify-content-between">
							<a href="add-to-cart?id=<% out.println(p.getId()); %>" class="btn btn-dark">Add to Cart</a> 
							<a href="order-now?quantity=1&id=<%=p.getId() %>" class="btn btn-primary">Buy now</a>
						</div>
					</div>
				</div>
			</div>
			<%
				}
				}
			%>

		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>


</body>
</html>