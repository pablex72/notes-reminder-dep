package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
//import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
@Data
@AllArgsConstructor
@Entity
@Table
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator= "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    @Column(name="Title")
    @NotBlank(message="Cannot be blank")
    private String title;
    @Column(name="Content")
    @NotBlank(message="Cannot be blank")
    private String content;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Note_Tags",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @OrderBy
    private Set<Tag> tagList;
    @Column(name="Is_Archived")
    private Boolean isArchived = false;

    public Note() {
    }

    public Note(@NotBlank String title , @NotBlank String content) {
        this.title = title;
        this.content = content;
    }

    public Note(@NotBlank String title ,@NotBlank String content, @NotNull Set<Tag> tagList) {
        this.title = title;
        this.content = content;
        this.tagList = tagList;
        this.isArchived = false;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void toggleArchived() {
        this.isArchived = !this.isArchived;
    }


}