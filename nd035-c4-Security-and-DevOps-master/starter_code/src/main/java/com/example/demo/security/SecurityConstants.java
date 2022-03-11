package com.example.demo.security;



//added few security constants that are needed in this application
//added expiration which is configurable
//added secret which is my secret
//added token prefix which is used by the user when they logged in successfully

//added header string and sign up url

public class SecurityConstants {

    public static final String SECRET = "oursecretkey";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
}