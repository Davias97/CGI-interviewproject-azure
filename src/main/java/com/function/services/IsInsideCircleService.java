package com.function.services;

import com.function.dtos.IsInsideCircleRequestDTO;
import com.function.dtos.IsInsideCircleResponseDTO;
import com.function.models.Circle;
import com.function.repositories.IsInsideCircleRepository;

public class IsInsideCircleService {

    private final IsInsideCircleRepository isInsideCircleRepository = new IsInsideCircleRepository();

    public IsInsideCircleResponseDTO processIsInsideCircleData(IsInsideCircleRequestDTO requestDTO) {

        IsInsideCircleResponseDTO responseDTO = new IsInsideCircleResponseDTO();
        responseDTO.setCentre(requestDTO.getCentre());
        responseDTO.setRadius(requestDTO.getRadius());
        responseDTO.setPoint(requestDTO.getPoint());

        // Method execution
        Circle circle = new Circle(requestDTO.getCentre(), requestDTO.getRadius());
        responseDTO.setInsideCircle(circle.isPointInside(requestDTO.getPoint()));

        // Database interaction
        isInsideCircleRepository.insertCircleData(responseDTO);
        responseDTO.setId(isInsideCircleRepository.getRecordId(responseDTO));

        return responseDTO;
    }

    public IsInsideCircleResponseDTO processGetIsInsideCircleData(String id) {
        // Database interaction
        return isInsideCircleRepository.getCircleData(id);
    }
}
