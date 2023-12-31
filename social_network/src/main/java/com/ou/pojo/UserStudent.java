package com.ou.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    // @NotNull
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "{userStudent.studentIdentical.notBlank}")
    @NotNull
    @Size(min = 10, max = 10, message = "{userStudent.studentIdentical.invalid}")
    @Column(name = "student_identical")
    private String studentIdentical;

    @JsonIgnore
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Override
    public String toString() {
        return "UserStudent [id=" + id + ", studentIdentical=" + studentIdentical + "]";
    }

    

    // public UserStudent(Integer id, String studentIdentical) {
    //     this.id = id;
    //     this.studentIdentical = studentIdentical;
    // }
}
