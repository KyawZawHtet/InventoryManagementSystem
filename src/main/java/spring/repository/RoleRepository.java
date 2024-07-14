package spring.repository;

import org.springframework.stereotype.Repository;
import spring.dto.CategoryDto;
import spring.dto.RoleDto;
import spring.model.Role;
import spring.utils.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static spring.utils.ConnectionClass.getConnection;


@Repository
public class RoleRepository {

    public int insertRole(RoleDto roleDto) {

        int i = 0;
        Connection connection = getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO role(role_name) VALUES (?)");
            preparedStatement.setString(1, roleDto.getRoleName());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Role insert : " + e.getMessage());
        }
        return i;
    }

    public List<RoleDto> showAllRoles() {
        List<RoleDto> roleList = new ArrayList<RoleDto>();
        Connection connection = getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM role");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RoleDto roleDto = new RoleDto();
                roleDto.setId(resultSet.getLong("id"));
                roleDto.setRoleName(resultSet.getString("role_name"));
                roleList.add(roleDto);
            }

        } catch (SQLException e) {
            System.out.println("Role list : " + e.getMessage());
        }

        return roleList;
    }

    public RoleDto showRole(RoleDto dto) {

        RoleDto roleDto = null;
        Connection connection = getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM role WHERE id = ?");
            preparedStatement.setLong(1, dto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                roleDto = new RoleDto();
                roleDto.setId(resultSet.getLong("id"));
                roleDto.setRoleName(resultSet.getString("role_name"));
            }

        } catch (SQLException e) {
            System.out.println("Select one role: " + e.getMessage());
        }

        return roleDto;
    }

    public int updateRole(RoleDto dto) {
        int i = 0;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE role SET role_name=? WHERE id = ?");
            preparedStatement.setString(1, dto.getRoleName());
            preparedStatement.setLong(2, dto.getId());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update role :" + e.getMessage());
        }

        return i;
    }

    public int deleteRole(Long id) {
        return 0;
    }
}
