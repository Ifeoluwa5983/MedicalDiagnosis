package com.diagnosis.service;

import com.diagnosis.DiagnosisException.DiagnosisException;
import com.diagnosis.dtos.DiagnosedIssuesDto;
import com.diagnosis.entity.DiagnosedIssues;
import com.diagnosis.repository.DiagnosedIssuesRepository;
import com.diagnosis.request.Symptoms;
import com.diagnosis.response.LoginResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import Decoder.BASE64Encoder;
//import org.codehaus.jackson.map.ObjectMapper;
import org.apache.http.impl.client.HttpClients;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MedicalDiagnosisService {

    @Autowired
    DiagnosedIssuesRepository diagnosedIssuesRepository;

    ModelMapper modelMapper = new ModelMapper();

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    private CloseableHttpClient httpclient = HttpClients.createDefault();;

    String api_key = "o.ifeoluwah@gmail.com";
    static String secret_key = "y7EXn5g6RSi92Tpk8";

    public LoginResponse login() throws DiagnosisException {
                String url
                = "https://sandbox-authservice.priaid.ch/login";
        SecretKeySpec keySpec = new SecretKeySpec(
                secret_key.getBytes(),
                "HmacMD5");

        String computedHashString = "";
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(keySpec);
            byte[] result = mac.doFinal(url.getBytes());

            BASE64Encoder encoder = new BASE64Encoder();
            computedHashString = encoder.encode(result);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new DiagnosisException("Can not create token (NoSuchAlgorithmException)");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new DiagnosisException("Can not create token (InvalidKeyException)");
        }

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization", "Bearer " + api_key + ":" + computedHashString);

        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                System.out.println("Breaking");
                RetrieveException(response, objectMapper);
            }
            LoginResponse accessToken = objectMapper.readValue(response.getEntity().getContent(), LoginResponse.class);

            return accessToken;
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new DiagnosisException("Can not create token (ClientProtocolException)");
        } catch (IOException e) {
            e.printStackTrace();
            throw new DiagnosisException("Can not create token (IOException)");
        }
    }

    public Object getDiagnosis(Symptoms symptoms) throws DiagnosisException {
        String url
                = "https://sandbox-healthservice.priaid.ch/diagnosis";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("gender",symptoms.getGender())
                .queryParam("symptoms",symptoms.getSymptoms())
                .queryParam("year_of_birth",symptoms.getYearOfBirth())
                .queryParam("token",login().getToken())
                .queryParam("language","de-ch")
                .build();
        System.out.println(builder.toUriString());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Object> diagnosisResponseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request , Object.class);
        return diagnosisResponseEntity.getBody();
    }

    public void createDiagnosedIssues(DiagnosedIssuesDto diagnosedIssuesDto) throws DiagnosisException {
        if(diagnosedIssuesDto.getIssueName().isEmpty() || diagnosedIssuesDto.getAccuracy() == 0){
            throw new DiagnosisException("Please input the issue name and pass in a valid accuracy number");
        }
        DiagnosedIssues diagnosedIssues = new DiagnosedIssues();
        modelMapper.map(diagnosedIssuesDto,diagnosedIssues);
        diagnosedIssuesRepository.save(diagnosedIssues);
    }

    private void RetrieveException(CloseableHttpResponse response, ObjectMapper objectMapper) throws DiagnosisException, IOException {

        String errorMessage = objectMapper.readValue(response.getEntity().getContent(), String.class);
        System.out.println("Resposne with status code: " + response.getStatusLine().getStatusCode() + ", error message: " + errorMessage);
        throw new DiagnosisException(errorMessage);
    }
}
