package no.linska.webapp.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity(name = "user_account")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="user_id")
    private Long id;

    @Column(name = "enabled")
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull
    private UserRole userRole;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "email_is_empty")
    private String email;

    @Column(name = "password")
    @Size(min = 8, max = 60, message = "password_must_contain_between_8_and_60_characters")
    @NotBlank(message = "password_is_empty")

    private String password;

    private String phoneNumber;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<PriceRequest> priceRequestList;

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s',email='%s']",
                id, firstName, lastName,email);
    }



}