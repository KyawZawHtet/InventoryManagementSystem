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
	public int insertProduct (ProductDto dto) {
	      Connection con = ConnectionClass.getConnection();
	      int result = 0;
	      try {
	          PreparedStatement ps = con.prepareStatement("INSERT INTO product (name, category_id) values (?,?)");
	          ps.setString(1, dto.getName());
	          ps.setLong(2, dto.getCategoryId());

	          result = ps.executeUpdate();
	      } catch (SQLException e) {
	          System.out.println("Insert Product: " + e.getMessage());
	      }
	      return result;
	  }


		public List<ProductDto> getAllProducts() {
		    Connection con = ConnectionClass.getConnection();
		    List<ProductDto> lists = new ArrayList<>();
		    try {
		        PreparedStatement ps = con.prepareStatement(
		            "SELECT p.*, c.* FROM product p INNER JOIN category c ON p.category_id = c.id"
		        );
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		        	ProductDto dto = new ProductDto();
		        	dto.setId(rs.getLong("id"));
		        	dto.setName(rs.getString("name"));
		            dto.setCategoryId(rs.getLong("category_id"));
		            lists.add(dto);
		        }
		    } catch (SQLException e) {
		        System.out.println("Get All Product: " + e.getMessage());
		    }
		    return lists;
		}


		public List<ProductDto> searchProductsName(String productName) {
	      Connection con = null;
	      PreparedStatement ps = null;
	      ResultSet rs = null;
	      List<ProductDto> products = new ArrayList<>();

	      try {
	          con = ConnectionClass.getConnection();
	          String sql = "SELECT id, name, category_id"
	                     + "FROM product WHERE "
	                     + "(name LIKE ? OR ? IS NULL) ";
	          
	          ps.setString(1, productName != null ? "%" + productName + "%" : null);
	          ps.setString(2, productName);

	          rs = ps.executeQuery();

	          while (rs.next()) {
	        	  ProductDto product = new ProductDto();
	        	  product.setId(rs.getLong("id"));
	        	  product.setName(rs.getString("name"));
	              product.setCategoryId(rs.getLong("category_id"));

	              products.add(product);
	          }
	      } catch (SQLException e) {
	          System.out.println("Search Products: " + e.getMessage());
	      }
	      return products;
	  }
		
		public ProductDto getProductById(int id) {
			ProductDto productDTO = null;
		    Connection con = ConnectionClass.getConnection();
		    try {
		        PreparedStatement ps = con.prepareStatement(
		            "SELECT p.*, c.* FROM product p INNER JOIN category c ON p.category_id = c.id WHERE p.id = ?"
		        );
		        ps.setInt(1, id);
		        ResultSet rs = ps.executeQuery();
		        if (rs.next()) {
		            productDTO = new ProductDto();
		            productDTO.setId(rs.getLong("id"));
		            productDTO.setName(rs.getString("name"));
		            productDTO.setCategoryId(rs.getLong("category_id"));
		            
		        }
		    } catch (SQLException e) {
		        System.out.println("Get Product By Id: " + e.getMessage());
		    }
		    return productDTO;
		}


	  public int updateProduct(ProductDto dto) {
	      Connection con = ConnectionClass.getConnection();
	      int result = 0;
	      try {
	          PreparedStatement ps = con.prepareStatement(
	              "UPDATE product SET name = ?, category_id = ? WHERE id = ? "
	          );
	          ps.setString(1, dto.getName());
	          ps.setLong(2, dto.getCategoryId());
	          ps.setLong(3, dto.getId());

	          result = ps.executeUpdate();
	      } catch (SQLException e) {
	          System.out.println("Update Product: " + e.getMessage());
	      }
	      return result;
	  }
	  
	  public int softDeleteProduct(int id) {
	      Connection con = ConnectionClass.getConnection();
	      int result = 0;
	      try {
	          PreparedStatement ps = con.prepareStatement("UPDATE productone SET deleted = TRUE WHERE id = ?");
	          ps.setInt(1, id);
	          result = ps.executeUpdate();
	      } catch (SQLException e) {
	          System.out.println("Soft Delete Product: " + e.getMessage());
	      }
	      return result;
	  }
}
