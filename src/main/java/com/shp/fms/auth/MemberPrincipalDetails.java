//package com.shp.fms.auth;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.shp.fms.model.MemberInfo;
//
//public class MemberPrincipalDetails implements UserDetails {
//	private static final long serialVersionUID = 1L;
//	
//	private MemberInfo member;
//	
//	public MemberPrincipalDetails(MemberInfo member) {
//		this.member = member;
//	}
//	
//	public MemberInfo getMember() {
//		return member;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority(member.getRole()));
//		return authorities;
//	}
//
//	@Override
//	public String getPassword() {
//		return member.getLoginPw();
//	}
//
//	@Override
//	public String getUsername() {
//		return member.getLoginId();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//}
