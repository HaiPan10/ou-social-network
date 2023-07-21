package com.ou.pojo;

import java.io.Serializable;

import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_student")
public class UserStudent implements Serializable{
    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Student identical must not be blank")
    @NotNull
    @Size(min = 10, max = 10, message = "Invalid student identical")
    @Column(name = "student_identical")
    private String studentIdentical;

    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    // public UserStudent(Integer id, String studentIdentical) {
    //     this.id = id;
    //     this.studentIdentical = studentIdentical;
    // }
}