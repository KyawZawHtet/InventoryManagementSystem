package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.CategoryDto;
import spring.dto.ProductDto;
import spring.utils.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    public int insertProduct(ProductDto productDto) {

        int i = 0;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product(name,category_id) VALUES (?,?)");
            preparedStatement.setString(1, productDto.getName());
            preparedStatement.setLong(2, productDto.getCategoryId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Product insert : " + e.getMessage());
        }
        return i;
    }

    public List<ProductDto> showAllProducts() {
        List<ProductDto> productList = new ArrayList<ProductDto>();
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductDto productDto = new ProductDto();
                productDto.setId(resultSet.getLong("id"));
                productDto.setName(resultSet.getString("name"));
                productDto.setCategoryId(resultSet.getLong("category_id"));
                productList.add(productDto);
            }

        } catch (SQLException e) {
            System.out.println("Product list : " + e.getMessage());
        }

        return productList;
    }

    public ProductDto showProduct(ProductDto dto) {

        ProductDto productDto = null;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE id = ?");
            preparedStatement.setLong(1, dto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productDto = new ProductDto();
                productDto.setId(resultSet.getLong("id"));
                productDto.setName(resultSet.getString("name"));
                productDto.setCategoryId(resultSet.getLong("category_id"));
            }

        } catch (SQLException e) {
            System.out.println("Select one product: " + e.getMessage());
        }

        return productDto;
    }

    public int updateProduct(ProductDto dto) {
        int i = 0;
        Connection connection = ConnectionClass.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE product SET name=?, category_id=? WHERE id = ?");
            preparedStatement.setString(1, dto.getName());
            preparedStatement.setLong(2, dto.getCategoryId());
            preparedStatement.setLong(3, dto.getId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update product :" + e.getMessage());
        }

        return i;
    }

    public int deleteProduct(Long id) {
        return 0;
    }
}
