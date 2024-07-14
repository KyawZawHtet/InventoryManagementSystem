package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.CategoryDto;
import spring.dto.UserDto;
import spring.utils.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    public int insertUser(UserDto userDto) {

        int i = 0;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(username,email,password,phone_number,status,role_id) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, userDto.getUsername());
            preparedStatement.setString(2, userDto.getEmail());
            preparedStatement.setString(3, userDto.getPassword());
            preparedStatement.setString(4, userDto.getPhoneNumber());
            preparedStatement.setBoolean(5, userDto.isStatus());
            preparedStatement.setLong(6, userDto.getRoleId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("User insert : " + e.getMessage());
        }
        return i;
    }

    public List<UserDto> showAllUsers() {
        List<UserDto> userList = new ArrayList<UserDto>();
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserDto userDto = new UserDto();
                userDto.setId(resultSet.getLong("id"));
                userDto.setUsername(resultSet.getString("username"));
                userDto.setEmail(resultSet.getString("email"));
                userDto.setPassword(resultSet.getString("password"));
                userDto.setPhoneNumber(resultSet.getString("phone_number"));
                userDto.setStatus(resultSet.getBoolean("status"));
                userDto.setRoleId(resultSet.getLong("role_id"));
                userList.add(userDto);
            }

        } catch (SQLException e) {
            System.out.println("User list : " + e.getMessage());
        }

        return userList;
    }

    public UserDto showUser(UserDto dto) {

        UserDto userDto = null;
        Connection connection = ConnectionClass.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE id = ?");
            preparedStatement.setLong(1, dto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userDto = new UserDto();
                userDto.setId(resultSet.getLong("id"));
                userDto.setUsername(resultSet.getString("username"));
                userDto.setEmail(resultSet.getString("email"));
                userDto.setPassword(resultSet.getString("password"));
                userDto.setPhoneNumber(resultSet.getString("phone_number"));
                userDto.setStatus(resultSet.getBoolean("status"));
                userDto.setRoleId(resultSet.getLong("role_id"));
            }

        } catch (SQLException e) {
            System.out.println("Select one user: " + e.getMessage());
        }

        return userDto;
    }

    public int updateUser(UserDto dto) {
        int i = 0;
        Connection connection = ConnectionClass.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET username=?,email=?,password=?,phone_number=?,status=?,role_id=? WHERE id = ?");
            preparedStatement.setString(1, dto.getUsername());
            preparedStatement.setString(2, dto.getEmail());
            preparedStatement.setString(3, dto.getPassword());
            preparedStatement.setString(4, dto.getPhoneNumber());
            preparedStatement.setBoolean(5, dto.isStatus());
            preparedStatement.setLong(6, dto.getRoleId());
            preparedStatement.setLong(7, dto.getId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update user :" + e.getMessage());
        }

        return i;
    }

    public int deleteUser(Long id) {
        return 0;
    }

    public boolean checkEmail(String email) {

        Connection connection = ConnectionClass.getConnection();
        boolean status = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                status = true;
            }

        } catch (Exception e) {
            System.out.println("Check email " + e.getMessage());
        }

        return status;
    }

    public UserDto signinUser(UserDto dto) {
        UserDto userDto = null;
        Connection connection = ConnectionClass.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user where email = ? and password = ?");
            preparedStatement.setString(1, dto.getEmail());
            preparedStatement.setString(2, dto.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userDto = new UserDto();
                userDto.setEmail(resultSet.getString("email"));
                userDto.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Signin user : " + e.getMessage());
        }
        return userDto;
    }

}
