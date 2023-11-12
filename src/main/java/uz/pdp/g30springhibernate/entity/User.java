package uz.pdp.g30springhibernate.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedQueries(value = {
        @NamedQuery(name = "find_all_users", query = "select u from User u"),
        @NamedQuery(name = "find_by_username", query = "select u from User u where u.username=:username"),
        @NamedQuery(name = "find_by_email", query = "select u from User u where u.email=?1"),
})
public class User {
//    @Id
//    @SequenceGenerator(name = "custom_user_seq", sequenceName = "custom_user_seq", initialValue = 1, allocationSize = 2)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="custom_user_seq")
//    private long id;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @Column(columnDefinition = "varchar(255) unique")
    private String email;
    @Column(nullable = false, length = 16)
    private String password;
    private Integer age;
}
