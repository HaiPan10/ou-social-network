/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.newpojo;

import com.ou.pojo.User;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PHONG
 */
@Entity
@Table(name = "post_invitation_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PostInvitationUser.findAll", query = "SELECT p FROM PostInvitationUser p"),
    @NamedQuery(name = "PostInvitationUser.findById", query = "SELECT p FROM PostInvitationUser p WHERE p.id = :id")})
public class PostInvitationUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "post_invitation_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PostInvitation postInvitationId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public PostInvitationUser() {
    }

    public PostInvitationUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PostInvitation getPostInvitationId() {
        return postInvitationId;
    }

    public void setPostInvitationId(PostInvitation postInvitationId) {
        this.postInvitationId = postInvitationId;
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
        if (!(object instanceof PostInvitationUser)) {
            return false;
        }
        PostInvitationUser other = (PostInvitationUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ou.newpojo.PostInvitationUser[ id=" + id + " ]";
    }
    
}
