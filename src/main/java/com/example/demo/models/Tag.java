package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
//import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="Tags")
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator= "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    @Column(name="Tag_Name", unique = true)
    @NotBlank(message="Cannot be blank")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tagList")
    Set<Note> notesList;
    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Note> getNotesList() {
        return notesList;
    }

    public void setNotesList(Set<Note> notesList) {
        this.notesList = notesList;
    }
}