/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.filter;

import ctu.demo.utils.JwtUtil;
import ctu.demo.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author ADMIN
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtTokenUtil.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token: {}", e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT Token has expired");
                return; // Stop further processing if the token is expired
            } catch (MalformedJwtException e) {
                logger.warn("Invalid JWT Token: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid JWT Token");
                return; // Stop further processing if the token is malformed
            } catch (Exception e) {
                logger.error("JWT Token processing error: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Internal server error while processing the token");
                return; // Stop further processing if any other error occurs
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

}
