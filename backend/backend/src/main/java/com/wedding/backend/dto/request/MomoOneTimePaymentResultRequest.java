package com.wedding.backend.dto.request;

import com.wedding.backend.util.helper.HashHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomoOneTimePaymentResultRequest {
    private String partnerCode;
    private String orderId;
    private String requestId;
    private long amount;
    private String orderInfo;
    private String orderType;
    private String transId;
    private String message;
    private int resultCode;
    private String payType;
    private long responseTime;
    private String extraData;
    private String signature;

    // Method to check if the signature is valid
    public boolean isValidSignature(String accessKey, String secretKey) {
        String rawHash = "accessKey=" + accessKey +
                "&amount=" + this.amount +
                "&extraData=" + this.extraData +
                "&message=" + this.message +
                "&orderId=" + this.orderId +
                "&orderInfo=" + this.orderInfo +
                "&orderType=" + this.orderType +
                "&partnerCode=" + this.partnerCode +
                "&payType=" + this.payType +
                "&requestId=" + this.requestId +
                "&responseTime=" + this.responseTime +
                "&resultCode=" + this.resultCode +
                "&transId=" + this.transId;

        String checkSignature = HashHelper.hmacSHA256(rawHash, secretKey);
        return this.signature.equals(checkSignature);
    }
}
