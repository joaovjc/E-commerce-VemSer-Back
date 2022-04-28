package com.dbc.vemserback.ecommerce.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.dto.user.UserLoginDto;
import com.dbc.vemserback.ecommerce.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String KEY_RULES = "RULES";

    @Value("${jwt.expiration}")
    private String expiration;
    @Value("${jwt.secret}")
    private String secret;

    public UserLoginDto getToken(Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        
        Date now = new Date();
        Date exp = new Date(now.getTime()+Long.parseLong(expiration));

        List<String> rules = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setIssuer("ecommerce")
                .setSubject(user.getUserId().toString())
                .claim(KEY_RULES, rules)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
       
        return UserLoginDto.builder()
        		.profile(user.getGroupEntity().getName()).username(user.getUsername())
        		.fullName(user.getFullName()).token(PREFIX + token)
        		.profileImage(user.getProfileImage()!=null?new String(user.getProfileImage()):null)
        		.build();
    }
    
    public UserLoginDto getToken(Authentication authentication, UserLoginDto dto){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        
        Date now = new Date();
        Date exp = new Date(now.getTime()+Long.parseLong(expiration));

        List<String> rules = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setIssuer("ecommerce")
                .setSubject(user.getUserId().toString())
                .claim(KEY_RULES, rules)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
       
        dto.setProfile(user.getGroupEntity().getName());
        dto.setToken(PREFIX + token);
        return dto;
    }
    

    public Authentication getAuthentication(HttpServletRequest request){
        String tokenBearer = request.getHeader(HEADER_AUTHORIZATION);
        if(tokenBearer!=null){
            String token = tokenBearer.replaceFirst(PREFIX, "");
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String user = body.getSubject();
            
            if(user!=null){
                List<String> regras = new ArrayList<>();
                for(Object o: body.get(KEY_RULES, List.class))regras.add(o.toString());
                List<SimpleGrantedAuthority> roles = regras.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(user, null, roles);
            }
        }
        return null;
    }
}

