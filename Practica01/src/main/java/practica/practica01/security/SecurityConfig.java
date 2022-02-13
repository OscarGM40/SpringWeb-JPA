package practica.practica01.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import practica.practica01.filters.CustomAuthenticationFilter;
import practica.practica01.filters.CustomAuthorizationFilter;

/* @Configuration implica que Spring tratará esta clase como un configFile.@EnableWebSecurity activa la seguridad?? */
@Configuration  @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
  
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Bean @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

    /* autenticacion */  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder); 
  }
  
  
  /* autorizacion */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
    customAuthenticationFilter.setFilterProcessesUrl("/api/login");

    /* deshabilito la CRSF para pasar de STATEFUL con cookies a  STATELESS con tokens(usaremos JWT) */
    http.csrf().disable();
    /* pasamos a stateless */ 
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    /* todos pueden acceder al login,solo los ROLE_USER pueden ver /api/user y solo los ADMIN pueden crear por POST.Además,cada request debe estar autenticado */
    http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/token/refresh").permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/login").permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/user/**").hasAnyAuthority("ROLE_USER");
    http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
    http.authorizeRequests().anyRequest().authenticated();

    /* aqui le pasaré mis filtros que debo crear */
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    
  }
  
}
