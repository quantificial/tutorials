package org.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
        	.requestMatchers()        	        	
            	.antMatchers("/login", "/oauth/authorize","/exit","/h2","/h2/**")
            	.and()
            	.authorizeRequests()            
            	.anyRequest()
            	.authenticated()
//            .and()
//            	.authorizeRequests()
//            	.antMatchers("/h2/**").permitAll()
        	.and()
            	.formLogin()
            	.permitAll()            
            .and().csrf().disable()            
            ;
    	
    	
//        http
//    	.authorizeRequests()
//    		.antMatchers("/h2_console/**").permitAll()
//    		.and()
//    	.authorizeRequests()        	        	
//        	.antMatchers("/login", "/oauth/authorize","/exit")
//        	.authenticated()
//    	.and()
//        	.formLogin()
//        	.permitAll()            
//        .and().csrf().disable()            
//        ;    	
//        
//        http.headers().frameOptions().disable();
        

    	
 //        http.authorizeRequests()        
//        .antMatchers("/h2_console/**").permitAll();
//		http.csrf().disable();
		http.headers().frameOptions().disable();
        
    } // @formatter:on
    
   
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth.inMemoryAuthentication()
            .withUser("johnson")
            .password(passwordEncoder().encode("abcd1234"))
            .roles("USER");
        
    } // @formatter:on

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
