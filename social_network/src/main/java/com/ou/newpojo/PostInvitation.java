/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.newpojo;

import com.ou.pojo.Post;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PHONG
 */
@Entity
@Table(name = "post_invitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PostInvitation.findAll", query = "SELECT p FROM PostInvitation p"),
    @NamedQuery(name = "PostInvitation.findById", query = "SELECT p FROM PostInvitation p WHERE p.id = :id"),
    @NamedQuery(name = "PostInvitation.findByEventName", query = "SELECT p FROM PostInvitation p WHERE p.eventName = :eventName"),
    @NamedQuery(name = "PostInvitation.findByStartAt", query = "SELECT p FROM PostInvitation p WHERE p.startAt = :startAt")})
public class PostInvitation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 250)
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "start_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postInvitationId")
    private Collection<PostInvitationUser> postInvitationUserCollection;
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne
    private Group1 groupId;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Post post;

    public PostInvitation() {
    }

    public PostInvitation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    @XmlTransient
    public Collection<PostInvitationUser> getPostInvitationUserCollection() {
        return postInvitationUserCollection;
    }

    public void setPostInvitationUserCollection(Collection<PostInvitationUser> postInvitationUserCollection) {
        this.postInvitationUserCollection = postInvitationUserCollection;
    }

    public Group1 getGroupId() {
        return groupId;
    }

    public void setGroupId(Group1 groupId) {
        this.groupId = groupId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
        if (!(object instanceof PostInvitation)) {
            return false;
        }
        PostInvitation other = (PostInvitation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ou.newpojo.PostInvitation[ id=" + id + " ]";
    }
    
}
