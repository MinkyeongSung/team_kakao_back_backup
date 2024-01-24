package com.example.team3_kakaotalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class Team3KakaoTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team3KakaoTalkApplication.class, args);
    }

}
