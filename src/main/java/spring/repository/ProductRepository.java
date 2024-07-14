package spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import spring.dto.ProductDto;
import spring.utils.ConnectionClass;

@Repository
public class ProductRepository {

	public ProductRepository() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ProductDto> getAll() {
		Connection con=ConnectionClass.getConnection();
		List<ProductDto> products = new ArrayList<>();
		try {
			PreparedStatement ps=con.prepareStatement("select p.id id, product_code, product_name, p.description description, uom, c.name category from product p inner join category c on p.category_id = c.id");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				ProductDto product = new ProductDto();
				product.setCategory(rs.getString("category"));
				product.setCode(rs.getString("product_code"));
				product.setDescription(rs.getString("description"));
				product.setId(rs.getString("id"));
				product.setName(rs.getString("product_name"));
				product.setUom(rs.getString("uom"));
				products.add(product);
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Get All : "+ e.getMessage());
		}
		
		return products;
	}
	
	public void insertOne(ProductDto dto) {
		Connection con=ConnectionClass.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO product (product_name, category_id, product_code, description, uom) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, dto.getName());
			ps.setInt(2, Integer.valueOf(dto.getCategory()));
			ps.setString(3, dto.getCode());
			ps.setString(4, dto.getDescription());
			ps.setString(5, dto.getUom());
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Insert One Product : "+ e.getMessage());
		}
	}
	
	public ProductDto getOne(String id) {
		Connection con = ConnectionClass.getConnection();
		ProductDto dto=null;
		try {
			PreparedStatement ps = con.prepareStatement("select * from product where id=?");
			ps.setInt(1, Integer.valueOf(id));
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				dto=new ProductDto();
				dto.setCategory(rs.getString("category_id"));
				dto.setCode(rs.getString("product_code"));
				dto.setDescription(rs.getString("description"));
				dto.setId(rs.getInt("id")+"");
				dto.setName(rs.getString("product_name"));
				dto.setUom(rs.getString("uom"));
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Get one product : "+ e.getMessage());
		}
		
		return dto;
	}

	public List<ProductDto> getExceptId(String id) {
		Connection con=ConnectionClass.getConnection();
		List<ProductDto> products = new ArrayList<>();
		try {
			PreparedStatement ps=con.prepareStatement("select p.id id, product_code, product_name, p.description description, uom, c.name category from product p inner join category c on p.category_id = c.id where p.id != ?");
			ps.setInt(1, Integer.valueOf(id));
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				ProductDto product = new ProductDto();
				product.setCategory(rs.getString("category"));
				product.setCode(rs.getString("product_code"));
				product.setDescription(rs.getString("description"));
				product.setId(rs.getString("id"));
				product.setName(rs.getString("product_name"));
				product.setUom(rs.getString("uom"));
				products.add(product);
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Get All : "+ e.getMessage());
		}
		
		return products;
	}
	
	public void updateOne(ProductDto dto) {
		Connection con=ConnectionClass.getConnection();
		try {
			PreparedStatement ps=con.prepareStatement("UPDATE product SET product_name = ?, category_id = ?, product_code = ?, description = ?, uom = ? WHERE 	id = ?");
			ps.setString(1, dto.getName());
			ps.setInt(2, Integer.valueOf(dto.getCategory()));
			ps.setString(3, dto.getCode());
			ps.setString(4, dto.getDescription());
			ps.setString(5, dto.getUom());
			ps.setInt(6, Integer.valueOf(dto.getId()));
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Update one product : "+ e.getMessage());
		}
	}

	public List<ProductDto> getToOrder(String[] ids) {
		Connection con=ConnectionClass.getConnection();
		List<ProductDto> products = new ArrayList<>();
		for(String id: ids)
		{
			ProductDto product = getOne(id);
			products.add(product);
		}
	
		return products;
	}
	
	
}
