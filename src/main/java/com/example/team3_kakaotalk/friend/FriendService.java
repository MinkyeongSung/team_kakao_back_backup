package com.example.team3_kakaotalk.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class FriendService {

    @Autowired
    FriendJPARepository friendJPARepository;

    // 친구 즐겨찾기 등록,해제
//    @Transactional
//    public String favoriteFriend(Integer id, FriendRequest.FavoriteFriendDTO favoriteFriendDTO) {
//        System.out.println("갱신할 즐찾 상태값");
//        System.out.println(favoriteFriendDTO.isFavorite);
//        System.out.println("즐찾을 당해버릴 녀석");
//        System.out.println(favoriteFriendDTO.userId);
//        System.out.println("세션 아이디(로그인한 유저, 친추를 해버릴 녀석)");
//        System.out.println(id);
//
////        Friend friend = friendJPARepository.findById(favoriteFriendDTO.getUserId()).orElseThrow(() -> new RuntimeException("친구아님!"));
//
//
//        System.out.println("!!!!!!한다? 진짜 한다?!!!!!");
//        // 즐찾 상태값을 불러와주고?
//        boolean newStatus = favoriteFriendDTO.isFavorite();
//        System.out.println("====상태값 바뀐거 맞니?=====");
//        System.out.println(newStatus);
//        // 할, 당할, 상태값 넣기
//        int friend = friendJPARepository.updateByUserId(id,favoriteFriendDTO.getUserId(),newStatus);
//        System.out.println("====체크체크=====");
//        System.out.println(friend);
//
//
//        if (newStatus) {
//            return "등록되었습니다";
//        } else {
//            return "해제되었습니다.";
//        }
//    }
}