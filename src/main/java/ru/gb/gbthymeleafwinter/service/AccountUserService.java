package ru.gb.gbthymeleafwinter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.gbthymeleafwinter.dao.security.AccountRoleDao;
import ru.gb.gbthymeleafwinter.dao.security.AccountUserDao;
import ru.gb.gbthymeleafwinter.entity.security.AccountUser;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountUserService {
    private final AccountUserDao userDao;
    private final AccountRoleDao roleDao;

    public AccountUser findById(Long id) {
        return userDao.findById(id).orElseThrow();
    }

    public AccountUser save(AccountUser user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (user.getId() != null) {
            Optional<AccountUser> userFromDbOptional = userDao.findById(user.getId());
            if (userFromDbOptional.isPresent()) {
                AccountUser userFromDb = userFromDbOptional.get();
//                userFromDb.setUsername(user.getUsername());
                if (user.getRoles().isEmpty()) user.getRoles().add(roleDao.findAccountRoleByName("ROLE_USER"));
                userFromDb.setRoles(user.getRoles());
                if (!bCryptPasswordEncoder.matches(userFromDb.getPassword(), user.getPassword()))
                    userFromDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userFromDb.setFirstname(user.getFirstname());
                userFromDb.setLastname(user.getLastname());
                userFromDb.setAccountNonExpired(user.isAccountNonExpired());
                userFromDb.setAccountNonLocked(user.isAccountNonLocked());
                userFromDb.setEnabled(user.isEnabled());
                userFromDb.setCredentialsNonExpired(user.isCredentialsNonExpired());
                return userDao.save(userFromDb);
            }
        }
        AccountUser accountUser = AccountUser.builder()
                .username(user.getUsername())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .role(roleDao.findAccountRoleByName("ROLE_USER"))
                .build();
        return userDao.save(accountUser);
    }
}
