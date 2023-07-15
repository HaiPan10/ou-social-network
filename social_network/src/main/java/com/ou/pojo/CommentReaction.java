/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PHONG
 */
@Entity
@Table(name = "comment_reaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommentReaction.findAll", query = "SELECT c FROM CommentReaction c"),
    @NamedQuery(name = "CommentReaction.findById", query = "SELECT c FROM CommentReaction c WHERE c.id = :id")})
public class CommentReaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Comment commentId;
    @JoinColumn(name = "reaction_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Reaction reactionId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public CommentReaction() {
    }

    public CommentReaction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comment getCommentId() {
        return commentId;
    }

    public void setCommentId(Comment commentId) {
        this.commentId = commentId;
    }

    public Reaction getReactionId() {
        return reactionId;
    }

    public void setReactionId(Reaction reactionId) {
        this.reactionId = reactionId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentReaction)) {
            return false;
        }
        CommentReaction other = (CommentReaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ou.pojo.CommentReaction[ id=" + id + " ]";
    }
    
}
