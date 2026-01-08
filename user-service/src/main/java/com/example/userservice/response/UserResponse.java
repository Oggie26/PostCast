package com.example.userservice.response;

import com.example.userservice.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String fullName;
    private LocalDate birthDate;
    private String nickname;
    private String bio;
    private EnumStatus status;
    private Integer totalFollowers;
    private Integer totalFollowing;
    private String avatar;
    private Integer points;
    private String phone;
    private UUID accountId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
