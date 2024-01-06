package com.clever.days.mapper;

import com.clever.days.entity.UserEO;
import com.clever.days.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DomUserMapper {
    User map(UserEO userEO);
}
