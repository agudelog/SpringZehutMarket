package com.zehut.market.web.security;

import com.zehut.market.domain.service.ZehutUserDetailsService;
import com.zehut.market.web.security.filter.JwtFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Configuration
public class SecurityConfig{
    @Autowired
    private ZehutUserDetailsService zehutUserDetailsService;

    @Autowired
    private JwtFilterRequest jwtFilterRequest;

    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

        //auth.userDetailsService(zehutUserDetailsService);
        auth.setUserDetailsService(zehutUserDetailsService);

        return auth;
    }

    //@Override
    //protected void configure(HttpSecurity http) throws Exception {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.csrf().disable().authorizeRequests().antMatchers("/**/authenticate")
                .permitAll().anyRequest().authenticated().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

   // @Override
   // @Bean
   // public AuthenticationManager authenticationManagerBean() throws Exception {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
