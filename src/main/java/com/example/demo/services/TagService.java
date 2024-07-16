package com.example.demo.services;


import com.example.demo.dto.TagDTO;
import com.example.demo.models.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Tag findTag(String name);

    Optional<Tag> findTag(Integer id);

    void createTag(TagDTO tagDTO);

    List<TagDTO> getTags();

    void deleteTag(Tag tag);
}
