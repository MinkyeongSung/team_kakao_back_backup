package com.example.team3_kakaotalk._core.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.team3_kakaotalk._core.handler.exception.MyUnAuthorizedException;
import com.example.team3_kakaotalk._core.utils.JwtTokenUtils;
import com.example.team3_kakaotalk.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;

public class JwtAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }

        String jwt = req.getHeader("Authorization");
        if (jwt == null || jwt.isEmpty()) {
            onError(resp, "토큰이 없습니다");
            return;
        }

        try {

            DecodedJWT decodedJWT = JwtTokenUtils.verify(jwt);
            System.out.println("=================");
            System.out.println(decodedJWT.getClaim("createdAt").asString());
            System.out.println("====================");
            int userId = decodedJWT.getClaim("id").asInt();
            String email = decodedJWT.getClaim("email").asString();
            String nickname = decodedJWT.getClaim("nickname").asString();
            String phoneNum = decodedJWT.getClaim("phoneNum").asString();
            String gender = decodedJWT.getClaim("gender").asString();
            Date birthdate = new java.sql.Date(decodedJWT.getClaim("birthdate").asDate().getTime());
            int profileId = decodedJWT.getClaim("profileId").asInt();

            User sessionUser = User.builder()
                    .id(userId)
                    .email(email)
                    .nickname(nickname)
                    .phoneNum(phoneNum)
                    .birthdate(birthdate)
                    .profileId(profileId)
                    .build();

            HttpSession session = req.getSession();
            session.setAttribute("sessionUser", sessionUser);
            System.out.println("세션유저입니당");
            System.out.println(sessionUser.getId());
            System.out.println("세션유저입니당");
            chain.doFilter(request, response);
        }catch (SignatureVerificationException | JWTDecodeException e1){
            onError(resp, "토큰 검증 실패");
        }catch (TokenExpiredException e2){
            onError(resp, "토큰 시간 만료");
        }
    }

    private void onError(HttpServletResponse response, String msg){
        MyUnAuthorizedException exception = new MyUnAuthorizedException(msg);

        try {
            String body = new ObjectMapper().writeValueAsString(exception.body());
            response.setStatus(exception.status().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter out = response.getWriter();
            out.println(body);
        }catch (Exception e){
            System.out.println("파싱 에러가 날 수 없음");
        }

    }
}
