/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author PHONG
 */
@Entity
@Table(name = "question")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2000)
    @Column(name = "question_text")
    private String questionText;
    @Column(name = "is_mandatory")
    private Boolean isMandatory;
    @Column(name = "question_order")
    private Integer questionOrder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private List<QuestionOption> questionOptions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private List<Answer> answers;
    @JsonIgnore
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PostSurvey surveyId;
    @JoinColumn(name = "question_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private QuestionType questionTypeId;
    // @Override
    // public String toString() {
    //     return "Question [questionText=" + questionText + ", isMandatory=" + isMandatory + ", questionOrder="
    //             + questionOrder + ", questionOptions=" + questionOptions + ", questionTypeId=" + questionTypeId + "]";
    // }

    
    
}
