package com.clever.days.repository;

import com.clever.days.entity.NoteEO;
import com.clever.days.util.Enums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteEO, UUID> {


    List<NoteEO> findAllByTgId(long tgId);

//    List<NoteEO> findAllByUuid(UUID tgUuid);
//
//    List<NoteEO> findAllByTgIdAndNoteTypeAndSphere(long tgId, Enums.NoteType noteType, String sphere);
//
//    List<NoteEO> findAllByTgIdAndCreatedDateBetween(long tgId, LocalDateTime noteDateTime);
}
