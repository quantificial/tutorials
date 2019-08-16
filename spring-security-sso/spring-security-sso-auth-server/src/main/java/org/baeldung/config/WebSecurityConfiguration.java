package org.baeldung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@Order(1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) throws Exception {
//	    web.ignoring()
//	            .antMatchers("/test");
	}
	
	private PasswordEncoder passwordEncoder;
	
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        if (passwordEncoder == null) {
            passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
        return passwordEncoder;
    }
    
//    @Bean
//    public ServletContextTemplateResolver templateResolver() {
//    	
//        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(null);
//        resolver.setPrefix("/WEB-INF/views");
//        resolver.setSuffix(".html"); // here
//        resolver.setTemplateMode("HTML5");
//        resolver.setOrder(1);
//        return resolver;
//    }    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
    	
    	// http.requestMatchers define which requests will be intercepted
    	// The requestMatchers line specifies to which requests the security check applies. The authorizeRequests line does the actual security check
        
    	// for oauth2 endpoints
    	http
        	.requestMatchers()        	        	
            	.antMatchers("/login", "/oauth/authorize","/oauth/check_token"); 

        // other endpoints
    	http.requestMatchers().antMatchers("/ologin");
        http.requestMatchers().antMatchers("/exit");
        http.requestMatchers().antMatchers("/h2","/h2/**");
        http.requestMatchers().antMatchers("/test");
        http.requestMatchers().antMatchers("/moon");
        http.requestMatchers().antMatchers("/sun");
        http.requestMatchers().antMatchers("/resources/templates/**");
        http.requestMatchers().antMatchers("/resources/**");
        
        // such that we could assign the permission
        http.authorizeRequests().antMatchers("/ologin").permitAll();
        http.authorizeRequests().antMatchers("/test").permitAll();
        http.authorizeRequests().antMatchers("/moon").permitAll();
        http.authorizeRequests().antMatchers("/sun").permitAll();
        http.authorizeRequests().antMatchers("/h2/**").permitAll();
        http.authorizeRequests().antMatchers("/resources/templates/**").permitAll();
        http.authorizeRequests().antMatchers("/resources/**").permitAll();

        // all other requests must be authenticated
        http
            .authorizeRequests()            
            	.anyRequest()
            	.authenticated();
                        
        // form based authentication is supported
        //http.formLogin().permitAll();
        http.formLogin().loginPage("/ologin").permitAll();
        //http.formLogin().loginProcessingUrl("/perform_login");
        
        
        // create custom login form 

        // disable csrf, such that get could be used for /logout
        http.csrf().disable();
        
        // for h2
        http.headers().frameOptions().disable();
        
        
        //example
        //http.authorizeRequests().antMatchers(HttpMethod.GET).access("permitAll");
        //http.authorizeRequests().antMatchers("/v1/hello").hasRole("USER");
        //http.authorizeRequests().antMatchers(HttpMethod.POST, "/v1/world").hasRole("USER");
        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/v1/hello").access("permitAll");
        //http.authorizeRequests().antMatchers("/v1/admin").hasRole("ADMIN");
        //http.authorizeRequests().antMatchers("/**/*.html").access("permitAll");
        //http.authorizeRequests().antMatchers("/**/*.css").access("permitAll");
        //http.authorizeRequests().antMatchers("/**/*.js").access("permitAll");
        //http.authorizeRequests().antMatchers("/**/*.png").access("permitAll");
        //http.authorizeRequests().anyRequest().authenticated();        
    	    			
        
    } // @formatter:on

    
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new JdbcUserDetails();
    }
       
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
    	
    	auth.userDetailsService(userDetailsServiceBean())
    		.passwordEncoder(passwordEncoder())
    		;
    	
//        auth.inMemoryAuthentication()
//            .withUser("johnson")
//            .password(passwordEncoder().encode("abcd1234"))
//            .roles("USER");
        
        
    } // @formatter:on

}
