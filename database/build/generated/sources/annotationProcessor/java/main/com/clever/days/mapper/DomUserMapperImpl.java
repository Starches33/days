package com.clever.days.mapper;

import com.clever.days.entity.UserEO;
import com.clever.days.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-06T12:35:05+0400",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class DomUserMapperImpl implements DomUserMapper {

    @Override
    public User map(UserEO userEO) {
        if ( userEO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userEO.getId() );
        user.tgId( userEO.getTgId() );
        user.name( userEO.getName() );

        return user.build();
    }
}
