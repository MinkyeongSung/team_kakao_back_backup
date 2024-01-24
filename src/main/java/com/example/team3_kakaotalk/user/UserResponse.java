package com.example.team3_kakaotalk.user;
import com.example.team3_kakaotalk.profile.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

public class UserResponse {
    @Data
    @ToString
    public static class loginDTO {
        private int id;
        private String nickname;
        private String phoneNum;
        private String email;
        private String profileImage;
        private String backImage;
        private String statusMessage;
        private String jwt;

        public loginDTO(User user, Profile profile) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.phoneNum = user.getPhoneNum();
            this.email = user.getEmail();
            this.backImage = profile.getBackImage();
            this.profileImage = profile.getProfileImage();
            this.statusMessage = profile.getStatusMessage();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateResponseDTO {
        //phoneNum만 필요!
        private String newPhoneNum;

        public UpdateResponseDTO(User user) {
            this.newPhoneNum = user.getPhoneNum();

        }
    }


    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MainResponseDTO{
        private int userId;
        private Profile userProfile;
        private List<FriendTepMainResponseDTO> friendList;
        private List<FriendTepMainResponseDTO> birthdayFriendList;
        private int birthdayCount;
    }

    // 친구 탭 메인
    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FriendTepMainResponseDTO {
        private Integer userId;
        private String nickname;
        private String birthdate;
        private String profileImage;
        private String backImage;
        private String statusMessage;
        private String phoneNum;
        private String isBirthday;
        private boolean isFavorite;
    }

    // 나의 프로필 상세보기
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyProfileDetailResponseDTO {
        private int id;
        private String nickname;
        private String profileImage;
        private String backImage;
        private String statusMessage;
    }

    // 친구 프로필 상세보기
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendProfileDetailResponseDTO {
        private int id;
        private String nickname;
        private String profileImage;
        private String backImage;
        private String statusMessage;
        private boolean isSuccess;

        public FriendProfileDetailResponseDTO(User user, Profile profile) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.profileImage = profile.getProfileImage();
            this.backImage = profile.getBackImage();
            this.statusMessage = profile.getStatusMessage();
            this.isSuccess = true;
        }
    }

    // 나의 프로필 삭제(프로필 이미지)
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyProfileImageDeleteResponseDTO {
        private Integer id;
        private String profileImage;
    }

    // 나의 프로필 삭제(배경 이미지)
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyProfileBackImageDeleteResponseDTO {
        private Integer id;
        private String backImage;
    }

    // 연락처로 친구 추가
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneNumFriendAddResponseDTO{
        private Integer userId1;
        private Integer userId2;
        private String phoneNum;
    }

    // 이메일로 친구 추가
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailFriendAddResponseDTO{
        private Integer userId1;
        private Integer userId2;
        private String email;
    }

    // 나의 프로필 수정
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyProfileUpdateResponseDTO{
        private Integer id;
        private String nickname;
        private String statusMessage;
        private String profileImage;
        private String backImage;
    }



    // 친구 검색
    @Data
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchFriendResponseDTO{
        private Integer id;
        private String nickname;
        private String phoneNum;
        private String statusMessage;
        private String profileImage;
        private String backImage;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date birthdate;
    }

    // 친구 차단
    @Data
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendDeleteResponseDTO{
        private Integer id;
        private Boolean isBlocked;
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserTestDTO{
        int userId;
        String userNickName;
        String userPhoneNum;

        public UserTestDTO(User user) {
            this.userId = user.getId();
            this.userNickName = user.getNickname();
            this.userPhoneNum = user.getPhoneNum();
        }
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetChatUsersDTO{
        int userId;
        int profileId;
        String userNickname;
        String userProfileImage;


        public GetChatUsersDTO(User user, Profile profile) {
            this.userId = user.getId();
            this.profileId = user.getProfileId();
            this.userNickname = user.getNickname();
            this.userProfileImage = profile.getProfileImage();
        }
    }

}




