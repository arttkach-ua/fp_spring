package com.epam.tkach.carrent.config;

import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.handlers.CustomSuccessHandler;
import com.epam.tkach.carrent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired private CustomSuccessHandler loginSuccessHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (UserDetailsService) email -> {
            Optional<User> user = userService.findByEmail(email);
            if (user.isPresent()) {
                if (user.get().isBlocked()) throw new UsernameNotFoundException("User is blocked:::" + email);
                return user.get();

            } else {
                throw new UsernameNotFoundException("No user fount with email:::" + email);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()
                //Доступ клиента
                .antMatchers("/users/topUp").hasRole("CLIENT")
                .antMatchers("/users/myProfile", "/users/save").hasAnyRole("ADMIN","CLIENT", "MANAGER")
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/carModels/list").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/carModels/**").hasRole("ADMIN")
                .antMatchers("/tariff/list").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/tariff/**").hasRole("ADMIN")
                .antMatchers("/users/**").hasRole("ADMIN")


                //Доступ разрешен всем пользователей
                .antMatchers("/", "/international","/resources/**","/static/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему

                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("email")
                    .successHandler(loginSuccessHandler)
                    //.defaultSuccessUrl("/", true)
                .permitAll()

                //Перенарпавление на главную страницу после успешного входа
                //.defaultSuccessUrl("/index")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
        //        http
//                .authorizeRequests()
//                    .antMatchers("/", "/home", "/registration").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .and()
//                .logout()
//                    .permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(bCryptPasswordEncoder());
    }
}
