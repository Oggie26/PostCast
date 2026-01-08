package com.example.auth_service.service.inteface;

import com.example.auth_service.request.AuthenticationRequest;
import com.example.auth_service.request.RegisterRequest;
import com.example.auth_service.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);
}
