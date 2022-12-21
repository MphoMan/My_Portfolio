package io.jumpco.psetbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jumpco.psetbackend.model.enumeration.UserType;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "pset_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PsetUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 50)
    @NotBlank(message = "First Name can not be empty")
    @NotNull(message = "First Name can not be null")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Size(min = 3, max = 50)
    @NotBlank(message = "Last Name can not be empty")
    @NotNull(message = "Last Name can not be null")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Transient
    private String confirmPassword;
    @Column(name = "active")
    private Boolean enabled;
    @JsonIgnore
    private Boolean locked = false;

    @Enumerated(EnumType.STRING)
    private UserType type;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "budget_user_authority",
            joinColumns = { @JoinColumn(name = "budget_user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id", referencedColumnName = "id") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();
    public void addAuthority(Authority authority){
        this.authorities.add(authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(auth -> new SimpleGrantedAuthority(auth.getName())).collect(Collectors.toList());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
