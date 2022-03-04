package ru.gb.gbthymeleafwinter.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.gbthymeleafwinter.dao.security.AccountUserDao;
import ru.gb.gbthymeleafwinter.entity.security.AccountUser;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final AccountUserDao accountUserDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " not found")
        );
    }

    @Transactional(readOnly = true)
    public AccountUser findByName(String username){
        return accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " not found")
        );
    }
}
