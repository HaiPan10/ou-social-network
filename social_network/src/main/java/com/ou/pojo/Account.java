package com.ou.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author PHONG
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    @Size(min = 1, max = 45, message = "Email must be between 1 and 45 characters")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 1, max = 45, message = "Password must be between 1 and 45 characters")
    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Size(max = 7)
    @Column(name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role roleId;

    public Account(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }    
}
