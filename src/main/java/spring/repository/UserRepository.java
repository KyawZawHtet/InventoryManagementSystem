package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static spring.utils.ConnectionClass.getConnection;

@Repository
public class UserRepository {

    public int insert (UserDto userDto) {
        int i = 0;
        String insertQuery = "INSERT INTO user(username, email, password, phone_number, status, role_id) VALUES (?,?,?,?,?,?)";
        try (var con = getConnection(); var ps = con.prepareStatement(insertQuery)) {
            ps.setString(1, userDto.getUsername());
            ps.setString(2, userDto.getEmail());
            ps.setString(3, userDto.getPassword());
            ps.setString(4, userDto.getPhoneNumber());
            ps.setBoolean(5, userDto.isStatus());
            ps.setLong(6, userDto.getRoleId());

            i = ps.executeUpdate();
        } catch(Exception e) {
            System.out.println("Insert User : " + e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    public UserDto showUserById(Long id) {
        String selectQuery = "SELECT * FROM user WHERE id = ?";
        UserDto user = null;
        try(var con = getConnection(); var ps = con.prepareStatement(selectQuery)) {
            ps.setLong(1, id);
            var rs = ps.executeQuery();
            while(rs.next()) {
                user = new UserDto();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setStatus(rs.getBoolean("status"));
                user.setRoleId(rs.getLong("role_id"));
            }
        } catch(SQLException e) {
            System.out.println("Show User By Id : " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    public int deleteUserById(Long id) {
        int i = 0;

        String deleteQuery = "DELETE FROM user WHERE id = ?";
        try(var con = getConnection(); var ps = con.prepareStatement(deleteQuery)) {
            ps.setLong(1, id);
            i = ps.executeUpdate();
        }catch(SQLException e) {
            System.out.println("Delete User By Id : "+ e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    public int updateUserById(UserDto userDto) {
        int i = 0;
        String updateQuery  = "update user set username = ?, email = ?, password = ?, phone_number = ?, status = ?, role_id = ? where id = ?";
        try(var con = getConnection(); var ps = con.prepareStatement(updateQuery)) {
            ps.setString(1, userDto.getUsername());
            ps.setString(2, userDto.getEmail());
            ps.setString(3, userDto.getPassword());
            ps.setString(4, userDto.getPhoneNumber());
            ps.setBoolean(5, userDto.isStatus());
            ps.setLong(6, userDto.getRoleId());
            ps.setLong(7, userDto.getId());

            i = ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Update User By Id : " + e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    public List<UserDto> showAllUser() {
        String selectQuery = "SELECT * FROM user";
        List<UserDto> userList = new ArrayList<>();
        try(var con = getConnection(); var ps = con.prepareStatement(selectQuery)) {
            var rs = ps.executeQuery();
            while(rs.next()) {
                UserDto user = new UserDto();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setStatus(rs.getBoolean("status"));
                user.setRoleId(rs.getLong("role_id"));
                userList.add(user);
            }
        }catch(Exception e) {
            System.out.println("Show All User : " + e.getMessage());
            e.printStackTrace();
        }
        return userList;
    }
}
