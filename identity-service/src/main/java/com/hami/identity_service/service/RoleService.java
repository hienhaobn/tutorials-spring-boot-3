package com.hami.identity_service.service;

import com.hami.identity_service.dto.request.RoleRequest;
import com.hami.identity_service.dto.response.RoleResponse;
import com.hami.identity_service.entity.Permission;
import com.hami.identity_service.entity.Role;
import com.hami.identity_service.exception.AppException;
import com.hami.identity_service.exception.ErrorCode;
import com.hami.identity_service.mapper.RoleMapper;
import com.hami.identity_service.repository.PermissionRepository;
import com.hami.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Transactional
    public RoleResponse update(String roleId, RoleRequest request) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());

        if(permissions.size() != request.getPermissions().size()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }

    private Role findRoleById(String roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }

    private Set<Permission> findAndValidatePermissions(List<String> permissionIds) {
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);

        if (permissions.size() != permissionIds.size()) {
            List<String> foundPermissionIds = permissions.stream()
                    .map(Permission::getName)
                    .toList();
            List<String> missingPermissionIds = permissionIds.stream()
                    .filter(id -> !foundPermissionIds.contains(id))
                    .toList();

            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        return new HashSet<>(permissions);
    }

    private void updateRolePermissions(Role role, Set<Permission> permissions) {
        role.setPermissions(permissions);
    }
}
