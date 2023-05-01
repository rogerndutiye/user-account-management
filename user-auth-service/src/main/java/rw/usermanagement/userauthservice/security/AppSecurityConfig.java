package rw.usermanagement.userauthservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestTokenFilter jwtRequestTokenFilter;

    public AppSecurityConfig(UserDetailsService jwtUserDetailsService, JwtRequestTokenFilter jwtRequestTokenFilter) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestTokenFilter = jwtRequestTokenFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                .antMatchers(
                        "/api/**",
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html")
                .permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            Map<String, Object> responseMap = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(401);
            responseMap.put("statusCode", 401);
            responseMap.put("message", "Unauthorized");
            responseMap.put("result", null);
            responseMap.put("timestamp", LocalDateTime.now().toString());
            response.setHeader("content-type", "application/json");
            String responseMsg = mapper.writeValueAsString(responseMap);
            response.getWriter().write(responseMsg);
        }).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilterBefore(jwtRequestTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/logout")
                .invalidateHttpSession(true)
                .deleteCookies("Authorization")
                .logoutSuccessHandler(logoutSuccessHandler());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.OK.value());
            response.sendRedirect("auth/login");
        };
    }
}
