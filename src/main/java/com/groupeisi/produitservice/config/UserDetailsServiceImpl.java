package com.groupeisi.produitservice.config;

import com.groupeisi.produitservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .map(u -> User.builder()
                .username(u.getEmail())
                .password(u.getPassword())
                .roles(u.getRole().name())
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));
    }
}
