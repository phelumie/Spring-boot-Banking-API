package com.microfinanceBank.apigateway.model;

import lombok.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
//@Value
@NoArgsConstructor
@AllArgsConstructor
public  class CachedRequest implements Serializable{
    RequestPath path;
    HttpMethod method;
    MultiValueMap<String, String> queryParams;



}
