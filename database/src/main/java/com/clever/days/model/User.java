package com.clever.days.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class User {

    private UUID id;

    private Long tgId;

    private String name;
}
