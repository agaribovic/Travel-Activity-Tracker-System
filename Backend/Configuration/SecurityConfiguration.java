package com.team1.demo.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin","/adduserpage", "/addhotelpage", "/userslistpage", "/hotelslistpage", "/adduser", "/addhotel", "/userslist", "/edit/user", "/edit/user/", "/deleteuser", "/deleteuser/", "/deletehotel", "/deletehotel/", "/gethotel", "/gethotel/", "/updatelonglat", "/updatelonglat/","/edit/hotel", "/edit/hotel/").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/userpanel", "/userpanel/", "/userpanel/**", "/reservation").access("hasAnyRole('ROLE_USER')")
                .antMatchers("/supervisor","/getreservations").access("hasAnyRole('ROLE_SUPERVISOR')")
                .antMatchers("/", "/login", "/aboutus", "/contactus","/account/confirmation", "/registration", "/password/forgotten", "/password/reset").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/default")
                .and()
                .logout()
                .logoutSuccessUrl("/?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }


}
