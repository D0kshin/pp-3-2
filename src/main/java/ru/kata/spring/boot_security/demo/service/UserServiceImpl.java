package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    @Override
    public void add(User user) {
        if (user.getRoles().isEmpty()){
            user.setRoles(List.of(roleService.findByName("ROLE_USER")));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        if (user.getRoles().isEmpty()){
            user.setRoles(List.of(roleService.findByName("ROLE_USER")));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }
}
