package com.hami.identity_service.mapper;

import com.hami.identity_service.dto.request.PermissionRequest;
import com.hami.identity_service.dto.request.RoleRequest;
import com.hami.identity_service.dto.response.PermissionResponse;
import com.hami.identity_service.dto.response.RoleResponse;
import com.hami.identity_service.entity.Permission;
import com.hami.identity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // ignore field permission và tu map va build
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
