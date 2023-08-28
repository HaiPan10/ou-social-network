/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.newpojo;

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
@Table(name = "answer_option")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnswerOption.findAll", query = "SELECT a FROM AnswerOption a"),
    @NamedQuery(name = "AnswerOption.findById", query = "SELECT a FROM AnswerOption a WHERE a.id = :id")})
public class AnswerOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Answer answerId;
    @JoinColumn(name = "question_option_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private QuestionOption questionOptionId;

    public AnswerOption() {
    }

    public AnswerOption(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Answer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answer answerId) {
        this.answerId = answerId;
    }

    public QuestionOption getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(QuestionOption questionOptionId) {
        this.questionOptionId = questionOptionId;
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
        if (!(object instanceof AnswerOption)) {
            return false;
        }
        AnswerOption other = (AnswerOption) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ou.newpojo.AnswerOption[ id=" + id + " ]";
    }
    
}
