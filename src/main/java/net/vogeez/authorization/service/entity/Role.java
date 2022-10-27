package net.vogeez.authorization.service.entity;

import lombok.*;

import javax.persistence.*;

/**
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
