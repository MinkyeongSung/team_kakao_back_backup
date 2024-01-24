package com.example.team3_kakaotalk.email;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailRequestDTO {
    private String email;
    private String phoneNum;

}
