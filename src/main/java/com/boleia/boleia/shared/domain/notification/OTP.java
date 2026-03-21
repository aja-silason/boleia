package com.boleia.boleia.shared.domain.notification;

import lombok.Getter;

@Getter
public class OTP {
    
    private final String otp;
    private final String phoneNumber;
    private final long expiredAt;
    
    private OTP(
        String otp,
        String phoneNumber,
        long expiredAt
    ){
        this.otp = otp;
        this.phoneNumber = phoneNumber;
        this.expiredAt = expiredAt;
    }

    public static OTP buildOtp(
        String otp, 
        String phoneNumber,
        long expiredAt
    ){
        return new OTP(otp, phoneNumber, expiredAt);
    }

    public boolean isExpired(){
        return System.currentTimeMillis() > expiredAt;
    }


}
