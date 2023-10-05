package com.ty.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ty.model.Order;
import com.ty.model.Products;

public class OrderDao {
	
	private static Connection connection=null;
	private String query;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public static Connection getConnection() throws ClassNotFoundException , SQLException {
		if(connection==null)
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping_cart", "root", "root");
			
			return connection;
	}
	
	public boolean insertOrder(Order order) {
		boolean result=false;
		
		try {
			Connection connection=OrderDao.getConnection();
			query="insert into orders (p_id, u_id, o_quantity,o_date) values(?,?,?,?)";
			ps=connection.prepareStatement(query);
			ps.setInt(1,order.getId());
			ps.setInt(2, order.getuId());
			ps.setInt(3,order.getQuantity());
			ps.setString(4, order.getDate());
			ps.executeUpdate();
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public List<Order> userOrders(int id){
		List<Order> list=new ArrayList<Order>();
		try {
			Connection connection= OrderDao.getConnection();
			query="select *from orders where u_id=? order by orders.o_id desc";
			ps=connection.prepareStatement(query);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			
			while(rs.next()) {
				Order order=new Order();
				ProductsDao dao=new ProductsDao();
				int pId=rs.getInt("p_id");
				
				Products products=dao.getSingleProduct(pId);
				order.setOrderId(rs.getInt("o_id"));
				order.setId(pId);
				order.setName(products.getName());
				order.setCategory(products.getCategory());
				order.setPrice(products.getPrice()*rs.getInt("o_quantity"));
				order.setQuantity(rs.getInt("o_quantity"));
				order.setDate(rs.getString("o_date"));
				list.add(order);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void cancelOrder(int id) {
		try {
			Connection connection=UserDao.getConnection();
			query="delete from orders where o_id=?";
			ps=connection.prepareStatement(query);
			ps.setInt(1, id);
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
