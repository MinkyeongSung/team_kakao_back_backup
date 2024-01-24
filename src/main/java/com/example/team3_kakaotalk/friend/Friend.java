package com.example.team3_kakaotalk.friend;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.example.team3_kakaotalk.user.User;

@Data
@Entity
@Table(name = "friend_tb", uniqueConstraints = {
        @UniqueConstraint(
                name = "user1_user2_unique",
                columnNames = {"user_id1", "user_id2"}
        )
})
@ToString
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id1")
    private Integer userId1;
    @Column(name = "user_id2")
    private Integer userId2;
    @ColumnDefault("false")
    private Boolean isBlocked;

    @Builder
    public Friend(Integer id, Integer userId1, Integer userId2, Boolean isBlocked) {
        this.id = id;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.isBlocked = isBlocked;
    }
}
