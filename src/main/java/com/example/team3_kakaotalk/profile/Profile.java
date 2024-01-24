package com.example.team3_kakaotalk.profile;

import com.example.team3_kakaotalk.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Entity
@Table(name = "profile_tb")
@ToString
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String profileImage;
    private String statusMessage;
    private String backImage;
    private String qrCode;
    @Column(name = "user_id", unique = true)
    private Integer userId;




    @Builder
    public Profile(Integer id, String profileImage, String statusMessage, String backImage, String qrCode, Integer userId) {
        this.id = id;
        this.profileImage = profileImage;
        this.statusMessage = statusMessage;
        this.backImage = backImage;
        this.qrCode = qrCode;
        this.userId = userId;
    }
}
