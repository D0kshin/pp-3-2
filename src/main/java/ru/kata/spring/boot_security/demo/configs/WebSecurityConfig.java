package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan(value = "ru.kata.spring.boot_security.demo")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    private final SuccessUserHandler successUserHandler;

    private final LoginSuccessHandler  loginSuccessHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler,  LoginSuccessHandler loginSuccessHandler) {
        this.successUserHandler = successUserHandler;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .successHandler(loginSuccessHandler)
                .and()
                .logout()
                .permitAll();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider  daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}