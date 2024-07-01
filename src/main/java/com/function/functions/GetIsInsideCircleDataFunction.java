package com.function.functions;

import com.function.dtos.IsInsideCircleResponseDTO;
import com.function.services.IsInsideCircleService;
import com.function.utils.RequestValidator;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class GetIsInsideCircleDataFunction {

    private final IsInsideCircleService circleService = new IsInsideCircleService();
    private final RequestValidator requestValidator = new RequestValidator();

    @FunctionName("getIsInsideCircleData")
    public HttpResponseMessage getIsInsideCircleData(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.FUNCTION, route = "getIsInsideCircleData/{id}")
            HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String queryId){

        IsInsideCircleResponseDTO responseDTO;
        try {
            String validatedId = requestValidator.validateId(queryId);
            responseDTO = circleService.processGetIsInsideCircleData(validatedId);
        } catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body(e.getMessage()).build();
        }
        return request.createResponseBuilder(HttpStatus.OK).body(responseDTO).build();
    }
}

