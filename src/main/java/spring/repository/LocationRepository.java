package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.CategoryDto;
import spring.dto.LocationDto;
import spring.model.Location;
import spring.utils.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationRepository {

    public int insertLocation(LocationDto locationDto) {

        int i = 0;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO location(name,address) VALUES (?,?)");
            preparedStatement.setString(1, locationDto.getName());
            preparedStatement.setString(2, locationDto.getAddress());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Location insert : " + e.getMessage());
        }
        return i;
    }

    public List<LocationDto> showAllLocations() {
        List<LocationDto> locationList = new ArrayList<LocationDto>();
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM location");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LocationDto locationDto = new LocationDto();
                locationDto.setId(resultSet.getLong("id"));
                locationDto.setName(resultSet.getString("name"));
                locationDto.setAddress(resultSet.getString("address"));
                locationList.add(locationDto);
            }

        } catch (SQLException e) {
            System.out.println("Location list : " + e.getMessage());
        }

        return locationList;
    }

    public LocationDto showLocation(LocationDto dto) {

        LocationDto locationDto = null;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM location WHERE id = ?");
            preparedStatement.setLong(1, dto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                locationDto = new LocationDto();
                locationDto.setId(resultSet.getLong("id"));
                locationDto.setName(resultSet.getString("name"));
                locationDto.setAddress(resultSet.getString("address"));
            }

        } catch (SQLException e) {
            System.out.println("Select one location: " + e.getMessage());
        }

        return locationDto;
    }

    public int updateLocation(LocationDto dto) {
        int i = 0;
        Connection connection = ConnectionClass.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE location SET name=?, address=? WHERE id = ?");
            preparedStatement.setString(1, dto.getName());
            preparedStatement.setString(2, dto.getAddress());
            preparedStatement.setLong(3, dto.getId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update location :" + e.getMessage());
        }

        return i;
    }

    public int deleteLocation(Long id) {
        return 0;
    }
}
