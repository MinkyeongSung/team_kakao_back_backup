package com.example.team3_kakaotalk.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class FriendRequest {

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FavoriteFriendDTO {
        int userId;
        boolean isFavorite;
    }
}
