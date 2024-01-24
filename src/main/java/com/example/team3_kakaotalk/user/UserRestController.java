package com.example.team3_kakaotalk.user;

import java.util.List;

import com.example.team3_kakaotalk._core.utils.Define;
import com.example.team3_kakaotalk._core.utils.PhotoToStringUtil;
import com.example.team3_kakaotalk.friend.Friend;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.team3_kakaotalk._core.utils.ApiUtils;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;
	

    // 친구탭 메인 화면
    @GetMapping("/friend-tep-main/{id}")
    public ResponseEntity<?> friendTepMain(@PathVariable Integer id) {

        System.out.println("================");
        System.out.println("메인 통신 중 컨트롤러 호출");

        UserResponse.MainResponseDTO dto = this.userService.friendTepMain(id);
        return ResponseEntity.ok().body(ApiUtils.success(dto));
    }

	// 나의 프로필 수정
    @PostMapping("/my-profile-update")
    public ResponseEntity<?> myProfileUpdate(@RequestBody UserRequest.MyProfileUpdateRequestDTO myProfileUpdateRequestDto){
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("---------유저 정보 확인--------- : " + sessionUser);
        System.out.println("___________________컨트롤러 진입 확인 : " + myProfileUpdateRequestDto.getNickname());

        UserResponse.MyProfileUpdateResponseDTO myProfileUpdateResponseDto = this.userService.myProfileUpdate(myProfileUpdateRequestDto, sessionUser.getId());

        System.out.println("프론트로 보내기 : " + myProfileUpdateRequestDto.getNickname());
        System.out.println("프로필 이미지 보내기 : " + myProfileUpdateRequestDto.getProfileImage());
        System.out.println("프로필 이미지 보내기 : " + myProfileUpdateRequestDto.getBackImage());

        return ResponseEntity.ok().body(ApiUtils.success(myProfileUpdateResponseDto));
    }

    // 나의 프로필 상세보기
    @GetMapping("/my-profile-detail/{id}")
    public ResponseEntity<?> myProfileDetail(@PathVariable Integer id) {
        System.out.println("디테일 컨트롤러 진입 : " + id);
        UserResponse.MyProfileDetailResponseDTO myProfileDetail = this.userService.myProfileDetail(id);
        System.out.println("디테일 페이지 응답" + myProfileDetail);
        return ResponseEntity.ok().body(ApiUtils.success(myProfileDetail));
    }

    // 친구 프로필 상세보기
    @GetMapping("/friend-profile-detail/{id}")
    public ResponseEntity<?> friendProfileDetail(@PathVariable Integer id) {
        UserResponse.FriendProfileDetailResponseDTO friendProfileDetailResponseDto = this.userService.friendProfileDetail(id);
        return ResponseEntity.ok().body(ApiUtils.success(friendProfileDetailResponseDto));
    }

    // 연락처로 친구 추가
    @PostMapping("/phoneNum-friend-add")
    public ResponseEntity<?> phoneNumFriendAdd(@RequestBody UserRequest.PhoneNumFriendAddRequestDTO phoneNumFriendAddRequestDto) {
        this.userService.phoneNumFriendAdd(phoneNumFriendAddRequestDto);
        UserResponse.MainResponseDTO mainResponseDTO = this.userService.friendTepMain(phoneNumFriendAddRequestDto.getId());
        return ResponseEntity.ok().body(ApiUtils.success(mainResponseDTO));
    }

    // 이메일로 친구 추가
    @PostMapping("/email-friend-add")
    public ResponseEntity<?> emailFriendAdd(@RequestBody UserRequest.EmailFriendAddRequestDTO emailFriendAddRequestDto) {
        this.userService.emailFriendAdd(emailFriendAddRequestDto);
        UserResponse.MainResponseDTO mainResponseDTO = this.userService.friendTepMain(emailFriendAddRequestDto.getId());
        return ResponseEntity.ok().body(ApiUtils.success(mainResponseDTO));
    }

    // 나의 프로필 삭제(프로필 이미지)
    @GetMapping("/my-profileImage-delete/{id}")
    public ResponseEntity<?> myProfileImageDelete(@PathVariable Integer id) {
        this.userService.myProfileImageDelete(id);
        return ResponseEntity.ok().body(ApiUtils.success("프로필 이미지 삭제 성공"));
    }

    // 나의 프로필 삭제(배경 이미지)
    @GetMapping("/my-profileBackImage-delete/{id}")
    public ResponseEntity<?> myProfileBackImageDelete(@PathVariable Integer id) {
        this.userService.myProfileBackImageDelete(id);
        return ResponseEntity.ok().body(ApiUtils.success("배경 이미지 삭제 성공"));
    }

	// 친구 차단
	@GetMapping("/friend-delete/{id}")
	public ResponseEntity<?> friendDelete(@PathVariable Integer id) {
        this.userService.friendDelete(id);
        return ResponseEntity.ok().body(ApiUtils.success("친구 차단 성공"));
    }

	// 친구 검색
	@GetMapping("/search-friend")
	public ResponseEntity<?> searchFriend(@RequestParam String keyword){
        System.out.println("검색 컨트롤러 진입 " + keyword);
		User sessionUser = (User) session.getAttribute("sessionUser");
		List<UserResponse.SearchFriendResponseDTO> searchFriendResponseDto = this.userService.searchFriend(keyword, sessionUser.getId());
        System.out.println("나 이제 나간다.");
		return ResponseEntity.ok().body(ApiUtils.success(searchFriendResponseDto));
	}

    @PostMapping("/get-chat-users")
    public ResponseEntity<?> getChatUsers(@RequestBody UserRequest.GetChatUsersDTO dto){
        System.out.println("=======================");
        System.out.println("컨트롤러 동작중");
        List<UserResponse.GetChatUsersDTO> respDTO = userService.getChatUsers(dto);
        return ResponseEntity.ok().body(ApiUtils.success(respDTO));
    }


    @GetMapping("/search-user/{condition}")
    public ResponseEntity<?> searchUser(@PathVariable String condition){
        UserResponse.FriendProfileDetailResponseDTO dto = userService.searchUserByCondition(condition);
        return ResponseEntity.ok().body(ApiUtils.success(dto));
    }

}
