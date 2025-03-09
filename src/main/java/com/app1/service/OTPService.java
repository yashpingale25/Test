package com.app1.service;

import com.app1.payload.OTPDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    private final Map<String, OTPDetails> otpStorage = new HashMap<>();   // here we use hash map to store data in key value pair
    private static final int OTP_EXPIRY_TIME = 5;

                // Method to generate OTP
    public String generateOTP(String mobileNumber){                                    // 1.method call amd mobile number taken

                // Generate a 6 digit random OTP
       String otp = String.format("%06d", new Random().nextInt(999999));        //2. otp  generated

                //  STORES OTP AND ITS CREATION TIME IN THE MAP
        OTPDetails otpDetails = new OTPDetails(otp, System.currentTimeMillis());       //3. time alloted to otp and stored in OTPDetails object

                // stored in hashmap as KEY--->mobile & VALUE---->otpDetails
        otpStorage.put(mobileNumber, otpDetails);                                      //4. otpDetails object is mapped with mobile number as key-value pair
        return  otp;
    }

                            // METHOD TO VALIDATE OTP
    public boolean validateOTP(String mobileNumber, String otp){
        OTPDetails otpDetails = otpStorage.get(mobileNumber);

        if(otpDetails == null){
            return false;
        }
              // check if otp  is expired
        long currentTime = System.currentTimeMillis();    //this is taking current system time to generate otp
        long otpTime = otpDetails.getTimestamp();
        long timeDifference = TimeUnit.MILLISECONDS.toMinutes(currentTime-otpTime);

        if(timeDifference > OTP_EXPIRY_TIME){             // it will compare system time to expiry time
            otpStorage.remove(mobileNumber);              //it will remove otp from hashmap
            return false;
        }
              // validate otp
        return otpDetails.getOtp().equals(otp);
    }

}

