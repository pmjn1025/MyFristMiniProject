package com.sparta.miniproject_movie_study_01.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.miniproject_movie_study_01.controller.request.CommentRequestDto;
import com.sparta.miniproject_movie_study_01.controller.request.MemberProfileRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String nickname;

  @Column(name = "profileimg")
  String profileimg;

  @Column(nullable = false)
  @JsonIgnore
  private String password;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Member member = (Member) o;
    return id != null && Objects.equals(id, member.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
  public void  updateNickEmail(MemberProfileRequestDto memberProfileRequestDto){
    this.nickname = memberProfileRequestDto.getNickname();
    this.email = memberProfileRequestDto.getEmail();;

  }
  public void updateimg(String profileimg) {
    this.profileimg = profileimg;
  }

  public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
    return passwordEncoder.matches(password, this.password);
  }
}
