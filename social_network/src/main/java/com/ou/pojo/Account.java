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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Account implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "{account.email.notBlank}")
    @Email(message = "{account.email.invalid}")
    @Size(min = 1, message = "{account.email.invalidSize}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{account.password.notBlank}")
    @Size(min = 1, message = "{account.password.invalidSize}")
    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Size(max = 64)
    @Column(name = "verification_code")
    private String verificationCode;

    @Size(max = 30)
    @Column(name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role roleId;

    public Account(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
