package spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import spring.dto.LocationDto;
import spring.utils.ConnectionClass;

@Repository
public class LocationRepository {
	public int insertLocation(LocationDto dto) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO location (name, address) values (?,?)");
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getAddress());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert Location: " + e.getMessage());
        }
        return result;
    }

    public List<LocationDto> getAllLocations() {
        Connection con = ConnectionClass.getConnection();
        List<LocationDto> lists = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM location");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocationDto dto = new LocationDto();
                dto.setId(rs.getLong("id"));
                dto.setName(rs.getString("name"));
                dto.setAddress(rs.getString("address"));
                
                lists.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("Get All Locations: " + e.getMessage());
        }
        return lists;
    }
    
    public List<LocationDto> searchLocationsByNameAndAddress(String locationName, String locationAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LocationDto> locations = new ArrayList<>();

        try {
            con = ConnectionClass.getConnection();
            String sql = "SELECT id, name, address FROM location WHERE "
                       + "(name LIKE ? OR ? IS NULL) "
                       + "AND (address LIKE ? OR ? IS NULL)";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + locationName + "%");
            ps.setString(2, locationName);
            ps.setString(3, "%" + locationAddress + "%");
            ps.setString(4, locationAddress);

            rs = ps.executeQuery();

            while (rs.next()) {
                LocationDto location = new LocationDto();
                location.setId(rs.getLong("id"));
                location.setName(rs.getString("name"));
                location.setAddress(rs.getString("address"));

                locations.add(location);
            }
        } catch (SQLException e) {
            System.out.println("Search Locations: " + e.getMessage());
        }

        return locations;
    }
    
    public boolean checkWarehousename(String name) {
        Connection con = ConnectionClass.getConnection();
        boolean status = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM location WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = true;
            }
        } catch (SQLException e) {
            System.out.println("Already warehousename error: " + e.getMessage());
        }
        return status;
    }

    public LocationDto getLocationById(int id) {
        LocationDto locationDTO = null;
        Connection con = ConnectionClass.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM location WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                locationDTO = new LocationDto();
                locationDTO.setId(rs.getLong("id"));
                locationDTO.setName(rs.getString("name"));
                locationDTO.setAddress(rs.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println("Get Location By Id: " + e.getMessage());
        }
        return locationDTO;
    }

    public int updateLocation(LocationDto dto) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE location SET name = ?, address = ? WHERE id = ?");
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getAddress());
            
            ps.setLong(3, dto.getId());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update Location: " + e.getMessage());
        }
        return result;
    }

    public int softDeleteLocation(int id) {
        Connection con = ConnectionClass.getConnection();
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE location SET deleted = TRUE WHERE location_id = ?");
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Soft Delete Location: " + e.getMessage());
        }
        return result;
    }
}
