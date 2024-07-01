package com.function.functions;

import java.io.IOException;
import java.util.Optional;

import com.function.dtos.IsInsideCircleRequestDTO;
import com.function.dtos.IsInsideCircleResponseDTO;
import com.function.services.IsInsideCircleService;
import com.function.utils.RequestValidator;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class IsInsideCircleFunction {

    private final IsInsideCircleService circleService = new IsInsideCircleService();
    private final RequestValidator requestValidator = new RequestValidator();

    @FunctionName("isInsideCircle")
    public HttpResponseMessage isInsideMethod(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION)
            HttpRequestMessage<Optional<String>> request) {

        IsInsideCircleResponseDTO responseDTO;
        try {
            IsInsideCircleRequestDTO requestDTO = requestValidator.validateRequestBody(request.getBody());
            responseDTO = circleService.processIsInsideCircleData(requestDTO);
        } catch (IOException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body(e.getMessage()).build();
        }
        return request.createResponseBuilder(HttpStatus.OK).body(responseDTO).build();
    }
}