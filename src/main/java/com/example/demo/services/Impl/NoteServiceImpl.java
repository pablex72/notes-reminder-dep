package com.example.demo.services.Impl;


import com.example.demo.dto.NoteDTO;
import com.example.demo.dto.TagDTO;
import com.example.demo.models.Note;
import com.example.demo.models.Tag;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.TagRepository;
import com.example.demo.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository,
                                     TagRepository tagRepository){
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Note findNote(String title) {
        return noteRepository.findByTitle(title);
    }

    @Override
    public Optional<Note> findNote(Integer id) {
        return noteRepository.findById(id);
    }

//    @Override
//    public List<NoteDTO> getNoteById(Integer noteId) {
//        return noteRepository.findById(noteId).stream().map(NoteDTO::new).collect(Collectors.toList());
//    }


    @Override
    public void createNote(NoteDTO noteDTO) {

        Set<Tag> uniqueTagListToSave = checkForPersistedDuplicates(noteDTO);

        Note note = new Note(noteDTO.getTitle(), noteDTO.getContent(), uniqueTagListToSave);

        noteRepository.save(note);
    }

    @Override
    public List<NoteDTO> getNotesAll() {

        return noteRepository.findAll().stream().map(NoteDTO::new).collect(Collectors.toList());

    }

    @Override
    public List<NoteDTO> getNotesActive() {

        return noteRepository.findNoteByIsArchived(false).stream().map(NoteDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<NoteDTO> getNotesArchived() {

        return noteRepository.findNoteByIsArchived(true).stream().map(NoteDTO::new).collect(Collectors.toList());
    }

    @Override
    public void updateNote(Integer noteId, NoteDTO noteDTO) {
        Note note = noteRepository.findById(noteId).get();
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());

        Set<Tag> set = checkForPersistedDuplicates(noteDTO);
        for (Tag tag : set){
            tagRepository.save(tag);
        }
        note.setTagList(set);
        noteRepository.save(note);
    }

    @Override
    public void toggleIsArchived(Integer noteId) {
        Note note = noteRepository.findById(noteId).get();
        note.toggleArchived();
        noteRepository.save(note);
    }

@Override
public List<NoteDTO> getNoteById(Integer noteId) {
      return noteRepository.findById(noteId).stream().map(NoteDTO::new).collect(Collectors.toList());
}

    @Override
    public void deleteNote(Note noteToDelete) {
        noteRepository.delete(noteToDelete);
    }


    private Set<Tag> checkForPersistedDuplicates(NoteDTO noteDTO){
        if (Objects.isNull(noteDTO.getTagList())){
            return null;
        }

        Set<TagDTO> uniqueTagsList = new HashSet<>();
        for (TagDTO tagDTO : noteDTO.getTagList()){
            if (uniqueTagsList.stream().noneMatch(tag-> tag.getName().equalsIgnoreCase(tagDTO.getName()))){
                Tag persistedTag = tagRepository.findByName(tagDTO.getName());
                if (Objects.nonNull(persistedTag)){
                    uniqueTagsList.add(new TagDTO(persistedTag));
                }else{
                    uniqueTagsList.add(tagDTO);
                }
            }

        }

        return uniqueTagsList.stream()
                .map(tagDTO -> {
                    Tag persistedTag = tagRepository.findByName(tagDTO.getName());
                    return persistedTag != null ? persistedTag : new Tag(tagDTO.getName());
                })
                .collect(Collectors.toSet());
    }
}
