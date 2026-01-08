package com.example.auth_service.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.podcast.proto.user.UserServiceGrpc;

@Configuration
public class GrpcClientConfig {

    @org.springframework.beans.factory.annotation.Value("${services.user-service.host}")
    private String userServiceHost;

    @org.springframework.beans.factory.annotation.Value("${services.user-service.port}")
    private int userServicePort;

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(userServiceHost, userServicePort)
                .usePlaintext()
                .build();

        return UserServiceGrpc.newBlockingStub(channel);
    }
}
