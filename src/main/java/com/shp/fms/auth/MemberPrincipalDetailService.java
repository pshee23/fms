//package com.shp.fms.auth;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.shp.fms.model.MemberInfo;
//import com.shp.fms.service.MemberService;
//
//import lombok.AllArgsConstructor;
//
//@Service
//@AllArgsConstructor
//public class MemberPrincipalDetailService implements UserDetailsService {
//
//	private final MemberService service;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		MemberInfo member = service.getMemberInfoByLoginId(username);
//		return new MemberPrincipalDetails(member);
//	}
//
//}
