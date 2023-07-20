package com.ou.pojo;

import java.io.Serializable;
<<<<<<< HEAD
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
=======
>>>>>>> 641bc84320599c5d54f40ef10ac9ef030e2392f1
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
<<<<<<< HEAD
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
=======
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

>>>>>>> 641bc84320599c5d54f40ef10ac9ef030e2392f1
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_student")
<<<<<<< HEAD
public class UserStudent implements Serializable {
=======
public class UserStudent implements Serializable{
>>>>>>> 641bc84320599c5d54f40ef10ac9ef030e2392f1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
<<<<<<< HEAD
    
    @NotBlank(message = "user identical could not be blank")
    @NotNull
    @Size(min = 10, max = 10, message = "user identical must be 10 characters")
    @Column(name = "student_identical")
    private String studentIdentical;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private User user;
=======

    @NotBlank(message = "Student identical must not be blank")
    @NotNull
    @Size(min = 10, max = 10, message = "Invalid student identical")
    @Column(name = "student_identical")
    private String studentIdentical;

    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public UserStudent(Integer id, String studentIdentical) {
        this.id = id;
        this.studentIdentical = studentIdentical;
    }
>>>>>>> 641bc84320599c5d54f40ef10ac9ef030e2392f1
}
