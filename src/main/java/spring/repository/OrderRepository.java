package spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import spring.dto.OrderDto;
import spring.utils.ConnectionClass;

@Repository
public class OrderRepository {

	public OrderRepository()
	{
		
	}
	
	
	public void insertOrder(OrderDto dto )
	{
		Connection con=ConnectionClass.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO orders (order_date,total_amount,status,location_id) VALUES (?,?,?,?)");
			ps.setDate(1, java.sql.Date.valueOf(dto.getOrderDate()));
			ps.setDouble(2, dto.getTotalAmount());
			ps.setString(3, dto.getStatus());
			ps.setString(4, dto.getCustomer());
			ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			System.out.println("Insert order "+ e.getMessage());
		}
	}
	
	public int getOrderID()
	{
		Connection con=ConnectionClass.getConnection();
		int result = 0;
		try {
			PreparedStatement ps = con.prepareStatement("select max(id) id from orders");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				result = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("Get Oid : "+ e.getMessage());
		}
		
		return result;
	}


	public List<OrderDto> getAll() {
		Connection con=ConnectionClass.getConnection();
		List<OrderDto> result = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT o.id id, order_date, total_amount, status, location_name name FROM orders o inner join location l on o.location_id=l.id");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				OrderDto order = new OrderDto();
				order.setCustomer(rs.getString("name"));
				order.setId(Long.valueOf(rs.getInt("id")));
				order.setOrderDate(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("order_date"))));
				order.setStatus(rs.getString("status"));
				order.setTotalAmount(rs.getDouble("total_amount"));
				result.add(order);
			}
		} catch (SQLException e) {
			System.out.println("Get All Orders : "+ e.getMessage());
		}
		
		return result;
	}


	public void updateStatusByID(String orderId) {
		Connection con=ConnectionClass.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE orders SET status =? WHERE (id = ?)");
			ps.setString(1, "true");
			ps.setInt(2, Integer.valueOf(orderId));
			ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			System.out.println("Update Status : "+ e.getMessage());
		}
	}
	
	
}
