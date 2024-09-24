package com.wedding.backend.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wedding.backend.base.BaseResult;
import com.wedding.backend.dto.response.MomoOneTimePaymentCreateLinkResponse;
import com.wedding.backend.util.helper.HashHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MomoOneTimePaymentRequest {
    private String partnerCode;
    private String requestId;
    private BigDecimal amount;
    private String orderId;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String requestType;
    private String extraData;
    private String lang = "vi";
    private String signature;

    public MomoOneTimePaymentRequest(String partnerCode, String requestId,
                                     BigDecimal amount, String orderId, String orderInfo,
                                     String redirectUrl, String ipnUrl, String requestType,
                                     String extraData) {
        this.partnerCode = partnerCode;
        this.requestId = requestId;
        this.amount = amount;
        this.orderId = orderId;
        this.orderInfo = orderInfo;
        this.redirectUrl = redirectUrl;
        this.ipnUrl = ipnUrl;
        this.requestType = requestType;
        this.extraData = extraData;
        this.lang = "vi";
    }

    // Method to generate the signature
    public void makeSignature(String accessKey, String secretKey) {
        String rawHash = "accessKey=" + accessKey +
                "&amount=" + this.amount +
                "&extraData=" + this.extraData +
                "&ipnUrl=" + this.ipnUrl +
                "&orderId=" + this.orderId +
                "&orderInfo=" + this.orderInfo +
                "&partnerCode=" + this.partnerCode +
                "&redirectUrl=" + this.redirectUrl +
                "&requestId=" + this.requestId +
                "&requestType=" + this.requestType;
        this.signature = HashHelper.hmacSHA256(rawHash, secretKey);
    }

    // Method to create payment link
    public BaseResult getLink(String paymentUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectNode requestData = objectMapper.valueToTree(this);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(requestData.toString(), headers);

            String response = restTemplate.postForObject(paymentUrl, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            MomoOneTimePaymentCreateLinkResponse responseData = mapper.readValue(response, MomoOneTimePaymentCreateLinkResponse.class);

            if ("0".equals(responseData.getResultCode())) {
                return new BaseResult(true, responseData.getPayUrl());
            } else {
                return new BaseResult(false, responseData.getMessage());
            }

        } catch (JsonProcessingException e) {
            // Handle JSON processing error
            e.printStackTrace();
            return new BaseResult(false, "JSON processing error: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions
            e.printStackTrace();
            return new BaseResult(false, e.getMessage());
        }
    }

}
