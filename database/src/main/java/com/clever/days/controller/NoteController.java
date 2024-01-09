package com.clever.days.controller;

import com.clever.days.model.Note;
import com.clever.days.service.NoteService;
import com.clever.days.util.Enums;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public ResponseEntity<Note> createNote(@RequestParam long tgId, @RequestParam String text,
                                           @RequestParam Enums.NoteType noteType,
                                           @RequestParam @Nullable Enums.Sphere sphere) {
        return ResponseEntity.ok(noteService.createNote(tgId, text, noteType, sphere));
    }

    @GetMapping("/all/{tgId}")
    public ResponseEntity<List<Note>> getAllNotesByTgId(@PathVariable(value = "tgId") long id) {

        return ResponseEntity.ok().body(noteService.getNoteListByTgId(id));
    }







//    @GetMapping("/{userId}")
//    public ResponseEntity<List<Note>> getNotesByUserId(@RequestParam UUID userId) {
//        List<Note> notes = noteService.getNoteListByUserId(userId);
//        return ResponseEntity.ok(notes);
//    }

}
