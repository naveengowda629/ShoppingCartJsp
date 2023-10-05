 package com.ty.dao;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;
import com.ty.model.User;

public class UserDao {
	
	public  UserDao() {
		
	}
		private static Connection connection=null;
		private String query;
		private PreparedStatement ps;
		private ResultSet rs;
		
	public static Connection getConnection() throws ClassNotFoundException , SQLException {
		if(connection==null)
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping_cart", "root", "root");
			System.out.println("connected");
			return connection;
	}
	
	public User userLogin(String email,String password) {
		User user=null;
		try {
			Connection con=UserDao.getConnection();
			query="select * from users where email=? and password=?";
			ps=con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			rs=ps.executeQuery();
			if(rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
		
	}

}
