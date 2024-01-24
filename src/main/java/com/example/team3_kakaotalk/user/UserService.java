package com.example.team3_kakaotalk.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.team3_kakaotalk._core.utils.PhotoToStringUtil;
import com.example.team3_kakaotalk._core.handler.exception.MyNotFoundException;
import com.example.team3_kakaotalk.friend.Friend;
import com.example.team3_kakaotalk.profile.Profile;
import com.example.team3_kakaotalk.profile.ProfileJPARepository;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.team3_kakaotalk._core.handler.exception.MyBadRequestException;
import com.example.team3_kakaotalk._core.handler.exception.MyServerErrorException;
import com.example.team3_kakaotalk._core.handler.exception.MyUnAuthorizedException;
import com.example.team3_kakaotalk._core.utils.JwtTokenUtils;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private UserMBRepository userMBRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileJPARepository profileJPARepository;

    @Autowired
    private HttpSession httpSession;

    @Transactional
    public void join(UserRequest.JoinDTO joinDTO) {
        System.out.println("+++++++++join서비스 시작+++++++ : " + joinDTO.getEmail());
        try {
            //비밀번호 해시화
            String encodedPassword = passwordEncoder.encode(joinDTO.getPassword());
            //해시화된 비밀번호 설정
            joinDTO.setPassword(encodedPassword);
            System.out.println("++++++ 해시화된 비번 ++++++ : " + encodedPassword);

            User user = joinDTO.toEntity();
            System.out.println("회원가입 오지?");
            int profileId = userJPARepository.save(user).getId();

            Profile profile = Profile.builder().userId(profileId).id(profileId).profileImage("이미지"+profileId).backImage(null).statusMessage(null).build();
            profileJPARepository.save(profile);
        } catch (Exception e) {
            throw new MyServerErrorException("서버 에러");
        }
    }


    public UserResponse.loginDTO login(UserRequest.LoginDTO loginDTO) {
        System.out.println("로그인 서비스 진입");
        //이메일, 비번으로 조회
        Optional<User> userOptional = userJPARepository.findByEmail(loginDTO.getEmail());
        User user = userOptional.get();
        System.out.println("로그인 이메일 조회 : " + user.getNickname());
        Profile profile = profileJPARepository.findByUserId(user.getId());


        // 사용자 정보가 존재하고, 입력된 비밀번호와 저장된 해시된 비밀번호가 일치하는지 확인
        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            // 비밀번호가 일치하면 JWT 생성 및 응답 DTO 생성
            String jwt = JwtTokenUtils.create(user);
            UserResponse.loginDTO responseDTO = new UserResponse.loginDTO(user,profile);

            responseDTO.setJwt(jwt);
            responseDTO.setStatusMessage(profile.getStatusMessage());
            System.out.println("나가기 전 " + responseDTO.getNickname());
            return responseDTO;
        } else {
            // 사용자 정보가 없거나 비밀번호가 일치하지 않으면 예외 발생
            throw new MyBadRequestException("유저 없음");
        }
    }

    public void resetPassword(String email) {
        Optional<User> userOptional = userJPARepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new MyBadRequestException("해당 이메일을 찾을 수 없습니다."));

    }

    // 친구탭 메인 화면
    public UserResponse.MainResponseDTO friendTepMain(Integer id) {

        Profile profile = profileJPARepository.findByUserId(id);

        UserResponse.MainResponseDTO mainDTO = new UserResponse.MainResponseDTO();
        mainDTO.setUserId(id);
        mainDTO.setUserProfile(profile);

        List<UserResponse.FriendTepMainResponseDTO> friendLists = this.userMBRepository.findByFriendTepMain(id);
        mainDTO.setFriendList(friendLists);

        List<UserResponse.FriendTepMainResponseDTO> birthdayFriendLists = friendLists.stream()
                .filter(e -> e.getIsBirthday().equals("오늘 생일 친구"))
                .collect(Collectors.toList());

        mainDTO.setBirthdayFriendList(birthdayFriendLists);
        mainDTO.setBirthdayCount(birthdayFriendLists.size());

        return mainDTO;
    }

    @Transactional
    public UserResponse.UpdateResponseDTO update(UserRequest.UpdateDTO updateDTO, User sessionUser) {
        userJPARepository.updatePhoneNumById(sessionUser.getEmail(), sessionUser.getPhoneNum());

        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new MyBadRequestException("오류 : " + sessionUser.getEmail()));

//        if (!(updateDTO.getEmail().equals(sessionUser.getEmail()))) {
//            throw new MyUnAuthorizedException("로그인 유저랑 변경하려는 유저가 다름 : " + updateDTO.getEmail());
//        }
        if (updateDTO.getPhoneNum() == null || (updateDTO.getPhoneNum().isEmpty())) {
            throw new MyUnAuthorizedException("로그인 유저랑 변경하려는 유저가 다름 ");
        }

        // 변경된 내용 업데이트
        user.setPhoneNum(updateDTO.getPhoneNum());

        UserResponse.UpdateResponseDTO responseDTO = new UserResponse.UpdateResponseDTO(user);
        return responseDTO;
    }

    // 나의 프로필 상세보기
    public UserResponse.MyProfileDetailResponseDTO myProfileDetail(Integer id) {
        UserResponse.MyProfileDetailResponseDTO myProfileDetailResponseDto = this.userMBRepository.findByMyProfileDetail(id);
        return myProfileDetailResponseDto;
    }

    // 친구 프로필 상세보기
    public UserResponse.FriendProfileDetailResponseDTO friendProfileDetail(Integer id) {
        UserResponse.FriendProfileDetailResponseDTO friendProfileDetailResponseDto = this.userMBRepository.findByFriendProfileDetail(id);
        return friendProfileDetailResponseDto;
    }

    // 나의 프로필 수정
    public UserResponse.MyProfileUpdateResponseDTO myProfileUpdate(UserRequest.MyProfileUpdateRequestDTO myProfileUpdateRequestDto, Integer sessionId){
        //System.out.println("서비스 진입 확인 : " + sessionUserId);
        System.out.println("서비스 진입 확인 : " + myProfileUpdateRequestDto.getNickname());

        String profileDecodeImage = PhotoToStringUtil.picToString(myProfileUpdateRequestDto.getProfileImage(), myProfileUpdateRequestDto.getNickname());
        String backDecodeImage = PhotoToStringUtil.picToString(myProfileUpdateRequestDto.getBackImage(), myProfileUpdateRequestDto.getNickname());
        myProfileUpdateRequestDto.setProfileImage(profileDecodeImage);
        myProfileUpdateRequestDto.setBackImage(backDecodeImage);

        System.out.println("이미지 dto에 잘 담김? " + myProfileUpdateRequestDto.getProfileImage());

        UserResponse.MyProfileUpdateResponseDTO responseDTO =  new UserResponse.MyProfileUpdateResponseDTO();
        responseDTO.setProfileImage("images/"+ profileDecodeImage);
        responseDTO.setBackImage("images/"+ backDecodeImage);

        myProfileUpdateRequestDto.setId(sessionId);

        System.out.println("리퀘스트 값 바뀜? " + myProfileUpdateRequestDto.getId());

        // 닉네임
        this.userMBRepository.myProfileNicknameUpdate(myProfileUpdateRequestDto);
        System.out.println("1번이 문제다");
        // 상태 메세제, 프로필 이미지, 배경 이미지
        this.userMBRepository.myProfileSmessageAndPimageAndBimageUpdate(myProfileUpdateRequestDto);
        System.out.println("2번이 문제다");

//         userJPARepository.findById(myProfileUpdateRequestDto.getId());

        // DTO 안 Id 를 기준으로 조인 쿼리로 조회
       UserResponse.MyProfileUpdateResponseDTO myProfileUpdateResponseDto = this.userMBRepository.findByMyProfile(myProfileUpdateRequestDto.getId());
        System.out.println("3번이 문제다");
        System.out.println("-------------------------------------------------------");
        System.out.println("서비스에서 내보내기 : " + myProfileUpdateResponseDto.getNickname());
        System.out.println("-------------------------------------------------------");
        return myProfileUpdateResponseDto;
    }

    // 연락처로 친구 추가
    public void phoneNumFriendAdd(UserRequest.PhoneNumFriendAddRequestDTO phoneNumFriendAddRequestDto) {
        String userPhoneNum = this.userMBRepository.findByPhoneNum(phoneNumFriendAddRequestDto.getPhoneNum());
        UserResponse.PhoneNumFriendAddResponseDTO PhoneNumFriendAddResponseDTO = this.userMBRepository.findByPhoneNumFriendAdd(phoneNumFriendAddRequestDto);
        if (phoneNumFriendAddRequestDto.getPhoneNum() == null || phoneNumFriendAddRequestDto.getPhoneNum().isEmpty()) {
            throw new MyBadRequestException("전화번호를 입력해 주세요.");
        }
        if (userPhoneNum == null) {
            throw new MyBadRequestException("등록되지 않은 사용자입니다.");
        }
        if (PhoneNumFriendAddResponseDTO != null) {
            throw new MyBadRequestException("이미 친구등록된 전화번호입니다.");
        }
        this.userMBRepository.phoneNumFriendAdd(phoneNumFriendAddRequestDto);
    }

    // 이메일로 친구 추가
    public void emailFriendAdd(UserRequest.EmailFriendAddRequestDTO emailFriendAddRequestDto) {
        String userEmail = this.userMBRepository.findByEmail(emailFriendAddRequestDto.getEmail());
        UserResponse.EmailFriendAddResponseDTO emailFriendAddResponseDto = this.userMBRepository.findByEmailFriendAdd(emailFriendAddRequestDto);
        if (emailFriendAddRequestDto.getEmail() == null || emailFriendAddRequestDto.getEmail().isEmpty()) {
            throw new MyBadRequestException("이메일을 입력하세요.");
        }
        if (userEmail == null) {
            throw new MyBadRequestException("등록되지 않은 사용자입니다.");
        }
        if (emailFriendAddResponseDto != null) {
            throw new MyBadRequestException("이미 친구등록된 이메일입니다.");
        }
        this.userMBRepository.emailFriendAdd(emailFriendAddRequestDto);
    }

    // 나의 프로필 삭제(프로필 이미지)
    public void myProfileImageDelete(Integer id) {
        this.userMBRepository.myProfileImageDelete(id);
    }

    // 나의 프로필 삭제(배경 이미지)
    public void myProfileBackImageDelete(Integer id) {
        this.userMBRepository.myProfileBackImageDelete(id);
    }

    // 친구 차단
    public void friendDelete(Integer id){
        this.userMBRepository.friendDeleteUpdate(id);
    }

    // 친구 검색
    public List<UserResponse.SearchFriendResponseDTO> searchFriend(String keyword, Integer sessionUserId){
        List<UserResponse.SearchFriendResponseDTO> searchFriendResponseDto = this.userMBRepository.findByFriend(keyword, sessionUserId).stream().filter(e->e.getId() != sessionUserId).distinct().toList();
        if (keyword == null || keyword.isEmpty()){
            throw new MyBadRequestException("검색어를 입력해주세요.");
        }
        if (searchFriendResponseDto.size() == 0){
            throw new MyBadRequestException("추가된 친구가 없습니다.");
        }

        return searchFriendResponseDto;
    }

    // 친구 목록 조회(카운트 하기)
    public Integer friendCount(Integer id){
        Integer friendCount = this.userMBRepository.findByFriendCount(id);
        return friendCount;
    }

    public UserResponse.loginDTO autoLogin(User sessionUser) {
        User user = userJPARepository.findById(sessionUser.getId()).orElseThrow(() -> new MyBadRequestException("자동 로그인 오류"));
        Profile profile = profileJPARepository.findByUserId(sessionUser.getId());

        String jwt = JwtTokenUtils.create(user);

        UserResponse.loginDTO dto = new UserResponse.loginDTO(user, profile);
        dto.setJwt(jwt);

        return dto;

    }


    public UserResponse.UserTestDTO userTest(int userId) {
        Optional<User> user = userJPARepository.findById(userId);

        UserResponse.UserTestDTO dto = new UserResponse.UserTestDTO(user.get());

        return dto;

    }

    public List<UserResponse.UserTestDTO> ListTest() {
        List<User> user = userJPARepository.findAll();

        List<UserResponse.UserTestDTO> newList = user.stream().map(e -> new UserResponse.UserTestDTO(e)).collect(Collectors.toList());

        return newList;
    }

    public List<UserResponse.GetChatUsersDTO> getChatUsers(UserRequest.GetChatUsersDTO dto) {
        List<User> users = userJPARepository.findAllById(dto.getUserIdList());

        List<UserResponse.GetChatUsersDTO> respDTO = users.stream().map(e->new UserResponse.GetChatUsersDTO(e, profileJPARepository.findByUserId(e.getId()))).collect(Collectors.toList());


        return respDTO;

    }

    public UserResponse.FriendProfileDetailResponseDTO searchUserByCondition(String condition) {
        List<User> user = new ArrayList<>();

        if(condition.contains("@")){
             Optional<User> opUser = userJPARepository.findByEmail(condition);
        }else{
            user = userJPARepository.findByPhoneNum(condition);
        }

        if(user.isEmpty()){
            UserResponse.FriendProfileDetailResponseDTO dto = new UserResponse.FriendProfileDetailResponseDTO();
            dto.setId(0);
            dto.setNickname("");
            dto.setBackImage("");
            dto.setProfileImage("");
            dto.setStatusMessage("");
            dto.setSuccess(false);
            return dto;
        }

        Profile profile = profileJPARepository.findByUserId(user.get(0).getId());

        UserResponse.FriendProfileDetailResponseDTO dto = new UserResponse.FriendProfileDetailResponseDTO(user.get(0), profile);
        dto.setSuccess(true);

        System.out.println(dto);
        return dto;
    }
}
