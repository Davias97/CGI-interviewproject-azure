package com.function.dtos;

import com.function.models.Point;
import com.function.utils.ErrorMessages;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsInsideCircleRequestDTO {

    @NotNull(message = ErrorMessages.MISSING_CENTRE)
    private Point centre;

    @NotNull(message = ErrorMessages.MISSING_RADIUS)
    @Positive(message = ErrorMessages.INVALID_RADIUS)
    private Double radius;

    @NotNull(message = ErrorMessages.MISSING_POINT)
    private Point point;
}
