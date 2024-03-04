package com.shp.fms.auth.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shp.fms.auth.Login;
import com.shp.fms.model.entity.Employee;
import com.shp.fms.model.entity.Member;
import com.shp.fms.repository.EmployeeRepository;
import com.shp.fms.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	private final EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("[loadUserByUsername] 실행");
		
		Optional<Employee> employeeOp = employeeRepository.findByLoginId(username);
		if(employeeOp.isPresent()) {
			return makeEntity(employeeOp.get());
		}
		
		Optional<Member> memberOp = memberRepository.findByLoginId(username);
		if(memberOp.isPresent()) {
			return makeEntity(memberOp.get());
		}
		
		throw new UsernameNotFoundException(username);
	}

	private UserDetails makeEntity(Employee employee) {
		Login userEntity = new Login();
		userEntity.setId(employee.getEmployeeId());
		userEntity.setUsername(employee.getLoginId());
		userEntity.setPassword(employee.getLoginPw());
		userEntity.setRoles(employee.getRole());
		
		return makePrincipalDetails(userEntity);
	}
	
	private UserDetails makeEntity(Member member) {
		Login userEntity = new Login();
		userEntity.setId(member.getMemberId());
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
