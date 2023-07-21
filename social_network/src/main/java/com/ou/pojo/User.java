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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author PHONG
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "First name could not be blank")
    @NotNull
    @Size(min = 1, max = 45, message = "First name could be from 1 to 45 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name could not be blank")
    @NotNull
    @Size(min = 1, max = 45, message = "Last name could be from 1 to 45 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;

    @Size(max = 300)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 300)
    @Column(name = "cover_avatar")
    private String coverAvatar;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Post> postCollection;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<PostReaction> postReactionCollection;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Comment> commentCollection;

    @JsonIgnore
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Account account;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<CommentReaction> commentReactionCollection;

    @JsonIgnore
<<<<<<< HEAD
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
=======
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
>>>>>>> 641bc84320599c5d54f40ef10ac9ef030e2392f1
    private UserStudent userStudent;

    public User(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
