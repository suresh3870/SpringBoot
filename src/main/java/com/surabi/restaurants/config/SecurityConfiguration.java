package com.surabi.restaurants.config;

import com.surabi.restaurants.Enum.Authority;
import com.surabi.restaurants.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(getPasswordEncoder())
                .usersByUsernameQuery(
                        "SELECT username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "SELECT username, authority from users where username = ?"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/surabi/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/surabi/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/", "/surabi/usersReg").permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/", true)
                .permitAll();
    }

    //	If you don't want to encode the created password, you can write the below bean method, FYI: not recommended for Prod env
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");

    }
   /*@Bean
   @Override
    public UserDetailsService userDetailsService(){
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        GrantedAuthority authority1 = new SimpleGrantedAuthority("ROLE_USER");
        UserDetails adminUserDetails = (UserDetails)new User("sk", "sk",true, Authority.ROLE_ADMIN);
        UserDetails basicUser = (UserDetails)new User("ms", "ms", true, Authority.ROLE_USER);
        return new InMemoryUserDetailsManager(Arrays.asList(adminUserDetails, basicUser));
    }*/
}
