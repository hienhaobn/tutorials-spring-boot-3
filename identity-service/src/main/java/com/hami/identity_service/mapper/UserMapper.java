package com.hami.identity_service.mapper;

import com.hami.identity_service.dto.request.UserCreationRequest;
import com.hami.identity_service.dto.request.UserUpdateRequest;
import com.hami.identity_service.dto.response.UserResponse;
import com.hami.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    // don't map the field lastname, it will return null
    //  @Mapping(target = "lastName", ignore = true)
    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
