package com.example.team3_kakaotalk.user;

import com.example.team3_kakaotalk.profile.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.regex.Pattern;

@Data
@Entity
@Table(name = "user_tb")
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "email", unique = true)
    private String email;
    private String nickname;
    @Column(name = "phone_num", unique = true)
    private String phoneNum;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(name = "profile_id", unique = true)
    private Integer profileId;

    @Builder
    public User(Integer id, String email, String nickname, String phoneNum, String password,  Date birthdate, Timestamp createdAt, Integer profileId) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.password = password;
        this.birthdate = birthdate;
        this.createdAt = createdAt;
        this.profileId = profileId;
    }
}
