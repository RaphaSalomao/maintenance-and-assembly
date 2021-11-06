package com.raphasalomao.maintenanceandassembly.service;

import com.raphasalomao.maintenanceandassembly.model.CreateUserRequest;
import com.raphasalomao.maintenanceandassembly.model.Principal;
import com.raphasalomao.maintenanceandassembly.model.entity.User;
import com.raphasalomao.maintenanceandassembly.model.enumerated.Role;
import com.raphasalomao.maintenanceandassembly.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public void createUser(final CreateUserRequest user) {
        var entity = User.builder()
                .email(user.getEmail())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .phone(user.getPhone())
                .role(Role.CUSTOMER)
                .name(user.getName())
                .build();
        try {
            if (this.retrievePrincipal().getRole().equals(Role.ADMIN)) {
                entity.setRole(Role.USER);
            }
        } catch (Exception e) {
        }
        userRepository.save(entity);
    }

    public User findUserByEmailOrPhone(final String emailOrPhone) {
        return userRepository.findByEmailOrPhone(emailOrPhone, emailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        var user = this.findUserByEmailOrPhone(username);
        return new Principal(user);
    }

    public User retrievePrincipal() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        return this.findUserByEmailOrPhone(username);
    }

    public String retrievePrincipalRole() {
        try {
            return this.retrievePrincipal().getRole().toString();
        } catch (Exception e) {
            return "ANONYMOUS";
        }
    }
}
