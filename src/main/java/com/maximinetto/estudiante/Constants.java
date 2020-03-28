package com.maximinetto.estudiante;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    
    public static String id;
    
    @Value("${id.name}")
    public void setId(String id) {
	Constants.id = id;
    }
    
}
