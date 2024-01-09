package com.clever.days.mapper;

import com.clever.days.entity.NoteEO;
import com.clever.days.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DomNoteMapper {

    Note toDto(NoteEO noteEO);

    NoteEO toEntity(Note note) ;
}
