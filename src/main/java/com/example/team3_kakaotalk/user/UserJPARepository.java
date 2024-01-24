package com.example.team3_kakaotalk.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team3_kakaotalk.user.UserResponse.FriendTepMainResponseDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);


	List<FriendTepMainResponseDTO> findAllById(int id);


    Optional<User> findById(Integer userId);

   // Optional<User> updateUserByEmail(Integer id, String email);
    //userId, email 기반으로 찾고 업데이트

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.phoneNum = :newPhoneNum WHERE u.email = :email")
    void updatePhoneNumById(@Param("newPhoneNum") String newPhoneNum, @Param("email") String email);
    List<User> findByPhoneNum(String condition);

}



