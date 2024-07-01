package com.function.dtos;

import com.function.models.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsInsideCircleResponseDTO {

    private boolean isInsideCircle;
    private Point centre;
    private double radius;
    private Point point;
    private String id;
}
