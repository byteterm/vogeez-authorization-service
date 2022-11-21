package net.vogeez.authorization.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * This Entity class represents a User in the database.
 * It contains all the information needed Information about a user that
 * we need in our application.
 *
 * @see Entity
 * @see Table
 * @see Id
 * @see GeneratedValue
 * @see Column
 * @see JsonIgnore
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 50, updatable = false)
    private String username;
    private String avatar;
    @Column(length = 144, nullable = false)
    @JsonIgnore
    private String password;
    @Column(unique = true, length = 100, nullable = false, updatable = false)
    private String email;
    @Column(nullable = false, length = 50)
    private String nickname;
    @Column(unique = true, length = 5, nullable = false)
    private String byteId;

    @Column(nullable = false)
    private boolean accountNonExpired;
    @Column(nullable = false)
    private boolean accountNonLocked;
    @Column(nullable = false)
    private boolean credentialsNonExpired;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false)
    private boolean emailVerified;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
