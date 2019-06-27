package com.mycompany.climbworksscheduler.config;

import com.mycompany.climbworksscheduler.data.EmployeeRepo;
import com.mycompany.climbworksscheduler.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = EmployeeRepo.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService uds;
    
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        
//        auth.userDetailsService(uds)
//                .passwordEncoder(getPasswordEncoder());
//    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/guide/**").hasAuthority("USER")
                    .anyRequest().permitAll()
                    .and().csrf().disable()
                    .formLogin().failureUrl("/login?login_error=1").permitAll()
                    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index.html");
    }

//    private PasswordEncoder getPasswordEncoder() {
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence.toString();
//            }
//            
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return true;
//            }
//        };
//    }

    @Autowired
    public void configureGlobalInDB(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(uds).passwordEncoder(bCryptPasswordEncoder());
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
