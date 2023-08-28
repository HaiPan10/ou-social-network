/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "post_survey")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PostSurvey.findAll", query = "SELECT p FROM PostSurvey p"),
    @NamedQuery(name = "PostSurvey.findById", query = "SELECT p FROM PostSurvey p WHERE p.id = :id"),
    @NamedQuery(name = "PostSurvey.findByStartAt", query = "SELECT p FROM PostSurvey p WHERE p.startAt = :startAt"),
    @NamedQuery(name = "PostSurvey.findBySurveyTitle", query = "SELECT p FROM PostSurvey p WHERE p.surveyTitle = :surveyTitle"),
    @NamedQuery(name = "PostSurvey.findBySurveyStatus", query = "SELECT p FROM PostSurvey p WHERE p.surveyStatus = :surveyStatus")})
public class PostSurvey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "start_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;
    @Size(max = 100)
    @Column(name = "survey_title")
    private String surveyTitle;
    @Size(max = 6)
    @Column(name = "survey_status")
    private String surveyStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "surveyId")
    private Collection<Question> questionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "surveyId")
    private Collection<Response> responseCollection;

    public PostSurvey() {
    }

    public PostSurvey(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(String surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    @XmlTransient
    public Collection<Response> getResponseCollection() {
        return responseCollection;
    }

    public void setResponseCollection(Collection<Response> responseCollection) {
        this.responseCollection = responseCollection;
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
        if (!(object instanceof PostSurvey)) {
            return false;
        }
        PostSurvey other = (PostSurvey) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ou.pojo.PostSurvey[ id=" + id + " ]";
    }
    
}
