package org.baeldung.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.baeldung.model.Authority;
import org.baeldung.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialDao {

//	   @Autowired
//	   private JdbcTemplate jdbcTemplate;
//	   
//	   public Credentials getUserDetails(String username) {
//		   
//		      Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
//		      
//		      String userSQLQuery = "SELECT * FROM credentials WHERE name=?";
//		      		      
//		      List<Credentials> list = jdbcTemplate.query(userSQLQuery, new String[] { username }, (ResultSet rs, int rowNum) -> {		         
//		        	 Credentials user = new Credentials();
//		        	 user.setName(username);		        
//		        	 user.setPassword(rs.getString("PASSWORD"));		         
//		         return user;
//		      });
//		      
//		      if (list.size() > 0) {
//		    	  GrantedAuthority  authority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
//		         grantedAuthoritiesList.add(authority);
//		         list.get(0).setGrantedAuthoritiesList(grantedAuthoritiesList);
//		         return list.get(0);
//		      }
//		      return null;
//	   }
}
