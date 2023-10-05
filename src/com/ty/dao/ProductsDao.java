package com.ty.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ty.model.Cart;
import com.ty.model.Products;

public class ProductsDao {
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
	
	public List<Products> getAllProducts(){
		List<Products> products=new ArrayList<Products>();
		
		try {
			Connection con=UserDao.getConnection();
			query="select * from products";
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next()) {
				Products p=new Products();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setCategory(rs.getString("category"));
				p.setPrice(rs.getDouble("price"));
				p.setImage(rs.getString("image"));
				
				products.add(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public List<Cart> getCartProducts(ArrayList<Cart> cartlist){
		List<Cart> products=new ArrayList<Cart>();
		try {
			if(cartlist.size()>0) {
				for(Cart item:cartlist) {
					Connection con=ProductsDao.getConnection();
					query="select * from products where id=?";
					ps=con.prepareStatement(query);
					ps.setInt(1, item.getId());
					rs=ps.executeQuery();
					
					while(rs.next()) {
						Cart ct=new Cart();
						ct.setId(rs.getInt("id"));
						ct.setName(rs.getString("name"));
						ct.setCategory(rs.getString("category"));
						ct.setPrice(rs.getDouble("price")*item.getQuantity());
						ct.setQuantity(item.getQuantity());
						products.add(ct);
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cartlist;
		
	}
	
	public Products getSingleProduct(int id) {
		Products row=null;
		try {
			Connection connection=ProductsDao.getConnection();
			query="select * from products where id=?";
			ps=connection.prepareStatement(query);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			while(rs.next()) {
				row=new Products();
				row.setId(rs.getInt("id"));
				row.setName(rs.getString("name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	public double getTotalCartPrice(ArrayList<Cart> cartlist)  {
		double sum=0;
			try {
				if(cartlist.size()>0) {
					for(Cart item:cartlist) {
						Connection con=UserDao.getConnection();
						query="select price from products where id=?";
						ps=con.prepareStatement(query);
						ps.setInt(1, item.getId());
						rs=ps.executeQuery();
						while(rs.next()) {
							sum+=rs.getDouble("price")*item.getQuantity();
						}
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return sum;
		
	}

}
