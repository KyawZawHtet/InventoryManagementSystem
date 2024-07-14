package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.InvoiceDto;
import spring.dto.OrderDetailDto;
import spring.utils.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceRepository {

    public int insertInvoice(InvoiceDto invoiceDto) {

        int i = 0;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO invoice(quantity,product_id,order_id) VALUES (?,?,?)");
            preparedStatement.setInt(1, invoiceDto.getQuantity());
            preparedStatement.setLong(2, invoiceDto.getProductId());
            preparedStatement.setLong(3, invoiceDto.getOrderId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Invoice insert : " + e.getMessage());
        }
        return i;
    }

    public List<InvoiceDto> showAllInvoices() {
        List<InvoiceDto> invoiceList = new ArrayList<InvoiceDto>();
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM invoice");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                InvoiceDto invoiceDto = new InvoiceDto();
                invoiceDto.setId(resultSet.getLong("id"));
                invoiceDto.setQuantity(resultSet.getInt("quantity"));
                invoiceDto.setProductId(resultSet.getLong("product_id"));
                invoiceDto.setOrderId(resultSet.getLong("order_id"));
                invoiceList.add(invoiceDto);
            }

        } catch (SQLException e) {
            System.out.println("Invoice list : " + e.getMessage());
        }

        return invoiceList;
    }

    public InvoiceDto showInvoice(InvoiceDto dto) {

        InvoiceDto invoiceDto = null;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM invoice WHERE id = ?");
            preparedStatement.setLong(1, dto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                invoiceDto = new InvoiceDto();
                invoiceDto.setId(resultSet.getLong("id"));
                invoiceDto.setQuantity(resultSet.getInt("quantity"));
                invoiceDto.setProductId(resultSet.getLong("product_id"));
                invoiceDto.setOrderId(resultSet.getLong("order_id"));
            }

        } catch (SQLException e) {
            System.out.println("Select one invoice: " + e.getMessage());
        }

        return invoiceDto;
    }

    public int updateInvoice(InvoiceDto dto) {
        int i = 0;
        Connection connection = ConnectionClass.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE invoice SET quantity=?, product_id=?, order_id=? WHERE id = ?");
            preparedStatement.setInt(1, dto.getQuantity());
            preparedStatement.setLong(2, dto.getProductId());
            preparedStatement.setLong(3, dto.getOrderId());
            preparedStatement.setLong(4, dto.getId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update invoice :" + e.getMessage());
        }

        return i;
    }

    public int deleteInvoice(Long id) {
        return 0;
    }
}
