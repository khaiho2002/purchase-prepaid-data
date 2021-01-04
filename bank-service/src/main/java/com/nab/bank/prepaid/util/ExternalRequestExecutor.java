package com.nab.bank.prepaid.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalRequestExecutor {

  private static final Logger log = LogManager.getLogger(ExternalRequestExecutor.class);

  public final static String AUTHORIZATION = "Authorization";
  public final static String BASIC = "Basic ";

  private static final RestTemplate restTemplate = new RestTemplateBuilder().build();

  public <T> ResponseEntity<T> getWithResponseHeader(String url, String username, String password, Class<T> responseType) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String auth = username + ":" + password;
    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
    headers.set(AUTHORIZATION, BASIC + new String(encodedAuth));

    return get(url, headers, responseType);
  }

  private <T> ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType) {
    log.info("Execute get request with url: {}", url);
    HttpEntity<String> header = new HttpEntity<>(headers);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, header, responseType);
    log.info("Response {}", response.getBody());
    return response;
  }

  public <T> ResponseEntity<T> execute(String url, HttpMethod method, String username, String password, Object request, Class<T> responseType)
      throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String auth = username + ":" + password;
    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
    headers.set(AUTHORIZATION, BASIC + new String(encodedAuth));

    return execute(url, method, headers, request, responseType);
  }

  private <T> ResponseEntity<T> execute(String url, HttpMethod method, HttpHeaders headers, Object request, Class<T> responseType) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      String json = mapper.writeValueAsString(request);
      log.info("Execute {} request with url {} and body {}", method.name(), url, json);

      HttpEntity<Object> entity = new HttpEntity<>(request, headers);
      ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);

      ObjectMapper jsonMapper = new ObjectMapper();
      log.info("Response {}", jsonMapper.writeValueAsString(response.getBody()));

      return response;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return null;
  }
}
