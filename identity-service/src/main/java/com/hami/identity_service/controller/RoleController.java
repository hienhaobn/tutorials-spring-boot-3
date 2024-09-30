package com.hami.identity_service.controller;

import com.hami.identity_service.dto.request.ApiResponse;
import com.hami.identity_service.dto.request.PermissionRequest;
import com.hami.identity_service.dto.request.RoleRequest;
import com.hami.identity_service.dto.response.PermissionResponse;
import com.hami.identity_service.dto.response.RoleResponse;
import com.hami.identity_service.service.PermissionService;
import com.hami.identity_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @PutMapping
    ApiResponse<RoleResponse> update(@PathVariable("roleId") String roleId, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.update(roleId, request))
                .build();
    }

    @DeleteMapping("/{roleId}")
    ApiResponse<Void> delete(@PathVariable String roleId) {
        roleService.delete(roleId);
        return ApiResponse.<Void>builder().build();
    }
}
