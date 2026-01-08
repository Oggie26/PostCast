package com.example.auth_service.service;

import com.example.auth_service.enity.Account;
import com.example.auth_service.enums.EnumRole;
import com.example.auth_service.enums.EnumStatus;
import com.example.auth_service.enums.ErrorCode;
import com.example.auth_service.exception.AppException;
import com.example.auth_service.repository.AccountRepository;
import com.example.auth_service.request.AuthenticationRequest;
import com.example.auth_service.request.RegisterRequest;
import com.example.auth_service.response.AuthenticationResponse;
import com.example.auth_service.service.inteface.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.podcast.proto.user.UserServiceGrpc;
import com.podcast.proto.user.CreateProfileRequest;
import com.podcast.proto.user.CreateProfileResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

        private final UserServiceGrpc.UserServiceBlockingStub userGrpc;

        private final AccountRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        @Override
        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();

                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);

                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .refreshToken(refreshToken)
                                .build();
        }

        @Override
        @Transactional
        public AuthenticationResponse register(RegisterRequest request) {
                Account user = Account.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(EnumRole.USER)
                                .status(EnumStatus.ACTIVE)
                                .build();

                repository.save(user);

                try {
                        CreateProfileRequest profileRequest = CreateProfileRequest.newBuilder()
                                        .setAccountId(user.getId().toString())
                                        .setEmail(user.getEmail())
                                        .setFullName(request.getFullName())
                                        .build();

                        CreateProfileResponse response = userGrpc.createUserProfile(profileRequest);

                        if (!response.getSuccess()) {
                                throw new AppException(ErrorCode.USER_EXISTED);
                        }

                } catch (Exception e) {
                        repository.delete(user);
                        throw new RuntimeException("Lỗi hệ thống: Không thể tạo User Profile. Vui lòng thử lại sau.");
                }

                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);

                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .refreshToken(refreshToken)
                                .build();
        }
}
