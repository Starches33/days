package com.clever.days.entity;

import com.clever.days.util.Enums;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "notes")
public class NoteEO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tg_id")
    private long tgId;

    @Column(nullable = false)
    private LocalDateTime postDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private Enums.NoteType noteType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sphere", length = 20)
    private Enums.Sphere sphere;

    @Column(name = "text", length = 3000)
    private String text;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEO user;
}
