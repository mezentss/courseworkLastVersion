package ru.mezentss.api.task5.pojos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class UserRequest {
    private String name;
    private String job;
}
