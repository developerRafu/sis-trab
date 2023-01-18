package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.User;
import com.rafu.sistrab.rest.dto.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(final RegisterRequest request);
}

