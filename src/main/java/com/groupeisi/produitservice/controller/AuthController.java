package com.groupeisi.produitservice.controller;
import com.groupeisi.produitservice.dto.AuthDTO;
import com.groupeisi.produitservice.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor @Tag(name = "Authentification")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register") public ResponseEntity<AuthDTO.LoginResponse> register(@RequestBody AuthDTO.RegisterRequest req) { return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(req)); }
    @PostMapping("/login") public ResponseEntity<AuthDTO.LoginResponse> login(@RequestBody AuthDTO.LoginRequest req) { return ResponseEntity.ok(authService.login(req)); }
}
