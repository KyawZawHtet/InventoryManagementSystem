package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.CategoryDto;
import spring.utils.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {

    public int insertCategory(CategoryDto categoryDto) {

        int i = 0;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO category(name,description) VALUES (?,?)");
            preparedStatement.setString(1, categoryDto.getName());
            preparedStatement.setString(2, categoryDto.getDescription());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Category insert : " + e.getMessage());
        }
        return i;
    }

    public List<CategoryDto> showAllCategories() {
        List<CategoryDto> categoryList = new ArrayList<CategoryDto>();
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM category");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setId(resultSet.getLong("id"));
                categoryDto.setName(resultSet.getString("name"));
                categoryDto.setDescription(resultSet.getString("description"));
                categoryList.add(categoryDto);
            }

        } catch (SQLException e) {
            System.out.println("Category list : " + e.getMessage());
        }

        return categoryList;
    }

    public CategoryDto showCategory(CategoryDto dto) {

        CategoryDto categoryDto = null;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE id = ?");
            preparedStatement.setLong(1, dto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categoryDto = new CategoryDto();
                categoryDto.setId(resultSet.getLong("id"));
                categoryDto.setName(resultSet.getString("name"));
                categoryDto.setDescription(resultSet.getString("description"));
            }

        } catch (SQLException e) {
            System.out.println("Select one category: " + e.getMessage());
        }

        return categoryDto;
    }

    public int updateCategory(CategoryDto dto) {
        int i = 0;
        Connection connection = ConnectionClass.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE category SET name=?, description=? WHERE id = ?");
            preparedStatement.setString(1, dto.getName());
            preparedStatement.setString(2, dto.getDescription());
            preparedStatement.setLong(3, dto.getId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update category :" + e.getMessage());
        }

        return i;
    }

    public int deleteCategory(Long id) {
        return 0;
    }
}
