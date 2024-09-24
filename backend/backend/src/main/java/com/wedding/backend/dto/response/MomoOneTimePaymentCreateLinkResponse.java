package com.wedding.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MomoOneTimePaymentCreateLinkResponse {
    private String partnerCode;
    private String requestId;
    private String orderId;
    private long amount;
    private long responseTime;
    private String message;
    private String resultCode;
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
    private String applink;
}
