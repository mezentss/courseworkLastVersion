package ru.mezentss.api.task5.pojos;

import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ResourceData {
     private int id;
     private String name;
     private int year;
     private String color;
     private String pantone_value;
}
