package spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import spring.dto.InvoiceDetailsDto;
import spring.model.Invoice;
import spring.utils.ConnectionClass;

@Repository
public class InvoiceRepository {

	public void insert(Invoice invoiceBean) {
		Connection con = ConnectionClass.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO invoice (order_id,product_id,quantity) VALUES (?,?,?)");
			ps.setInt(1, Integer.valueOf(invoiceBean.getOrderId()));
			ps.setString(2, invoiceBean.getLot());
			ps.setDouble(3, Double.valueOf(invoiceBean.getQty()));
			ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			System.out.println("Insert invoice : "+ e.getMessage());
		}
		
	}

	public List<InvoiceDetailsDto> makeDetails(Integer oId) {
		Connection con = ConnectionClass.getConnection();
		List<InvoiceDetailsDto> result = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement("select o.id oId, o.order_date oDate, inv.id invoiceId, "
					+ "lt.location_name customer, lt.address address, "
					+ "p.product_name product, inv.quantity quantity, l.price price, l.expired_date expired "
					+ "from invoice inv "
					+ "inner join orders o "
					+ "on o.id = inv.order_id "
					+ "inner join lot l "
					+ "on inv.product_id = l.lot_no "
					+ "inner join location lt "
					+ "on lt.id = o.location_id "
					+ "inner join product p "
					+ "on p.id = l.product_id "
					+ "where o.id = ?");
			ps.setInt(1, oId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				InvoiceDetailsDto dto = new InvoiceDetailsDto();
				dto.setAddress(rs.getString("address"));
				dto.setCustomer(rs.getString("customer"));
				
				dto.setExpired(rs.getString("expired"));
				dto.setInvoiceId(rs.getInt("invoiceId")+"");
				dto.setODate(rs.getString("oDate"));
				dto.setOId(rs.getInt("oId") + "");
				dto.setPName(rs.getString("product"));
				dto.setPrice(rs.getDouble("price"));
				dto.setQuantity(rs.getDouble("quantity"));
				result.add(dto);
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Make PDF : "+ e.getMessage());
		}
		
		return result;
	}
	
	

}
