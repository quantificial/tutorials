package org.baeldung.config;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

// example of using sql to init the database	
//	@Value("classpath:schema.sql")
//	private Resource schemaScript;
	
//	@Bean
//	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//	    DataSourceInitializer initializer = new DataSourceInitializer();
//	    initializer.setDataSource(dataSource);
//	    initializer.setDatabasePopulator(databasePopulator());
//	    return initializer;
//	}
//	

//	private DatabasePopulator databasePopulator() {
//	    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//	    populator.addScript(schemaScript);
//	    return populator;
//	}
	
//	@Bean
//	public DataSource dataSource() {
//	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//	    dataSource.setUrl(env.getProperty("jdbc.url"));
//	    dataSource.setUsername(env.getProperty("jdbc.user"));
//	    dataSource.setPassword(env.getProperty("jdbc.pass"));
//	    return dataSource;
//	}

//  server credentials only
//	λ curl -s -u appalone:secret -X POST localhost:8081/auth/oauth/token?grant_type=client_credentials
//	{"access_token":"c70d254e-52e7-42c8-8856-1a29c905f640","token_type":"bearer","expires_in":3562,"scope":"read write"}
	
// test password grant_type
// curl -X POST --user clientId:secret http://localhost:8081/auth/oauth/token -H "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d "grant_type=password&username=johnson&password=abcd1234&scope=read"
	
	/*
	 * JWT Token Configuration - [
	 */
    @Autowired
    private Environment environment;	
	
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        String pwd = environment.getProperty("keystore.password");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("/keystore/jwt.jks"),
                pwd.toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }	
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }    
    		
	/*
	 * JWT Token Configuration - ]
	 */
    

	
    
//    @Autowired    
//    private BCryptPasswordEncoder passwordEncoder;

    @Autowired    
    private PasswordEncoder passwordEncoder;

    
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
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }

    // create the in memory token store
//    @Bean
//    public TokenStore inMemoryTokenStore() {
//        return new InMemoryTokenStore();
//    }
    
    // create JDBC client detail service
    @Bean(name="jdbcClientDetailsService")
    public JdbcClientDetailsService clientDetailsService() {
    	JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(oauthDataSource());
    	jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }    
        
    // token store in JDBC format
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(oauthDataSource());
//    }
    
    
    
    
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
     * configure token store
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
         		.tokenStore(tokenStore())
         		.tokenEnhancer(jwtTokenEnhancer()) // JWT
         		.approvalStore(approvalStore())
                .authenticationManager(authenticationManager);
    }    

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    	
    	// use jdbc instead of in memory
    	
    	 clients.withClientDetails(clientDetailsService());
    	 
    	 System.out.println(oauthDataSource().toString());

    	
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
