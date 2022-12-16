package com.microfinanceBank.apigateway.model;


import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
//@TypeAlias("response")
//@Value
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("MyCache")
public class CachedResponse implements Serializable {

    HttpStatus httpStatus;
    Map<String,String> headers;
    byte[] body;

}


