package com.shp.fms.auth.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shp.fms.auth.Login;
import com.shp.fms.model.entity.Member;
import com.shp.fms.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("[loadUserByUsername] 실행");
		
		Optional<Member> memberOp = memberRepository.findByLoginId(username);
		if(memberOp.isPresent()) {
			return makeEntity(memberOp.get());
		}
		
		throw new UsernameNotFoundException(username);
	}
	
	private UserDetails makeEntity(Member member) {
		Login userEntity = new Login();
		userEntity.setId(member.getId());
		userEntity.setUsername(member.getLoginId());
		userEntity.setPassword(member.getLoginPw());
		userEntity.setRoles(member.getRole());
		
		return makePrincipalDetails(userEntity);
	}
	
	private UserDetails makePrincipalDetails(Login entity) {
		log.info("[loadUserByUsername] 종료");
		return new PrincipalDetails(entity);
	}
}
