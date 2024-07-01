package com.function.utils;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.function.dtos.IsInsideCircleRequestDTO;
import com.function.repositories.IsInsideCircleRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class RequestValidator {

    private final IsInsideCircleRepository circleRepository = new IsInsideCircleRepository();

    public IsInsideCircleRequestDTO validateRequestBody(Optional<String> requestBody) throws IOException {

        // Validation for missing request body
        if (requestBody.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.MISSING_BODY);
        }

        // Parsing request body to JSON object
        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(requestBody.get());
        } catch (JsonParseException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_DATA_FORMAT);
        }

        // Check for unexpected properties
        if (jsonNode.fieldNames().hasNext()) {
            String fieldName = jsonNode.fieldNames().next();
            if (!fieldName.equals("centre") && !fieldName.equals("radius") && !fieldName.equals("point")) {
                throw new IllegalArgumentException(ErrorMessages.UNEXPECTED_PROPERTY);
            }
        }

        // Check for missing or null properties
        if (!jsonNode.has("centre") || jsonNode.get("centre").isNull() ||
                !jsonNode.get("centre").has("x") || jsonNode.get("centre").get("x").isNull() ||
                !jsonNode.get("centre").has("y") || jsonNode.get("centre").get("y").isNull() ||
                !jsonNode.has("radius") || jsonNode.get("radius").isNull() ||
                !jsonNode.has("point") || jsonNode.get("point").isNull() ||
                !jsonNode.get("point").has("x") || jsonNode.get("point").get("x").isNull() ||
                !jsonNode.get("point").has("y") || jsonNode.get("point").get("y").isNull()) {
            throw new IllegalArgumentException(ErrorMessages.INCOMPLETE_REQUEST_BODY);
        }

        // Check for invalid property values
        if (jsonNode.get("radius").asDouble() <= 0) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_RADIUS);
        }

        IsInsideCircleRequestDTO circleRequestDTO;
        try {
            circleRequestDTO = new ObjectMapper().readValue(requestBody.get(), IsInsideCircleRequestDTO.class);
        } catch (JsonParseException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_DATA_FORMAT);
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<IsInsideCircleRequestDTO>> violations = validator.validate(circleRequestDTO);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<IsInsideCircleRequestDTO> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }

        return circleRequestDTO;
    }

    public String validateId(String id) {

        if (id.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.MISSING_ID);
        }

        try {
            int idAsInt = Integer.parseInt(id);
            if (idAsInt <= 0) {
                throw new IllegalArgumentException(ErrorMessages.INVALID_ID_FORMAT);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_ID_FORMAT);
        }

        if (!circleRepository.existsById(id)) {
            throw new IllegalArgumentException(ErrorMessages.ID_DOES_NOT_EXIST);
        }
        return id;
    }
}