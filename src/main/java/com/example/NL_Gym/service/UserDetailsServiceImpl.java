    package com.example.NL_Gym.service;
    import com.example.NL_Gym.model.User;
    import com.example.NL_Gym.repository.UserRepository;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;
    import java.util.Optional;

    @Service
    public class UserDetailsServiceImpl implements UserDetailsService {
        private final UserRepository userRepository;

        public UserDetailsServiceImpl(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
        }


    }
