<%@page import="com.ty.dao.ProductsDao"%>
<%@page import="java.util.* "%>
<%@page import="com.ty.model.Cart"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ty.dao.UserDao"%>
<%@page import="com.ty.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%
		ArrayList<Cart> cartlist=(ArrayList<Cart>) session.getAttribute("cart-list");
		List<Cart> cartProduct=null;
		if(cartlist != null){
			ProductsDao dao=new ProductsDao();
			cartProduct=dao.getCartProducts(cartlist);
			double total=dao.getTotalCartPrice(cartlist);
			request.setAttribute("cartlist", cartlist);
			request.setAttribute("total", total);
		}
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart page</title>
<%@include file="includes/header.jsp"%>

<style type="text/css">
.table tbody td {
	vertical-align: middle;
}

.btn-incre, .btn-decre {
	box-shadow: none;
	font-size: 25px;
}
</style>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>

	<div class="container">
		<div class="d-flex py-3">
			<h3>Total price:$ ${(total>0)?total:0}</h3>
			<a class="mx-3 btn btn-primary" href="check-out">Check Out</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy Now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(cartlist !=null){
						for(Cart c:cartlist){%>
							<tr>
							<td><%=c.getName() %></td>
							<td><%=c.getCategory() %></td>
							<td><%=c.getPrice() %></td>
							<td>
								<form action="order-now" method="post" class="form-inline">
									<input type="hidden" name="id" value="<%=c.getId() %>" class="form-input">
									<div class="form-group d-flex justify-content-between w-50">
										<a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%=c.getId() %>"> <i
											class=" fas fa-minus-square"></i></a> 
											<input type="text" name="quantity" class="form-control w-50" value="<%=c.getQuantity() %>" readonly>
										<a class="btn btn-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%=c.getId() %>">
										<i class=" fas fa-plus-square"></i> </a>
									</div>
									<button type="submit" class="btn btn-primary btn-sm">Buy</button>
								</form>
							</td>
							<td><a class="btn btn-sm btn-danger" href="remove-cart-item?id=<%=c.getId() %>">remove</a></td>
						</tr>
							
					<% 	}
					}
				%>
				
				
			</tbody>

		</table>

	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>