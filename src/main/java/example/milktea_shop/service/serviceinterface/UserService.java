package example.milktea_shop.service.serviceinterface;

import example.milktea_shop.dto.request.user.UpdateUserRoleRequest;
import example.milktea_shop.dto.response.user.UserProfileResponse;

public interface UserService {

    UserProfileResponse updateUserRole(Long id, UpdateUserRoleRequest request);

    UserProfileResponse getCurrentUserProfile(Long currentUserId);
}
