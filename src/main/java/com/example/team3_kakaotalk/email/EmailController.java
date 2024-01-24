package com.example.team3_kakaotalk.email;

import com.example.team3_kakaotalk._core.utils.ApiUtils;
import com.example.team3_kakaotalk.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

//    @GetMapping("login/mailConfirm")
//    public String mailConfirm(String email) throws Exception{
//        String code = mailService.sendSimpleMessage(email);
//        System.out.println("인증 코드" + code);
//        return code;
//    }


    //유저에게 인증코드 담긴 이메일 발송
    @PostMapping("/mail-send")
    public ResponseEntity<?> mailSend(@RequestBody EmailRequestDTO emailDto) {
        int emailCheck = emailService.emailCheck(emailDto.getEmail());
        System.out.println("체크 결과 : " + emailCheck);
        if (emailCheck == 2) {
            emailService.joinEmail(emailDto.getEmail());
            return ResponseEntity.ok().body(ApiUtils.success("이메일을 보냈습니다"));
        } else {
            // 실패할 경우 예외를 처리하고 클라이언트에게 실패 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용중인 이메일입니다.");
        }
        //return ResponseEntity.ok(ApiUtils.success(emailCheck));

    }

    @PostMapping("/mail-check")
    public ResponseEntity<?> mailCheck(@RequestBody EmailCheckDTO emailDto) {
        try {
            Boolean result = emailService.checkEmail(emailDto.getVerifyNumber());

            return ResponseEntity.ok(ApiUtils.success("이메일 인증되었습니다"));

        } catch (Exception e) {
            // 실패할 경우 예외를 처리하고 클라이언트에게 실패 응답을 반환합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 인증되지 않았습니다");
        }
    }

    @PostMapping("/password-find")
    public ResponseEntity<?> passwordFind(@RequestBody EmailRequestDTO emailRequestDTO) {
        System.out.println("패스워드 확인 한다?" + emailRequestDTO); //-->로고
        emailService.passwordEmail(emailRequestDTO);
        System.out.println("++++패");
        Optional<User> user = emailService.passwordUpdate(emailRequestDTO);
        return ResponseEntity.ok(ApiUtils.success("비밀번호가 발급되었습니다"));
    }

}

