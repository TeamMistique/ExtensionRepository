package com.teammistique.extensionrepository.models;

import com.fasterxml.jackson.annotation.*;
import com.teammistique.extensionrepository.models.security.Role;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int id;

    @Column(name = "Username")
    private String username;


    @Column(name = "Password")
    private String password;

    @Column(name = "Enabled")
    private int enabled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "UserID")},
            inverseJoinColumns = {@JoinColumn(name = "RoleID")}
    )
    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Role> roles;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Extension> extensions;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled==1;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
