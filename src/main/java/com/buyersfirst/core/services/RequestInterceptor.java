package com.buyersfirst.core.services;

import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.buyersfirst.core.models.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor{
    @Autowired
    JWTBuilder jwtBuilder;
    @Autowired
    UsersRepository usersRepository;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{
        String token = request.getHeader("Authorization");

        if (token == null) {
            response.getWriter().write("Authorization needed");
            response.setStatus(401);
            return false;
        }

        JwtClaims claims = jwtBuilder.generateParseToken(token);
        Integer userId = Integer.parseInt(claims.getClaimValue("userId").toString());
        if (usersRepository.findById(userId).isPresent()) {
            request.setAttribute("userId", userId);
            return true;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model){
        // Do something here after the request is processed by the controller
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception){
        // Do something here if an error occured and want to change what the user sees
    }
}
