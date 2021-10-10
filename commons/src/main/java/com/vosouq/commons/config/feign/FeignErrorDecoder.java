package com.vosouq.commons.config.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vosouq.commons.model.ErrorMessage;
import feign.Request;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:common.properties")
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        String reason = response.reason() == null ? "Feign error in service call !!!" : response.reason();
        Request request = response.request();

        log.error("FeignErrorDecoder - request: [{} {}], reason: {}",
                request.httpMethod().name(),
                request.url(),
                reason);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ErrorMessage errorMessage = mapper.readValue(response.body().asInputStream(), ErrorMessage.class);

            log.error("throwing FeignDecodeException - errorMessage body: [code: {}, message: {}, exception: {}]",
                    errorMessage.getCode(),
                    errorMessage.getMessage(),
                    errorMessage.getException());

            return new FeignDecodeException(
                    response.status(),
                    reason,
                    errorMessage,
                    response.request());

        } catch (Throwable t) {
            log.error("throwing DecodeException - status: {}, reason: {}", response.status(), reason);
            return new DecodeException(response.status(), reason, response.request());
        }

    }

}
