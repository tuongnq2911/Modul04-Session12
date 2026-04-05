package example.milktea_shop.service.serviceimplenment;

import example.milktea_shop.dto.request.user.UpdateUserRoleRequest;
import example.milktea_shop.dto.response.user.UserProfileResponse;
import example.milktea_shop.entity.User;
import example.milktea_shop.enums.RoleName;
import example.milktea_shop.exception.BadRequestException;
import example.milktea_shop.exception.ResourceNotFoundException;
import example.milktea_shop.repository.UserRepository;
import example.milktea_shop.service.serviceinterface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserProfileResponse updateUserRole(Long id, UpdateUserRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        if (request.getRole() == RoleName.ROLE_ADMIN) {
            throw new BadRequestException("Bài tập này không cho phép nâng quyền trực tiếp thành ADMIN qua API");
        }

        user.setRole(request.getRole());
        return toUserProfileResponse(userRepository.save(user));
    }

    @Override
    public UserProfileResponse getCurrentUserProfile(Long currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));
        return toUserProfileResponse(user);
    }

    private UserProfileResponse toUserProfileResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .build();
    }
}
