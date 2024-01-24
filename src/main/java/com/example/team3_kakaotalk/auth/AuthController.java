package com.example.team3_kakaotalk.auth;

import com.example.team3_kakaotalk._core.utils.ApiUtils;
import com.example.team3_kakaotalk._core.utils.Define;
import com.example.team3_kakaotalk.user.User;
import com.example.team3_kakaotalk.user.UserRequest;
import com.example.team3_kakaotalk.user.UserResponse;
import com.example.team3_kakaotalk.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO reqDTO, Errors errors) {
//            System.out.println("로그인 통신줃");
//        UserResponse.loginDTO responseDTO = userService.login(reqDTO);
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
//    }

    //회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO joinDTO) {
        System.out.println("+++++++컨트롤러 진입 : " +joinDTO.getEmail() );

        userService.join(joinDTO);
        System.out.println("*********유저서비스로 가자******** : " +joinDTO.getEmail() );
        return ResponseEntity.ok().body(ApiUtils.success("회원가입 완료"));
    }

    //로그인
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO loginDTO) {
        System.out.println("=======================");
        System.out.println("컨트롤러 호출" + loginDTO.getEmail());
        UserResponse.loginDTO responseDTO = userService.login(loginDTO);
        System.out.println("로그인 페이지 응답 : " + responseDTO);

        return ResponseEntity.ok().header("Authorization", "Bearer " + responseDTO.getJwt()).body(ApiUtils.success(responseDTO));
    }

    @GetMapping("/user/jwtTest")
    public ResponseEntity<?> jwtTest(@RequestBody UserRequest.LoginDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        return ResponseEntity.ok().body(ApiUtils.success(sessionUser));
    }


    //업데이트
    @PostMapping("/user/update")
    public ResponseEntity<?> update(@RequestBody @Valid  UserRequest.UpdateDTO requestDTO, Errors errors) {
        System.out.println("++유저 업뎃 컨트롤러 진입 +++");
        User sessionUser = (User) session.getAttribute("sessionUser");
        //JWT가 session으로 변해서 그걸 꺼낼 수 있다

        UserResponse.UpdateResponseDTO responseDTO = userService.update(requestDTO, sessionUser);

        System.out.println("응답 디티오!!!!!"+responseDTO.getNewPhoneNum());
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @PostMapping("/autologin")
    public ResponseEntity<?> autologin () {
            User sessionUser = (User) session.getAttribute(Define.PRINCIPAL);
            UserResponse.loginDTO dto = userService.autoLogin(sessionUser);
            return ResponseEntity.ok().header("Authorization", "Bearer " + dto.getJwt()).body(ApiUtils.success(dto));
    }

    @GetMapping("/userIdTest/{id}")
    public ResponseEntity<?> userIdTest(@PathVariable int id){
        System.out.println("=============");
        System.out.println("테스트 컨트롤러");
        return ResponseEntity.ok().body(ApiUtils.success(id));
    }

    @PostMapping("/userTest")
    public ResponseEntity<?> userTest(@RequestBody UserRequest.userTestDTO reqDTO){
        UserResponse.UserTestDTO dto = userService.userTest(reqDTO.getUserId());
        return ResponseEntity.ok().body(ApiUtils.success(dto));

    }

    @GetMapping("/ListTest")
    public ResponseEntity<?> ListTest(){
        List<UserResponse.UserTestDTO> dtoList = userService.ListTest();
        return ResponseEntity.ok().body(ApiUtils.success(dtoList));
    }


    }


