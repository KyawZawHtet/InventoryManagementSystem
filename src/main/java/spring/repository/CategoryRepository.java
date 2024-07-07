package spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import spring.dto.CategoryDto;
import spring.utils.ConnectionClass;

@Repository
public class CategoryRepository {
	public int insertCategory(CategoryDto dto) {
	    Connection con = ConnectionClass.getConnection();
	    int result = 0;
	    try {
	        PreparedStatement ps = con.prepareStatement("INSERT INTO category (name, description) VALUES (?,?)");
	        ps.setString(1, dto.getName());
	        ps.setString(2, dto.getDescription());
	        
	        result = ps.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Insert Category: " + e.getMessage());
	    }
	    return result;
	}

	public List<CategoryDto> getAllCategories() {
	    Connection con = ConnectionClass.getConnection();
	    List<CategoryDto> lists = new ArrayList<>();
	    try {
	        PreparedStatement ps = con.prepareStatement("SELECT * FROM category");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	CategoryDto dto = new CategoryDto();
	            dto.setId(rs.getLong("id"));
	            dto.setName(rs.getString("name"));
	            dto.setDescription(rs.getString("description"));
	            lists.add(dto);
	        }
	    } catch (SQLException e) {
	        System.out.println("Get All Categories: " + e.getMessage());
	    }
	    return lists;
	}
	
	public boolean checkCategoryname(String name) {
        Connection con = ConnectionClass.getConnection();
        boolean status = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM category WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = true;
            }
        } catch (SQLException e) {
            System.out.println("Already Category error: " + e.getMessage());
        }
        return status;
    }
	
	public List<CategoryDto> searchCategoriesByNameAndDescription(String categoryName, String categoryDescription) {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<CategoryDto> categories = new ArrayList<>();

	    try {
	        con = ConnectionClass.getConnection();
	        String sql = "SELECT id, name, description FROM category WHERE "
	                   + "(name LIKE ? OR ? IS NULL) "
	                   + "AND (description LIKE ? OR ? IS NULL)";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, "%" + categoryName + "%");
	        ps.setString(2, categoryName);
	        ps.setString(3, "%" + categoryDescription + "%");
	        ps.setString(4, categoryDescription);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	        	CategoryDto category = new CategoryDto();
	            category.setId(rs.getLong("id"));
	            category.setName(rs.getString("name"));
	            category.setDescription(rs.getString("description"));

	            categories.add(category);
	        }
	    } catch (SQLException e) {
	        System.out.println("Search Categories: " + e.getMessage());
	    }

	    return categories;
	}
	
	public CategoryDto getCategoryById(int id) {
		CategoryDto categoryDTO = null;
	    Connection con = ConnectionClass.getConnection();
	    try {
	        PreparedStatement ps = con.prepareStatement("SELECT * FROM category WHERE id = ?");
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            categoryDTO = new CategoryDto();
	            categoryDTO.setId(rs.getLong("id"));
	            categoryDTO.setName(rs.getString("name"));
	            categoryDTO.setDescription(rs.getString("description"));
	            
	        }
	    } catch (SQLException e) {
	        System.out.println("Get Category By Id: " + e.getMessage());
	    }
	    return categoryDTO;
	}

	public int updateCategory(CategoryDto dto) {
	    Connection con = ConnectionClass.getConnection();
	    int result = 0;
	    try {
	        PreparedStatement ps = con.prepareStatement("UPDATE category SET name = ?, description =? WHERE id = ?");
	        ps.setString(1, dto.getName());
	        ps.setString(2, dto.getDescription());
	        ps.setLong(3, dto.getId());
	        
	        result = ps.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Update Category: " + e.getMessage());
	    }
	    return result;
	}

	public int softDeleteCategory(int id) {
	    Connection con = ConnectionClass.getConnection();
	    int result = 0;
	    try {
	        PreparedStatement ps = con.prepareStatement("UPDATE category SET deleted = TRUE WHERE cat_id = ?");
	        ps.setInt(1, id);
	        result = ps.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Soft Delete Category: " + e.getMessage());
	    }
	    return result;
	}
}
