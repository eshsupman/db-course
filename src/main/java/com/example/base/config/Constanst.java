package com.example.base.config;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.PublicKey;

public class Constanst {

    public static final String jwtSecret = "123456789012345678901234567890123456789012345678901234567890";
    public static SecretKey key = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
    public static PublicKey publicKey ;

}
