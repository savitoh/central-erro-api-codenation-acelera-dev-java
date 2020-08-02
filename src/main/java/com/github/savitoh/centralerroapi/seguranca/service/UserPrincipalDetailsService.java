package com.github.savitoh.centralerroapi.seguranca.service;

import com.github.savitoh.centralerroapi.seguranca.UserPrincipal;
import com.github.savitoh.centralerroapi.user.User;
import com.github.savitoh.centralerroapi.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Não foi possivel encontrar usuário com login: " + login));
        return new UserPrincipal(user);
    }

    public UserDetails loadUserById(Integer userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Não foi possível encontrar o usuário com id: " + userId));
        return new UserPrincipal(user);
    }

}
