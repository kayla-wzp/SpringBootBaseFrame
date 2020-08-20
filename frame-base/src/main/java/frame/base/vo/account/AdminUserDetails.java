package frame.base.vo.account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Permission;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUserDetails implements UserDetails {
	private User user;
	private List<Permission> permissionList;

	public AdminUserDetails(User user, List<Permission> permissionList) {
		this.user = user;
		this.permissionList = permissionList;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//返回当前用户的权限
		return permissionList.stream()
				.filter(permission -> permission.getActions()!=null)
				.map(permission ->new SimpleGrantedAuthority(permission.getActions()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getLoginPassword();
	}

	@Override
	public String getUsername() {
		return user.getLoginNo();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getStatus().equals("1");
	}
}
