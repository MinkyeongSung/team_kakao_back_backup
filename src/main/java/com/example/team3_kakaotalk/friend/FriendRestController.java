package com.example.team3_kakaotalk.friend;

import com.example.team3_kakaotalk._core.utils.ApiUtils;
import com.example.team3_kakaotalk._core.utils.Define;
import com.example.team3_kakaotalk.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendRestController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpSession session;

    // 친구 즐겨찾기 추가, 해제
//    @PutMapping("/favorite/{id}")
//    public ResponseEntity<?> favoriteFriend(@PathVariable Integer id, @RequestBody FriendRequest.FavoriteFriendDTO favoriteFriendDTO) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        System.out.println("================================");
//        System.out.println("session: " + sessionUser.getId());
//        System.out.println("Received userId: " + id);
//        System.out.println("Received isFavorite: " + favoriteFriendDTO.isFavorite);
//        System.out.println("Received favoriteFriendDTO: " + favoriteFriendDTO);
//        System.out.println("================================");
//
//        String response = friendService.favoriteFriend(sessionUser.getId(), favoriteFriendDTO);
//        return ResponseEntity.ok().body(ApiUtils.success(response));
//    }



}
