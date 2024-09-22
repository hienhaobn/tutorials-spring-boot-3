package com.hami.identity_service.mapper;

import com.hami.identity_service.dto.request.PermissionRequest;
import com.hami.identity_service.dto.request.UserCreationRequest;
import com.hami.identity_service.dto.request.UserUpdateRequest;
import com.hami.identity_service.dto.response.PermissionResponse;
import com.hami.identity_service.dto.response.UserResponse;
import com.hami.identity_service.entity.Permission;
import com.hami.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    // don't map the field lastname, it will return null
    //  @Mapping(target = "lastName", ignore = true)
    PermissionResponse toPermissionResponse(Permission permission);
}
