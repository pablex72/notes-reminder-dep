package com.example.demo.dto;

import com.example.demo.models.Tag;
import lombok.Data;

@Data
public class TagDTO {
    private Integer id;
    private String name;

    public TagDTO() {}
    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

    public TagDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
