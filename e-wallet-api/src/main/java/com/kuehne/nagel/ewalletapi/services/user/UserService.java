package com.kuehne.nagel.ewalletapi.services.user;

import com.kuehne.nagel.ewalletapi.models.dtos.UserDetailDto;
import com.kuehne.nagel.ewalletapi.repositories.UserRepository;
import com.kuehne.nagel.ewalletapi.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation {@link UserDetailsService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /**
     * @see UserRepository
     */
    private final UserRepository userRepository;

    @Override
    public UserDetailDto loadUserByUsername(String username) {

        return Optional
                .ofNullable(userRepository.findByLogin(username))
                .map(UserMapper.INSTANCE::convert)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
