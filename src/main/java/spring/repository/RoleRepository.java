package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.RoleDto;
import spring.dto.UserDto;
import spring.model.Role;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static spring.utils.ConnectionClass.getConnection;

@Repository
public class RoleRepository {

    public int insert(RoleDto roleDto) {
        int i = 0;
        String insertQuery = "INSERT INTO role(role) VALUES (?)";
        try (var con = getConnection(); var ps = con.prepareStatement(insertQuery)) {
            ps.setString(1, roleDto.getRole());
            i = ps.executeUpdate();
        } catch(Exception e) {
            System.out.println("Insert UserRole : " + e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    public int updateUserRoleById(RoleDto req) {
        int i = 0;
        String updateQuery  = "UPDATE role SET role = ? WHERE id = ?";
        try(var con = getConnection(); var ps = con.prepareStatement(updateQuery)) {
            ps.setString(1, req.getRole());
            ps.setLong(2, req.getId());
            i = ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Update User Role By Id : " + e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    public RoleDto showUserRoleById(Long id) {
        String selectQuery = "SELECT * FROM role WHERE id = ?";
        RoleDto userRole = null;
        try(var con = getConnection(); var ps = con.prepareStatement(selectQuery)) {
            ps.setLong(1, id);
            var rs = ps.executeQuery();
            while(rs.next()) {
                userRole = new RoleDto();
                userRole.setId(rs.getLong("id"));
                userRole.setRole(rs.getString("role"));
            }
        }catch(Exception e) {
            System.out.println("Show User Role DTo By Id : " + e.getMessage());
            e.printStackTrace();
        }
        return userRole;
    }

    public int deleteUseRoleById(Long id) {
        int i = 0;

        String deleteQuery = "DELETE FROM role WHERE id = ?";
        try(var con = getConnection(); var ps = con.prepareStatement(deleteQuery)) {
            ps.setLong(1, id);
            i = ps.executeUpdate();
        }catch(SQLException e) {
            System.out.println("Delete User Role By Id : "+ e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    public List<RoleDto> showAllUserRole() {
        String selectQuery = "SELECT * FROM role";
        List<RoleDto> roleDtoList = new ArrayList<>();
        try(var con = getConnection(); var ps = con.prepareStatement(selectQuery)) {
            var rs = ps.executeQuery();
            while(rs.next()) {
                RoleDto roleDto = new RoleDto();
                roleDto.setId(rs.getLong("id"));
                roleDto.setRole(rs.getString("role"));
                roleDtoList.add(roleDto);
            }
        }catch(Exception e) {
            System.out.println("Show All User Role  : " + e.getMessage());
            e.printStackTrace();
        }
        return roleDtoList;
    }
}
