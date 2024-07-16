package com.example.demo.dto;

//import com.ensolvers.notes.models.Note;
//import com.ensolvers.notes.models.Tag;
import com.example.demo.models.Note;
import com.example.demo.models.Tag;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
@Data
public class NoteDTO {
    private Integer id;
    @NotBlank(message = "Cannot be blank")

    private String title;
    @NotBlank(message = "Cannot be blank")
    private String content;
    private Set<TagDTO> tagList;
    private Boolean isArchived = false;


    public NoteDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        if (Objects.nonNull(note.getTagList())) {
            this.tagList = note.getTagList().stream().map(TagDTO::new).collect(Collectors.toSet());
        }
        this.isArchived = note.getArchived();
    }

    public NoteDTO(Integer id, String title, String content, Set<Tag> tagList, Boolean isArchived, LocalDateTime lastEdited) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tagList = tagList.stream().map(TagDTO::new).collect(Collectors.toSet());
        ;
        this.isArchived = isArchived;
    }

}
