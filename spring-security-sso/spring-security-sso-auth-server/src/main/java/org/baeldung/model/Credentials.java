package org.baeldung.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name ="credentials")
public class Credentials implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    // example to specify the table
//    @JoinTable(
//    		  name = "course_like", 
//    		  joinColumns = @JoinColumn(name = "student_id"), 
//    		  inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "credentials_authorities",
    joinColumns = @JoinColumn(name = "credentials_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))    
    private List<Authority> authorities;

    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
