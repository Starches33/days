package com.clever.days.service;

import com.clever.days.model.Note;
import com.clever.days.util.Enums;

import java.util.List;

public interface NoteService {

    Note createNote(long tgId, String text, Enums.NoteType noteType, Enums.Sphere sphere);

    List<Note> getNoteListByTgId(long tgId);
}
