package com.sparta.miniproject_movie_study_01.service;


import com.sparta.miniproject_movie_study_01.domain.Member;
import com.sparta.miniproject_movie_study_01.domain.UserDetailsImpl;
import com.sparta.miniproject_movie_study_01.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 이부분에서 나중에 인덱스나, 다른 부분으로 바꿀 수있다.
    Optional<Member> member = memberRepository.findByNickname(username);
    return member
        .map(UserDetailsImpl::new)
        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
  }
}
