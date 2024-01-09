//package com.clever.days.service;
//
//import com.clever.days.BaseServiceTest;
//import com.clever.days.repository.NoteRepository;
//import com.clever.days.repository.UserRepository;
//import com.clever.days.util.Enums;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class UserNoteServiceTest extends BaseServiceTest {
//
//    @Autowired
//    private NoteService noteService;
//    @Autowired
//    private NoteRepository noteRepository;
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void createUserNote() {
//
//        long tgId = 333L;
//
//        var user = userService.createUser(tgId, "test");
//
//        assertNotNull(user);
//        assertNotNull(user.getId());
//
//        var userActual = userRepository.findByTgId(user.getTgId()).orElse(null);
//
//        assertNotNull(userActual);
//        assertEquals(user.getName(), userActual.getName());
//
//
//        var note = noteService.createNote(3337L, RandomStringUtils.randomAlphabetic(40),
//                Enums.NoteType.USEFUL, Enums.Sphere.CRYPTO, UUID.randomUUID());
//
//        assertNotNull(note);
//        assertNotNull(note.getId());
//
//        var NoteActual = noteRepository.findById(note.getId()).orElse(null);
//
//        assertNotNull(NoteActual);
//        assertEquals(note.getText(), NoteActual.getText());
//    }
//
//
//}
