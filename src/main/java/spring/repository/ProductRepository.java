package spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
=======

>>>>>>> origin/ATD
import org.springframework.stereotype.Repository;
import spring.dto.ProductDto;
import spring.utils.ConnectionClass;

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

<<<<<<< HEAD
	public ProductRepository() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("Hi");
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
	
	
=======
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
>>>>>>> origin/ATD
}
