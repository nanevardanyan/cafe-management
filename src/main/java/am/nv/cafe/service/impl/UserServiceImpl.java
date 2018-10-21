package am.nv.cafe.service.impl;

import am.nv.cafe.dataaccess.model.User;
import am.nv.cafe.exception.DuplicateEntryException;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.repository.UserRepository;
import am.nv.cafe.service.UserService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User add(User user) throws DuplicateEntryException, InternalErrorException {
        if (repository.findByEmail(user.getEmail()) != null) {
            LOGGER.error("Failed to add. User already exists");
            throw new DuplicateEntryException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            return repository.saveAndFlush(user);
        } catch (Exception e) {
            String message = String.format("Failed to add the [User : %s ]", user);
            LOGGER.error(message, e);
            throw new InternalErrorException(message, e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null) {
            LOGGER.error("Failed to get user with the specified username " +
                    "[Username: %s]", email);
            throw new UsernameNotFoundException("Could not find user " + email);
        }
        return new CustomUserDetails(user);
    }

    private final static class CustomUserDetails extends User implements UserDetails {

        private CustomUserDetails(User user) {
            super(user);
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.createAuthorityList("ROLE_" + getProfile().name());
        }

        @Override
        public String getPassword() {
            return super.getPassword();
        }

        public String getUsername() {
            return getEmail();
        }

        public boolean isAccountNonExpired() {
            return true;
        }

        public boolean isAccountNonLocked() {
            return true;
        }

        public boolean isCredentialsNonExpired() {
            return true;
        }

        public boolean isEnabled() {
            return true;
        }
    }
}
