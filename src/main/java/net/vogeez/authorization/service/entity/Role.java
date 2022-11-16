package net.vogeez.authorization.service.entity;

import lombok.*;

import javax.persistence.*;

/**
 * This Entity class is used to store / get the a role from the database.
 * The role is used to make it simpler to give the user the right permissions.
 * Here we store the role name, the permissions, the aliases of the role, a icon
 * and a color for the role. For every variable we set some information for the database
 * like the column name, the length of the column... over the annotations.
 *
 * @see Table
 * @see Entity
 * @see Id
 * @see GeneratedValue
 * @see Column
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
@Table(name = "roles")
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;
}
