package dev.fillipe.expense_tracker.security.userdetails;

import dev.fillipe.expense_tracker.models.User;
import dev.fillipe.expense_tracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(UUID.fromString(username)).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        return new UserDetailsImpl(user);
    }


}
