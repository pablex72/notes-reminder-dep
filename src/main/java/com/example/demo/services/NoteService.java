package com.example.demo.services;


import com.example.demo.dto.NoteDTO;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.models.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    Note findNote(String title);

    Optional<Note> findNote(Integer id);

    void createNote(NoteDTO noteDTO) ;

    List<NoteDTO> getNotesAll();

    List<NoteDTO> getNotesActive();

    List<NoteDTO> getNotesArchived();

    void updateNote(Integer id, NoteDTO noteDTO);

    void deleteNote(Note note);

    void toggleIsArchived(Integer noteId);

    List<NoteDTO> getNoteById(Integer noteId);
}

