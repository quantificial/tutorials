package org.baeldung.config;

import org.baeldung.model.Credentials;

import javax.servlet.http.HttpServletRequest;

import org.baeldung.model.Authority;
import org.baeldung.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by ahmed on 21.5.18.
 */
public class JdbcUserDetails implements UserDetailsService{

    @Autowired
    private CredentialRepository credentialRepository;
    
    public static String getRequestRemoteAddr(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
                   .getRequest(); 
        return request.getRemoteAddr();
    }        

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials credentials = credentialRepository.findByName(username);


        if(credentials==null){

            throw new UsernameNotFoundException("User"+username+"can not be found");
        }

        User user = new User(credentials.getName(), 
        		credentials.getPassword(),
        		credentials.isEnabled(),
        		true, // account non-expired
        		true, // credentialsNonExpired 
        		true, // accountNonLocked 
        		credentials.getAuthorities());
        
        System.out.println("## Authority ##########################################");
        for(Authority s : credentials.getAuthorities()) {
        	System.out.println(s.getAuthority());
        }
        
        System.out.println("## IP ##########################################");
        System.out.println(JdbcUserDetails.getRequestRemoteAddr());
        

        return  user;


    }
}
