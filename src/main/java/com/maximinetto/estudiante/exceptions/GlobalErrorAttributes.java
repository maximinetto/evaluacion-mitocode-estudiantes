package com.maximinetto.estudiante.exceptions;

import java.util.Map;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);

        if (getError(request) instanceof GlobalErrorException) {
            GlobalErrorException ex = (GlobalErrorException) getError(request);
            map.put("exception", ex.getClass().getSimpleName());
            map.put("message", ex.getMessage());
            map.put("cause", ex.getCausa());
            map.put("status", ex.getStatus().value());
            map.put("error", ex.getStatus().getReasonPhrase());

            return map;
        }

        map.put("exception", "SystemException");
        map.put("message", "System Error , Check logs!");
        map.put("status", "500");
        map.put("error", " System Error ");
        return map;
    }
}