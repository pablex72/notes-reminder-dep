package com.example.demo.controllers;

import com.example.demo.dto.NoteDTO;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.models.Note;
import com.example.demo.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/note")
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen
public class NoteController {
    private final NoteService noteService;
    @Autowired
    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public ResponseEntity createNote (@Valid @RequestBody NoteDTO noteDTO) {
        System.out.println("noteDTO"+ noteDTO);
        if (Objects.nonNull(noteService.findNote(noteDTO.getTitle()))){
            return new ResponseEntity<>("It is a duplicated title", HttpStatus.OK);
        }
        noteService.createNote(noteDTO);
        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoteDTO>> getNotesAll(){
        List<NoteDTO> noteList = noteService.getNotesAll();
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }
    @GetMapping("/active")
    public ResponseEntity<List<NoteDTO>> getNotesActive(){
        List<NoteDTO> noteList = noteService.getNotesActive();
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }
    @GetMapping("/archived")
    public ResponseEntity<List<NoteDTO>> getNotesArchived(){
        List<NoteDTO> noteList = noteService.getNotesArchived();
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }
    @PatchMapping("/update/{noteId}")
    public ResponseEntity updateNote (@PathVariable("noteId") Integer noteId ,
                                                   @Valid @RequestBody NoteDTO noteDTO){
        if (Objects.nonNull(noteService.findNote(noteId))){
            noteService.updateNote(noteId , noteDTO);
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Need to modify the id", HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/archived/{noteId}")
    public ResponseEntity toggleArchived(@PathVariable("noteId") Integer noteId){
        if (Objects.nonNull(noteService.findNote(noteId))){
            noteService.toggleIsArchived(noteId);
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Need to modify the id", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{noteId}")
    public ResponseEntity deleteNote (@PathVariable("noteId") Integer noteId){
        Note note = noteService.findNote(noteId).orElse(null);
        if (Objects.nonNull(note)){
            noteService.deleteNote(note);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Need to modify the id", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<?> getNoteById(@PathVariable("noteId") Integer noteId) {
        List<NoteDTO> noteDTOOptional = noteService.getNoteById(noteId);
        return new ResponseEntity<>(noteDTOOptional, HttpStatus.OK);
    }

//    @GetMapping("/{noteId}")
//    public ResponseEntity<NoteDTO> getNoteById(@PathVariable("noteId") Integer noteId) {
//        Optional<NoteDTO> noteDTOOptional = noteService.getNoteById(noteId);
//        return noteDTOOptional
//                .map(noteDTO -> new ResponseEntity<>(noteDTO, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }


}
