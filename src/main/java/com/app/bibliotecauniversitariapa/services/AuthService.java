package com.app.bibliotecauniversitariapa.services;

import com.app.bibliotecauniversitariapa.dtos.auth.AuthenticationRequest;
import com.app.bibliotecauniversitariapa.dtos.auth.AuthenticationResponse;
import com.app.bibliotecauniversitariapa.dtos.auth.RegisterRequest;
import com.app.bibliotecauniversitariapa.dtos.auth.UserResponse;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse refreshToken(String refreshToken);

    UserResponse getAuthenticatedUser();
}
