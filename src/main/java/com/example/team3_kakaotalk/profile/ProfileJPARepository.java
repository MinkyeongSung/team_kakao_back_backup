package com.example.team3_kakaotalk.profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJPARepository extends JpaRepository<Profile, Integer> {

    Profile findByUserId(Integer id);
}
