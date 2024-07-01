package com.function.utils;

public class SQLQueries {
    public static final String INSERT_CIRCLE_DATA_SQL =
            "INSERT INTO Table_1 (DATE_CREATED, CIRCLE_X, CIRCLE_Y, RADIUS, POINT_X, POINT_Y, RESULT) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_CIRCLE_DATA_SQL =
            "SELECT * FROM Table_1 WHERE ID = ?";
    public static final String CHECK_IF_ID_EXISTS_SQL =
            "SELECT * FROM Table_1 WHERE ID = ?";
    public static final String GET_RECORD_ID_SQL =
            "SELECT ID FROM Table_1 WHERE CIRCLE_X = ? AND CIRCLE_Y = ? AND RADIUS = ? AND POINT_X = ? AND POINT_Y = ? AND RESULT = ?";
}

