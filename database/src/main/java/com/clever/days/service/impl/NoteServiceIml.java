package com.clever.days.service.impl;

import com.clever.days.entity.NoteEO;
import com.clever.days.mapper.DomNoteMapper;
import com.clever.days.model.Note;
import com.clever.days.repository.NoteRepository;
import com.clever.days.repository.UserRepository;
import com.clever.days.service.NoteService;
import com.clever.days.util.Enums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NoteServiceIml implements NoteService {

    private final NoteRepository noteRepository;

    private final UserRepository userRepository;

    private DomNoteMapper domNoteMapper;

    @Autowired
    private NoteServiceIml(NoteRepository noteRepository, UserRepository userRepository, DomNoteMapper domNoteMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.domNoteMapper = domNoteMapper;
    }

    @Override
    public Note createNote(long tgId, String text, Enums.NoteType noteType, Enums.Sphere sphere) {
        var uuid = UUID.randomUUID();
        var user = userRepository.findByTgId(tgId);

        return domNoteMapper.toDto(
                noteRepository.save(new NoteEO(uuid, tgId, LocalDateTime.now(), noteType, sphere, text, user.get())));
    }

    public List<Note> getNoteListByTgId(long tgId) {
        //todo - Антоха, как думаешь, правильно понимаю, что в бд с фильтром tgId ведь процесс пойдет быстрее,
        // чем тащить allNotes сюда и в стриме фильтровать?

        return noteRepository.findAllByTgId(tgId).stream()
                .map(domNoteMapper::toDto)
                .collect(Collectors.toList());
    }
}
