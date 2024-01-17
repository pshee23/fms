//package com.shp.fms.auth.auth;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.shp.fms.auth.Login;
//
//import lombok.Data;
//
//@Data
//public class PrincipalDetails implements UserDetails {
//	private static final long serialVersionUID = 2439417033846575577L;
//	
//	private Login loginBody;
//	
//	public PrincipalDetails(Login loginBody) {
//		this.loginBody = loginBody;
//	}
//	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Collection<GrantedAuthority> authroities = new ArrayList<>();
//		loginBody.getRoleList().forEach(r -> {
//			authroities.add(() -> r);
//		});
//		return authroities;
//	}
//
//	@Override
//	public String getPassword() {
//		return loginBody.getPassword();
//	}
//
//	@Override
//	public String getUsername() {
//		return loginBody.getUsername();
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
//	
//}
