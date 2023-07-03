package com.blog.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTockenHelper tokenHelper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 1. Getting the token
        final String requestToken = request.getHeader("Authorization");
        // Bearer 1234trf345ty
        System.out.println(requestToken);
        String userName = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")){
            token = requestToken.substring(7);
            try {
                userName = this.tokenHelper.getUserNameFromToken(token);
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                System.out.println("Unable To get Token");
            } catch (ExpiredJwtException e){
                e.printStackTrace();
                System.out.println("Token is Expired");
            } catch (MalformedJwtException e){
                e.printStackTrace();
                System.out.println("Invalid JWT Token");
            }
        } else {
            System.out.println("Token does not began with Bearer ");
        }

        // once we get the token, now we are going to validate token
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (this.tokenHelper.validateToken(token,userDetails)){

                // sahi chal raha hain
                // Authentication karna hain

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Invalid JWT Token");
            }
        } else {
            System.out.println("Username is null and context is not null");
        }
        filterChain.doFilter(request,response);
    }
}
