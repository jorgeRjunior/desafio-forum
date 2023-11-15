package com.tangerino.blog.controllers;


import com.fasterxml.jackson.databind.util.BeanUtil;
import com.tangerino.blog.dto.AuthDTO;
import com.tangerino.blog.dto.CreateUserDTO;
import com.tangerino.blog.dto.UserDTO;
import com.tangerino.blog.entity.User;
import com.tangerino.blog.service.UserService;
import com.tangerino.blog.utils.ConvertUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid AuthDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authentication.getPrincipal();

            Instant now = Instant.now();


            String scope =
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(joining(" "));

            JwtClaimsSet claims =
                    JwtClaimsSet.builder()
                            .issuer("forum.tangerino.com")
                            .issuedAt(now)
                            .expiresAt(now.plusSeconds(36000L))
                            .subject(format("%s,%s", user.getId(), user.getUsername()))
                            .claim("roles", scope)
                            .build();

            String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(ConvertUtils.convert(user,UserDTO.class));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public UserDTO register(@RequestBody @Valid CreateUserDTO request) {
        return userService.create(request);
    }
}
