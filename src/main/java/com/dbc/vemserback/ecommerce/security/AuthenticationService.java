package com.dbc.vemserback.ecommerce.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //Optional<UserEntity> optionalUsuario = //TODO
//        if(optionalUsuario.isPresent()){
//            return optionalUsuario.get();
//        }
        throw new UsernameNotFoundException("User not found!");
    }
}
