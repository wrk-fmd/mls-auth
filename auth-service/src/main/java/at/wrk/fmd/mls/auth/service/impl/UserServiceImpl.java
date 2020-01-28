package at.wrk.fmd.mls.auth.service.impl;

import static java.util.Objects.requireNonNull;

import at.wrk.fmd.mls.auth.repository.UserRepository;
import at.wrk.fmd.mls.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = requireNonNull(userRepository, "UserRepository must not be null");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Could not find user %s", username)));
    }
}
