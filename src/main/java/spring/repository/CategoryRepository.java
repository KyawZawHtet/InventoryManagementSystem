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

	public CategoryRepository() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<CategoryDto> getAll() {
		Connection con=ConnectionClass.getConnection();
		List<CategoryDto> categories = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement("select * from category");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				CategoryDto category = new CategoryDto();
				category.setId(rs.getInt("id")+"");
				category.setName(rs.getString("name"));
				category.setDescription(rs.getString("description"));
				categories.add(category);
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Inserting Data : "+ e.getMessage());
		}
		return categories;
	}

	public int updateOne(CategoryDto dto) {
		Connection con=ConnectionClass.getConnection();
		int result = 0;
		try {
			PreparedStatement ps = con.prepareStatement("update category set name=?, description=? where id=?");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDescription());
			ps.setInt(3, Integer.valueOf(dto.getId()));
			result = ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Updating : "+ e.getMessage());
		}
		return result;
	}

	public int deleteOne(Integer id) {
		Connection con=ConnectionClass.getConnection();
		int result = 0;
		try {
//			PreparedStatement ps = con.prepareStatement("delete from category where id=?");
			PreparedStatement ps = con.prepareStatement("update category set deleted=1 where id=?");
			ps.setInt(1, id);
			result = ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Deleting : "+ e.getMessage());
		}
		return result;
	}

	public int insertOne(CategoryDto dto) {
		Connection con=ConnectionClass.getConnection();
		int result = 0;
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO category (name, description) VALUES (?, ?)");
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDescription());
			result = ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Insert Category : "+ e.getMessage());
		}
		return result;
	}

	public CategoryDto getOne(Integer id) {
		Connection con=ConnectionClass.getConnection();
		CategoryDto category =null;
		try {
			PreparedStatement ps = con.prepareStatement("select * from category where id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				category = new CategoryDto();
				category.setId(rs.getInt("id")+"");
				category.setName(rs.getString("name"));
				category.setDescription(rs.getString("description"));
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Inserting Data : "+ e.getMessage());
		}
		
		return category;
	}

	public List<CategoryDto> getExceptId(String id) {
		Connection con=ConnectionClass.getConnection();
		List<CategoryDto> categories = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement("select * from category where id != ?");
			ps.setInt(1, Integer.valueOf(id));
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				CategoryDto category = new CategoryDto();
				category.setId(rs.getInt("id")+"");
				category.setName(rs.getString("name"));
				category.setDescription(rs.getString("description"));
				categories.add(category);
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Inserting Data : "+ e.getMessage());
		}
		return categories;
	}

	
	
}
