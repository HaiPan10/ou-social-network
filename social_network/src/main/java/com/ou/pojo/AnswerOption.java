/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "answer_option")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @JoinColumn(name = "answer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Answer answerId;

    @JoinColumn(name = "question_option_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private QuestionOption questionOptionId;

    @Override
    public String toString() {
        return "AnswerOption [questionOptionId=" + questionOptionId + "]";
    }
}
