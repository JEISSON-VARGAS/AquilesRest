package com.api.aquilesApi.Utilities.Http;



import org.springframework.http.HttpStatus;

import java.util.*;

public class ResponseHttpApi {

    public static final String CODE_OK="200";
    public static final String CODE_BAD="400";
    public static final String NO_CONTENT="204";

    //findAll
    public static Map<String, Object> responseHttpFindAll(Object data, String code,String msm, int size, int page, int items) {
        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", code);
        response.put("message", msm);
        response.put("currentPage", page);
        response.put("totalItems", items);
        response.put("totalPages", size);
        response.put("data",data);

        return response;
    }

    //findById
    public static Map<String, Object> responseHttpFindId(Object data, String code,String msm) {

        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", code);
        response.put("message", msm);
        response.put("data",data);

        return response;
    }

    //Post, Put and Delete
    public static Map<String, Object> responseHttpAction(String code, String msm) {

        Map<String,Object> response = new HashMap<>();

        response.put("date",new Date());
        response.put("code",code);
        response.put("message",msm);
        return response;
    }

    //Error
    public static Map<String,Object> responseHttpError(String result,HttpStatus codeMessage,String data){
        Map<String,Object> response = new HashMap<>();

        response.put("date",new Date());
        response.put("code",codeMessage.value());
        response.put("message",result);
        response.put("data",data);

        return response;
    }
}


/*
package com.api.aquilesApi.Utilities.Http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHttpApi {

    public static final String CODE_OK = "200";
    public static final String CODE_BAD = "400";
    public static final String NO_CONTENT = "204";

    // Método genérico para respuestas estándar
    public static ResponseEntity<Map<String, Object>> generateResponse(String message, HttpStatus status, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", status.value());
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }

    // findAll
    public static ResponseEntity<Map<String, Object>> responseHttpFindAll(Object data, String message, int size, int page, int items) {
        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", CODE_OK);
        response.put("message", message);
        response.put("currentPage", page);
        response.put("totalItems", items);
        response.put("totalPages", size);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // findById
    public static ResponseEntity<Map<String, Object>> responseHttpFindId(Object data, String message) {
        return generateResponse(message, HttpStatus.OK, data);
    }

    // Post, Put and Delete
    public static ResponseEntity<Map<String, Object>> responseHttpAction(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }

    // Error
    public static ResponseEntity<Map<String, Object>> responseHttpError(String message, HttpStatus status, String details) {
        Map<String, Object> response = new HashMap<>();
        response.put("date", new Date());
        response.put("code", status.value());
        response.put("message", message);
        response.put("details", details);

        return new ResponseEntity<>(response, status);
    }
}

 */