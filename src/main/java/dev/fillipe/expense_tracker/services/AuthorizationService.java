package dev.fillipe.expense_tracker.services;

import dev.fillipe.expense_tracker.dto.input.RegisterAuthDTO;
import dev.fillipe.expense_tracker.dto.input.LoginAuthDTO;
import dev.fillipe.expense_tracker.enums.RoleName;
import dev.fillipe.expense_tracker.models.Role;
import dev.fillipe.expense_tracker.models.User;
import dev.fillipe.expense_tracker.repositories.UserRepository;
import dev.fillipe.expense_tracker.security.config.SecurityConfig;
import dev.fillipe.expense_tracker.security.jwt.JwtTokenService;
import dev.fillipe.expense_tracker.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorizationService {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    public String login(LoginAuthDTO loginAuthDTO) {

        if (loginAuthDTO.email().isBlank() || loginAuthDTO.password().isBlank()) {
            throw new AuthenticationServiceException("Email and password cannot be blank");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginAuthDTO.email(), loginAuthDTO.password());

        Authentication auth = authenticationManager.authenticate(authenticationToken);

        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();

        return jwtTokenService.generateToken(user);
    }

    public void register(RegisterAuthDTO registerAuthDTO, RoleName role) {
        Optional<User> userOptional = userRepository.findByEmail(registerAuthDTO.email());

        if (userOptional.isPresent()) {
            throw new AuthenticationServiceException("User already exists");
        }

        if (registerAuthDTO.password().isBlank() || registerAuthDTO.email().isBlank()) {
            throw new AuthenticationServiceException("Email and password cannot be blank");
        }

        User user = User.builder()
                .email(registerAuthDTO.email())
                .password(securityConfig.passwordEncoder().encode(registerAuthDTO.password()))
                .roles(List.of(new Role(role)))
                .build();

        userRepository.save(user);
    }
}
