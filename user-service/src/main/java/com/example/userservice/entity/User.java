package com.example.userservice.entity;

import com.example.userservice.enums.EnumStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2 đến 100 ký tự")
    @Column(nullable = false)
    private String fullName;

    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    @Column
    private LocalDate birthDate;

    @Column(unique = true)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @NotNull(message = "Trạng thái là bắt buộc")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumStatus status;

    @Column(name = "total_followers")
    private Integer totalFollowers = 0;

    @Column(name = "total_following")
    private Integer totalFollowing = 0;

    @Column
    private String avatar;

    @Min(value = 0, message = "Điểm tích lũy không được âm")
    @Column(nullable = false)
    private Integer points = 0;

    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
    @Column(length = 15)
    private String phone;

    @NotNull(message = "AccountId không được để trống")
    @Column(unique = true, nullable = false)
    private UUID accountId;
}