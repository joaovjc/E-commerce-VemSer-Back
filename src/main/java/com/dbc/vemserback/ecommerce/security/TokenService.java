package com.dbc.vemserback.ecommerce.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String KEY_GROUPS = "GROUPS";

    @Value("${jwt.expiration}")
    private String expiration;
    @Value("${jwt.secret}")
    private String secret;

    public String getToken(Authentication authentication) throws BusinessRuleException{
//        UserEntity usuario = (UserEntity) authentication.getPrincipal();
        

        Date now = new Date();//data atual
        Date exp = new Date(now.getTime()+Long.parseLong(expiration));

//        List<String> regras = usuario.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setIssuer("pessoa-api")
//                .setSubject(usuario.getId().toString())
//                .claim(KEY_GROUPS, regras)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return PREFIX + token;
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
                for(Object o: body.get(KEY_GROUPS, List.class))regras.add(o.toString());
                List<SimpleGrantedAuthority> roles = regras.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(user, null, roles);
            }
        }
        return null;
    }
}

