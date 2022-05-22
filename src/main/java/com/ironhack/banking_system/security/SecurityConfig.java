package com.ironhack.banking_system.security;

import com.ironhack.banking_system.filter.CustomAuthenticationFilter;
import com.ironhack.banking_system.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/bank/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/bank/login/**").permitAll();//anyone can have access to login
        //      admins
        http.authorizeRequests().antMatchers(GET, "/bank/admins").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/bank/admins").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/bank/admins/**").hasAnyAuthority("ROLE_ADMIN");
        //      accountholders
        http.authorizeRequests().antMatchers(GET, "/bank/accountholders/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/bank/accountholders").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/bank/accountholders").hasAnyAuthority("ROLE_ADMIN");
        //      accounts
        http.authorizeRequests().antMatchers(POST, "/bank/accounts").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/bank/accounts/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/bank/balance/**").hasAnyAuthority("ROLE_USER"); //???
        http.authorizeRequests().antMatchers(PATCH, "/bank/balance/**").hasAnyAuthority("ROLE_ADMIN");
        //      third parties
        http.authorizeRequests().antMatchers(POST, "/bank/thirdparties").hasAnyAuthority("ROLE_ADMIN");
        //      transfers
        http.authorizeRequests().antMatchers(POST, "/bank/transfers").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/bank/fromthirdparty/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/bank/tothirdparty/**").permitAll();
        //      roles
        http.authorizeRequests().antMatchers(POST, "/bank/roles").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/bank/roles/addtouser").hasAnyAuthority("ROLE_ADMIN");
        //      users
        http.authorizeRequests().antMatchers(GET, "/bank/users").hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().anyRequest().authenticated(); //any other request will have to be authenticated
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

