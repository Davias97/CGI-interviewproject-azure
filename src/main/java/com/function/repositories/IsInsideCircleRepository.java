package com.function.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.function.dtos.IsInsideCircleResponseDTO;
import com.function.models.Point;
import com.function.utils.DatabaseConnection;
import com.function.utils.ErrorMessages;
import com.function.utils.SQLQueries;

public class IsInsideCircleRepository {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    public void insertCircleData(IsInsideCircleResponseDTO circleResponseDTO) {

        try (Connection connection = databaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    SQLQueries.INSERT_CIRCLE_DATA_SQL);

            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setDouble(2, circleResponseDTO.getCentre().getX());
            statement.setDouble(3, circleResponseDTO.getCentre().getY());
            statement.setDouble(4, circleResponseDTO.getRadius());
            statement.setDouble(5, circleResponseDTO.getPoint().getX());
            statement.setDouble(6, circleResponseDTO.getPoint().getY());
            statement.setBoolean(7, circleResponseDTO.isInsideCircle());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ErrorMessages.FAILED_TO_INSERT_DATA, e);
        }
    }

    public IsInsideCircleResponseDTO getCircleData(String id) {

        IsInsideCircleResponseDTO responseDTO = new IsInsideCircleResponseDTO();
        try (Connection connection = databaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQLQueries.GET_CIRCLE_DATA_SQL);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                responseDTO.setCentre(new Point(resultSet.getDouble("CIRCLE_X"), resultSet.getDouble("CIRCLE_Y")));
                responseDTO.setRadius(resultSet.getDouble("RADIUS"));
                responseDTO.setPoint(new Point(resultSet.getDouble("POINT_X"), resultSet.getDouble("POINT_Y")));
                responseDTO.setInsideCircle(resultSet.getBoolean("RESULT"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(ErrorMessages.FAILED_TO_GET_DATA, e);
        }
        return responseDTO;
    }

    public boolean existsById(String id) {

        try (Connection connection = databaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQLQueries.CHECK_IF_ID_EXISTS_SQL);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(ErrorMessages.ID_DOES_NOT_EXIST, e);
        }
        return false;
    }

    public String getRecordId(IsInsideCircleResponseDTO circleResponseDTO) {
        String id = null;
        try (Connection connection = databaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQLQueries.GET_RECORD_ID_SQL);
            statement.setDouble(1, circleResponseDTO.getCentre().getX());
            statement.setDouble(2, circleResponseDTO.getCentre().getY());
            statement.setDouble(3, circleResponseDTO.getRadius());
            statement.setDouble(4, circleResponseDTO.getPoint().getX());
            statement.setDouble(5, circleResponseDTO.getPoint().getY());
            statement.setBoolean(6, circleResponseDTO.isInsideCircle());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getString("ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(ErrorMessages.FAILED_TO_GET_DATA, e);
        }
        return id;
    }
}