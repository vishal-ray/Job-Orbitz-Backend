package com.alpha.TaskOrbit.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private Long userId;
    private String jwtToken;
    private String username;
}