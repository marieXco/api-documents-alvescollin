package io.swagger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration()
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("editor").password(passwordEncoder().encode("editorPassword"))
                .roles("EDITOR");
        auth.inMemoryAuthentication()
                .withUser("reviewer").password(passwordEncoder().encode("reviewerPassword"))
                .roles("REVIEWER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/documents").hasRole("EDITOR")
                .antMatchers(HttpMethod.PUT,"/documents/**/status").hasRole("REVIEWER")
                .antMatchers("/documents/**/lock").hasAnyRole("REVIEWER", "EDITOR")
                .antMatchers(HttpMethod.GET, "/documents/**/").hasAnyRole("REVIEWER", "EDITOR")
                .antMatchers(HttpMethod.PUT, "/documents/**/").hasAnyRole("REVIEWER", "EDITOR")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
