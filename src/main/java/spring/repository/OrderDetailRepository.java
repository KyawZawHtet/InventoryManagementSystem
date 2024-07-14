package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.OrderDetailDto;
import spring.utils.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDetailRepository {

    public int insertOrderDetail(OrderDetailDto orderDetailDto) {

        int i = 0;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO order_detail(quantity,product_id,order_id) VALUES (?,?,?)");
            preparedStatement.setInt(1, orderDetailDto.getQuantity());
            preparedStatement.setLong(2, orderDetailDto.getProductId());
            preparedStatement.setLong(3, orderDetailDto.getOrderId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("OrderDetail insert : " + e.getMessage());
        }
        return i;
    }

    public List<OrderDetailDto> showAllOrderDetails() {
        List<OrderDetailDto> orderDetailList = new ArrayList<OrderDetailDto>();
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM order_detail");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetailDto orderDetailDto = new OrderDetailDto();
                orderDetailDto.setId(resultSet.getLong("id"));
                orderDetailDto.setQuantity(resultSet.getInt("quantity"));
                orderDetailDto.setProductId(resultSet.getLong("product_id"));
                orderDetailDto.setOrderId(resultSet.getLong("order_id"));
                orderDetailList.add(orderDetailDto);
            }

        } catch (SQLException e) {
            System.out.println("OrderDetail list : " + e.getMessage());
        }

        return orderDetailList;
    }

    public OrderDetailDto showOrderDetail(OrderDetailDto dto) {

        OrderDetailDto orderDetailDto = null;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM order_detail WHERE id = ?");
            preparedStatement.setLong(1, dto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderDetailDto = new OrderDetailDto();
                orderDetailDto.setId(resultSet.getLong("id"));
                orderDetailDto.setQuantity(resultSet.getInt("quantity"));
                orderDetailDto.setProductId(resultSet.getLong("product_id"));
                orderDetailDto.setOrderId(resultSet.getLong("order_id"));
            }

        } catch (SQLException e) {
            System.out.println("Select one order detail: " + e.getMessage());
        }

        return orderDetailDto;
    }

    public int updateOrderDetail(OrderDetailDto dto) {
        int i = 0;
        Connection connection = ConnectionClass.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE order_detail SET quantity=?, product_id=?, order_id=? WHERE id = ?");
            preparedStatement.setInt(1, dto.getQuantity());
            preparedStatement.setLong(2, dto.getProductId());
            preparedStatement.setLong(3, dto.getOrderId());
            preparedStatement.setLong(4, dto.getId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update order detail :" + e.getMessage());
        }

        return i;
    }

    public int deleteOrderDetail(Long id) {
        return 0;
    }
}
