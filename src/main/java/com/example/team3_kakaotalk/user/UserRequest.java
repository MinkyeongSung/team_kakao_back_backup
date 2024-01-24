package com.example.team3_kakaotalk.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class UserRequest {
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinDTO {
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
        private String nickname;
        @Size(min = 4, max = 20, message = "4에서 20자 이내여야 합니다.")
        private String password;
        private String phoneNum;
        private Date birthdate;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .nickname(nickname)
                    .password(password)
                    .phoneNum(phoneNum)
                    .birthdate(birthdate)
                    .build();

        }
    }

    @Data
    @ToString
    public static class LoginDTO {

         @NotEmpty
//@Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;
        private String phoneNum;
        @NotEmpty
        @Size(min = 4, max = 20, message = "4에서 20자 이내여야 합니다.")
        private String password;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateDTO {
//id, email만 필요!
        //가 아니고 서버의 회원이므로 세션에서 뺄 수 있다
   private String phoneNum;
    }

    // 나의 프로필 수정
    @Data
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyProfileUpdateRequestDTO{
        private Integer id;
        private String nickname;
        private String statusMessage;
        private String profileImage;
        private String backImage;
    }

    // 연락처로 친구 추가
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneNumFriendAddRequestDTO{
        private Integer id;
        private String phoneNum;
    }

    // 이메일로 친구 추가
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailFriendAddRequestDTO{
        private Integer id;
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요.")
        private String email;
    }

    // 친구 검색
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchFriendRequestDTO{
        private Integer id;
        private String keyword;
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class userTestDTO{
        int userId;
        String nickname;
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetChatUsersDTO{
        List<Integer> userIdList;
    }




}



