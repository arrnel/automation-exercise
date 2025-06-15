package com.automationexercise.tests.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjToMapConverter {

    private static final ObjectMapper OM = new ObjectMapper();

    public static Map<String, Object> convertObjToMap(Object obj) {
        return OM.convertValue(obj, new TypeReference<>() {
        });
    }

}
