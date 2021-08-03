package com.example.RedditBackend.controller;


import com.example.RedditBackend.dto.RegisterRequest;
import com.example.RedditBackend.exception.SpringRedditException;
import com.example.RedditBackend.model.VerificationToken;
import com.example.RedditBackend.repository.VerificationTokenRepository;
import com.example.RedditBackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private AuthService authService;
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
    authService.signup(registerRequest);
    return new ResponseEntity<>("User Registration Successful",
            OK);
  }

  @GetMapping("/accountVerification/{token}")
  public ResponseEntity<String> verifyAccount(@PathVariable String token){
     authService.verifyAccount(token);
    return new ResponseEntity<>("Account Activated Successfully", OK);

  }


}
