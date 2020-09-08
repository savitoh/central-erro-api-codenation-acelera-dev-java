package com.github.savitoh.centralerroapi.seguranca.config;


import com.github.savitoh.centralerroapi.seguranca.AutenticacaoJwtEntryPoint;
import com.github.savitoh.centralerroapi.seguranca.filter.FiltroJwtAutenticacao;
import com.github.savitoh.centralerroapi.seguranca.jwt.TokenManager;
import com.github.savitoh.centralerroapi.seguranca.service.UserPrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Order(2)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/v1/**")
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new FiltroJwtAutenticacao(tokenManager, userPrincipalDetailsService),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(autenticacaoEntryPoint());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userPrincipalDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**",
                "/configuration/**", "/swagger-resources/**", "/css/**", "/**.ico", "/js/**");
    }

    @Bean
    public AuthenticationEntryPoint autenticacaoEntryPoint() {
        return new AutenticacaoJwtEntryPoint();
    }

}
