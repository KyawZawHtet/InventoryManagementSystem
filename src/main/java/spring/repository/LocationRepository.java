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
            PreparedStatement ps = con.prepareStatement("INSERT INTO location (location_name, address) values (?,?)");
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
                dto.setName(rs.getString("location_name"));
                dto.setAddress(rs.getString("address"));
                
                lists.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("Get All Locations: " + e.getMessage());
        }
        return lists;
    }
    
    public List<LocationDto> searchLocationsBylocation_nameAndAddress(String location_name, String locationAddress) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LocationDto> locations = new ArrayList<>();

        try {
            con = ConnectionClass.getConnection();
            String sql = "SELECT id, location_name, address FROM location WHERE "
                       + "(location_name LIKE ? OR ? IS NULL) "
                       + "AND (address LIKE ? OR ? IS NULL)";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + location_name + "%");
            ps.setString(2, location_name);
            ps.setString(3, "%" + locationAddress + "%");
            ps.setString(4, locationAddress);

            rs = ps.executeQuery();

            while (rs.next()) {
                LocationDto location = new LocationDto();
                location.setId(rs.getLong("id"));
                location.setName(rs.getString("location_name"));
                location.setAddress(rs.getString("address"));

                locations.add(location);
            }
        } catch (SQLException e) {
            System.out.println("Search Locations: " + e.getMessage());
        }

        return locations;
    }
    
    public boolean checkWarehouselocation_name(String location_name) {
        Connection con = ConnectionClass.getConnection();
        boolean status = false;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM location WHERE location_name=?");
            ps.setString(1, location_name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = true;
            }
        } catch (SQLException e) {
            System.out.println("Already warehouselocation_name error: " + e.getMessage());
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
                locationDTO.setName(rs.getString("location_name"));
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
            PreparedStatement ps = con.prepareStatement("UPDATE location SET location_name = ?, address = ? WHERE id = ?");
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