/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author PHONG
 */
@Entity
@Table(name = "post_survey")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSurvey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
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
    private Collection<Question> questions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "surveyId")
    private Collection<Response> responses;
    @JsonIgnore
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Post post;
    @Override
    public String toString() {
        return "PostSurvey [surveyTitle=" + surveyTitle + ", surveyStatus=" + surveyStatus + ", questions=" + questions
                + "]";
    }

    
    
}
