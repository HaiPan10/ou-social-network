/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PHONG
 */
@Entity
@Table(name = "invitation_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvitationGroup.findAll", query = "SELECT i FROM InvitationGroup i"),
    @NamedQuery(name = "InvitationGroup.findById", query = "SELECT i FROM InvitationGroup i WHERE i.id = :id"),
    @NamedQuery(name = "InvitationGroup.findByGroupName", query = "SELECT i FROM InvitationGroup i WHERE i.groupName = :groupName")})
public class InvitationGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "group_name")
    private String groupName;
    @OneToMany(mappedBy = "groupId")
    private Collection<PostInvitation> postInvitationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<GroupUser> groupUserCollection;

    public InvitationGroup() {
    }

    public InvitationGroup(Integer id) {
        this.id = id;
    }

    public InvitationGroup(Integer id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @XmlTransient
    public Collection<PostInvitation> getPostInvitationCollection() {
        return postInvitationCollection;
    }

    public void setPostInvitationCollection(Collection<PostInvitation> postInvitationCollection) {
        this.postInvitationCollection = postInvitationCollection;
    }

    @XmlTransient
    public Collection<GroupUser> getGroupUserCollection() {
        return groupUserCollection;
    }

    public void setGroupUserCollection(Collection<GroupUser> groupUserCollection) {
        this.groupUserCollection = groupUserCollection;
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
        if (!(object instanceof InvitationGroup)) {
            return false;
        }
        InvitationGroup other = (InvitationGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ou.pojo.InvitationGroup[ id=" + id + " ]";
    }
    
}
