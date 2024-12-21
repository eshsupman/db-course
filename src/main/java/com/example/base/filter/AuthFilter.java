package com.example.base.filter;

import com.example.base.config.Constanst;
import com.example.base.controllers.UsersController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;
import com.example.base.controllers.UsersController.*;

import javax.crypto.SecretKey;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String auth = httpRequest.getHeader("Authorization");
        if(auth!=null){
            String[] tokens = auth.split("Bearer ");
            if(tokens.length>1 && tokens[1] != null){
                String token = tokens[1];
                System.out.println(token);
                try{
                    Claims claims = Jwts.parser()
                            .verifyWith( Constanst.key)
                            .build().parseSignedClaims(token).getPayload();
                    httpRequest.setAttribute("user", claims.get("username").toString());
                }catch (Exception e){
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
                    return;
                }
            }else{
                httpResponse.sendError(HttpStatus.BAD_REQUEST.value(), "Auth token must be bearer token");
                return;
            }
        }else{
            httpResponse.sendError(HttpStatus.BAD_GATEWAY.value(), "Auth token is null");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
