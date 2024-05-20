package ru.mezentss.api.task5.pojos;

import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserData{
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
