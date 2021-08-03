package com.example.RedditBackend.security;


import com.example.RedditBackend.exception.SpringRedditException;
import com.example.RedditBackend.model.User;
import io.jsonwebtoken.Jwts;
import lombok.Value;

import org.springframework.stereotype.Service;
import java.security.cert.CertificateException;
import java.io.IOException;
import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.security.*;
import java.sql.Date;
import java.time.Instant;
import static java.util.Date.from;
import org.springframework.security.core.Authentication;

@Service
public class JwtProvider {


    private KeyStore keyStore;



    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringRedditException("Exception occurred while loading keystore", e);
        }

    }


    public String generateToken(Authentication authentication){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .compact();
    }
    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception occured while retrieving public key from keystore", e);
        }
    }

}
