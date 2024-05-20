package ru.mezentss.api.task5.pojos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class LoginRegisterRequest {
    private String email;
    private String password;

}
