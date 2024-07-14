package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.LotDto;
import spring.utils.ConnectionClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LotRepository {

    public int insertLot(LotDto dto) {
        int result = 0;
        Connection con = ConnectionClass.getConnection();
        try {

            PreparedStatement checkPs = con.prepareStatement(
                    "SELECT id, quantity FROM lot WHERE expired_date = ? AND price = ?"
            );
            checkPs.setDate(1, new java.sql.Date(dto.getExpiredDate().getTime()));
            checkPs.setDouble(2, dto.getPrice());
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                long existingLotId = rs.getLong("id");
                double existingQuantity = rs.getInt("quantity");

                PreparedStatement updatePs = con.prepareStatement(
                        "UPDATE lot SET quantity = ? WHERE id = ?"
                );
                updatePs.setDouble(1, existingQuantity + dto.getQuantity());
                updatePs.setLong(2, existingLotId);
                result = updatePs.executeUpdate();
            } else {

                String newlotId = generateLotId();
                dto.setLotNumber(newlotId);

                PreparedStatement insertPs = con.prepareStatement(
                        "INSERT INTO lot (product_id, lot_no, expired_date, price, date, uom, quantity)" +
                                "VALUES (?, ?, ?, ?, ?, ? ,?)"
                );
                insertPs.setLong(1, dto.getProductId());
                insertPs.setString(2, dto.getLotNumber());
                insertPs.setDate(3, new java.sql.Date(dto.getExpiredDate().getTime()));
                insertPs.setDouble(4, dto.getPrice());
                insertPs.setDate(5, new java.sql.Date(dto.getDate().getTime()));
                insertPs.setString(6, dto.getUom());
                insertPs.setInt(7, dto.getQuantity());


                result = insertPs.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Insert Lot: " + e.getMessage());
        }
        return result;
    }
    public String generateLotId() {
        String newLotId = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection con = ConnectionClass.getConnection();
            ps = con.prepareStatement("SELECT lot_no FROM lot ORDER BY id DESC LIMIT 1");
            rs = ps.executeQuery();

            if (rs.next()) {
                String lastlotId = rs.getString("lot_no");
                if (lastlotId != null && lastlotId.startsWith("LOT")) {
                    int num = Integer.parseInt(lastlotId.substring(3)) + 1;
                    newLotId = String.format("LOT%03d", num);
                } else {
                    newLotId = "LOT001";
                }
            } else {
                newLotId = "LOT001";
            }
        } catch (SQLException e) {
            System.out.println("lot No generation error: " + e.getMessage());
        }

        return newLotId;
    }

    public List<LotDto> getAllLots() {
        Connection con = ConnectionClass.getConnection();
        List<LotDto> lists = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT l.*, p.* FROM lot l " +
                            "INNER JOIN product p ON l.product_id = p.id "
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LotDto dto = new LotDto();
                dto.setId(rs.getLong("id"));
                dto.setProductId(rs.getLong("product_id"));
                dto.setProductName(rs.getString("name"));
                dto.setLotNumber(rs.getString("lot_no"));
                dto.setExpiredDate(rs.getDate("expired_date"));
                dto.setPrice(rs.getDouble("price"));
                dto.setDate(rs.getDate("date"));
                dto.setUom(rs.getString("uom"));
                dto.setQuantity(rs.getInt("quantity"));


                lists.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("Get All Lots: " + e.getMessage());
        }
        return lists;
    }

    public LotDto getLotById(int id) {
        LotDto lotDTO = null;
        Connection con = ConnectionClass.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT l.*, p.* FROM lot l " +
                            "INNER JOIN product p ON l.product_id = p.id "+
                            "WHERE l.id = ?"
            );
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lotDTO = new LotDto();
                lotDTO.setId(rs.getLong("id"));
                lotDTO.setProductId(rs.getLong("product_id"));
                lotDTO.setLotNumber(rs.getString("lot_no"));
                lotDTO.setExpiredDate(rs.getDate("expired_date"));
                lotDTO.setPrice(rs.getDouble("price"));
                lotDTO.setDate(rs.getDate("date"));
                lotDTO.setUom(rs.getString("uom"));
                lotDTO.setQuantity(rs.getInt("quantity"));

            }
        } catch (SQLException e) {
            System.out.println("Get Lot By Id: " + e.getMessage());
        }
        return lotDTO;
    }

    public int updateLot(LotDto dto) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE lot SET product_id = ?, expired_date = ?,price = ?,date = ?,uom = ?,quantity = ?" +
                            "WHERE id = ?"
            );

            ps.setLong(1, dto.getProductId());
            ps.setDate(2, new java.sql.Date(dto.getExpiredDate().getTime()));
            ps.setDouble(3, dto.getPrice());
            ps.setDate(4, new java.sql.Date(dto.getDate().getTime()));
            ps.setString(5, dto.getUom());
            ps.setDouble(6, dto.getQuantity());
            ps.setLong(7, dto.getId());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update Lot: " + e.getMessage());
        }
        return result;
    }

    public int softDeleteLot(int id) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE lotone SET deleted = TRUE WHERE id = ?");
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Soft Delete Lot: " + e.getMessage());
        }
        return result;
    }
}
