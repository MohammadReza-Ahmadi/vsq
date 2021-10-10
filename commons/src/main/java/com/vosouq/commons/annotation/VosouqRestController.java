package com.vosouq.commons.annotation;

import com.vosouq.commons.model.ErrorMessage;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage.class),
        @ApiResponse(code = 401, message = "Unauthorized - Error Message Same As 400"),
        @ApiResponse(code = 403, message = "Forbidden - Error Message Same As 400"),
        @ApiResponse(code = 404, message = "Not Found - Error Message Same As 400"),
        @ApiResponse(code = 405, message = "Method Not Allowed - Error Message Same As 400"),
        @ApiResponse(code = 406, message = "Not Acceptable - Error Message Same As 400"),
        @ApiResponse(code = 408, message = "Request Timeout - No Error Message"),
        @ApiResponse(code = 409, message = "Conflict(Already Exist) - Error Message Same As 400"),
        @ApiResponse(code = 415, message = "Unsupported Media Type - Error Message Same As 400"),
        @ApiResponse(code = 500, message = "Internal Server Error - Error Message Same As 400")
})
@RestController
public @interface VosouqRestController {
}