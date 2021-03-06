package pl.edu.agh.carhire.security;

import pl.edu.agh.carhire.model.Role;
import pl.edu.agh.carhire.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Klasa odpowiadajaca pobieranie kontekstu zalogowanego użytkownika wraz
 * z jego prawami dostepu do roznych modulow aplikacji
 */
public class CustomUserDetails extends User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	public CustomUserDetails(User user){
		if (user != null) {
			this.setId(user.getId());
			this.setUsername(user.getUsername());
			this.setEmail(user.getEmail());
			this.setPassword(user.getPassword());
			this.setFirstName(user.getFirstName());
			this.setLastName(user.getLastName());
			this.setEnabled(user.getEnabled());
			this.setRoles(user.getRoles());
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		for (final Role role : this.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
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
		return true;
	}

	public String getName() {
		return this.getFirstName() + " " + this.getLastName();
	}
}
