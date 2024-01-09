package com.clever.days.model;

import com.clever.days.util.Enums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Note {

    private LocalDateTime noteDateTime;

    private Enums.NoteType noteType;

    private Enums.Sphere sphere;

    private String text;

}
