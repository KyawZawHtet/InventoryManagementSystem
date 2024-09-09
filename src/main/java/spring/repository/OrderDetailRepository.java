package spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import spring.dto.DetailsDto;
import spring.dto.OrderDetailDto;
import spring.utils.ConnectionClass;

@Repository
public class OrderDetailRepository {

	public void insertDto(OrderDetailDto dto) {
		Connection con=ConnectionClass.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO order_detail (quantity, unit_price, order_id, product_id) VALUES (?,?,?,?)");
			ps.setInt(1, dto.getQuantity());
			ps.setDouble(2, dto.getUnitPrice());
			ps.setInt(3, Integer.valueOf(dto.getOrderId()));
			ps.setString(4, dto.getProductId());
			
			ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			System.out.println("Insert order details : "+ e.getMessage());
		}
	}

	public List<DetailsDto> getByOID(Integer id) {
		Connection con=ConnectionClass.getConnection();
		List<DetailsDto> result= new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement(""
					+ "select p.product_name product, dt.quantity orderQty, unit_price, order_id, l.location_name customer, lt.quantity quantity, expired_date expired, lt.lot_no lot "
					+ "from order_detail dt "
					+ "inner join orders o "
					+ "on dt.order_id=o.id "
					+ "inner join  location l "
					+ "on l.id = o.location_id "
					+ "inner join lot lt "
					+ "on lt.lot_no=dt.product_id "
					+ "inner join product p "
					+ "on p.id=lt.product_id "
					+ "where order_id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				DetailsDto inv = new DetailsDto();
				inv=new DetailsDto();
				inv.setExpired(rs.getDate("expired").toLocaleString());
				inv.setCustomer(rs.getString("customer"));
				inv.setOrder_qty(rs.getInt("orderQty"));
				inv.setTotal_qty(rs.getInt("quantity"));
				inv.setOrder_id(rs.getInt("order_id")+"");
				inv.setProduct(rs.getString("product")+"");
				inv.setPrice(rs.getDouble("unit_price"));
				inv.setLot(rs.getString("lot")+"");
				result.add(inv);
			}
			
			con.close();
		} catch (SQLException e) {
			System.out.println("Get Order Details : "+ e.getMessage());
		}
		
		return result;
	}

}
