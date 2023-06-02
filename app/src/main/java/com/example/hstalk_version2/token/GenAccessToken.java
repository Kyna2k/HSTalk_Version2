package com.example.hstalk_version2.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenAccessToken {
    private  static final String API_SID = "SK.0.pyKowpHNdD1s2Ur4SpQFEAFAPy3hG71k";
    private static final String API_SerectKey = "ZkE3SU80bU01VkdPMGpVWE5jS2V6cnNHTnlGWGVjQVo=";
    public static String genAccesToken(String user_id)
    {
        try{
            Algorithm algorithmHS = Algorithm.HMAC256(API_SerectKey);
            Map<String, Object> headerClaims = new HashMap<String, Object>();
            headerClaims.put("typ", "JWT");
            headerClaims.put("alg", "HS256");
            headerClaims.put("cty", "stringee-api;v=1");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY,30);
            calendar.getTime();

            String token = JWT.create().withHeader(headerClaims)
                    .withClaim("jti", API_SID + "-" + System.currentTimeMillis())
                    .withClaim("iss", API_SID)
                    .withExpiresAt(calendar.getTime())
                    .withClaim("userId", user_id)
                    .sign(algorithmHS);
            return token;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
