package org.baeldung.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Autowired    
    private BCryptPasswordEncoder passwordEncoder;

    // h2 datasource
    //@Autowired
    //private DataSource dataSource;
    
    
    // datasource in bean
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource oauthDataSource() {
        return DataSourceBuilder.create().build();
    	//return this.dataSource;
    }
    
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //oauthServer.tokenKeyAccess("permitAll()")
        //    .checkTokenAccess("isAuthenticated()");
    }

    // create the in memory token store
    @Bean
    public TokenStore inMemoryTokenStore() {
        return new InMemoryTokenStore();
    }
    
    // create JDBC client detail service
    @Bean(name="jdbcClientDetailsService")
    public JdbcClientDetailsService clientDetailsService() {
    	JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(oauthDataSource());
    	jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }    
        
    // token store in JDBC format
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(oauthDataSource());
    }
    
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(oauthDataSource());
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(oauthDataSource());
    }
            
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
        
    
    /**
     * config to use in memory token store
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
         		.tokenStore(tokenStore())
         		.approvalStore(approvalStore())
                .authenticationManager(authenticationManager);
                
    }    

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    	
    	// use jdbc instead of in memory
    	
    	 clients.withClientDetails(clientDetailsService());
    	 
    	 

    	
    	/** data example
    	 *  https://pattern-match.com/blog/2018/10/17/springboot2-with-oauth2-integration/
    	 *  
    	 *  
	        clients
	        .inMemory()
	        .withClient(CLIENT_ID)
	        .secret(CLIENT_SECRET)
	        .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN)
	        .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
	        .accessTokenValiditySeconds(VALID_FOREVER)
	        .refreshTokenValiditySeconds(VALID_FOREVER);
        */    	
    	
    	System.out.println(passwordEncoder.encode("secret"));
    	// $2a$10$w6U0a421YvG8GuVXeVcyk..7Kba/esmX0jNwV2jYYwedKC2CIT7Qy    	
    	    	
    	/*
        clients.inMemory()
            .withClient("app1")
            .secret(passwordEncoder.encode("secret"))
            .authorizedGrantTypes("authorization_code")
            .scopes("user_info")
            .autoApprove(true)            
            .redirectUris("http://localhost:8082/ui/login")
            .accessTokenValiditySeconds(3600)    
            .and()
	        .withClient("app2")
	        .secret(passwordEncoder.encode("secret"))
	        .authorizedGrantTypes("authorization_code")
	        .scopes("user_info")
	        .autoApprove(true)
	        .redirectUris("http://localhost:8083/ui2/login")
	        .accessTokenValiditySeconds(3600)                                    
        ;
        */
        
    }

}
