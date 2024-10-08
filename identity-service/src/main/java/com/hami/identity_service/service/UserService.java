package com.hami.identity_service.service;

import com.hami.identity_service.constant.PredefineRole;
import com.hami.identity_service.dto.request.UserCreationRequest;
import com.hami.identity_service.dto.request.UserUpdateRequest;
import com.hami.identity_service.dto.response.UserResponse;
import com.hami.identity_service.entity.Role;
import com.hami.identity_service.entity.User;
import com.hami.identity_service.exception.AppException;
import com.hami.identity_service.exception.ErrorCode;
import com.hami.identity_service.mapper.UserMapper;
import com.hami.identity_service.repository.RoleRepository;
import com.hami.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
// it will create a constructor that includes all variables start with "final" and inject it
@RequiredArgsConstructor
// it will create all variables with private that is final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// a variable final is constant variable
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User create(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        roleRepository.findById(PredefineRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    // todo: chặn trước khi vào method
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        log.info("In method getAll");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getMyInfo() {
        // khi user đăng nhập thành công thì thông tin đăng nhập được lưu trong SecurityContextHolder
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        // chỉ đúng trong trường hợp username là duy nhất
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public UserResponse update(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        userMapper.updateUser(user, request);
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void delete(String userId) {
        userRepository.deleteById(userId);
    }

    // todo: vẫn vào method và chặn trước khi return giá trị
    // method chỉ chạy khi lấy thông tin của chính mình
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getOne(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!")));
    }

}
