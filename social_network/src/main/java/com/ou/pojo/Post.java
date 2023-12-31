package com.ou.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "post")
@ToString
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 255, message = "{post.content.invalidSize}")
    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;

    @Column(name = "is_active_comment")
    private Boolean isActiveComment;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "postId")
    private List<ImageInPost> imageInPostList;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "postId")
    private List<PostReaction> postReactionList;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "postId")
    private List<Comment> commentList;

    @Transient
    private Map<Integer, Long> reactionTotal;
    @Transient
    private Integer commentTotal;
    // @Override
    // public String toString() {
    //     return "Post [id=" + id + ", content=" + content + ", isActiveComment=" + isActiveComment + ", imageInPostList="
    //             + imageInPostList + "]";
    // }
    @Transient
    private Reaction currentReaction;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "post")
    private PostSurvey postSurvey;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "post")
    private PostInvitation postInvitation;

    public Post(Integer id){
        this.id = id;
    }

    public Post(Post post, PostInvitation postInvitation) {
        this.id = post.getId();
        this.postInvitation = postInvitation;
    }
}
