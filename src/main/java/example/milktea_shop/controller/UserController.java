package example.milktea_shop.controller;

import example.milktea_shop.dto.request.user.UpdateUserRoleRequest;
import example.milktea_shop.dto.response.ApiResponse;
import example.milktea_shop.dto.response.ResponseFactory;
import example.milktea_shop.dto.response.user.UserProfileResponse;
import example.milktea_shop.security.user.CustomUserDetails;
import example.milktea_shop.service.serviceinterface.UserService;
import example.milktea_shop.util.MessageConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRoleRequest request) {
        UserProfileResponse response = userService.updateUserRole(id, request);
        return ResponseEntity.ok(ResponseFactory.success(MessageConstant.UPDATE_SUCCESS, response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUserProfile(
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        UserProfileResponse response = userService.getCurrentUserProfile(currentUser.getUser().getId());
        return ResponseEntity.ok(ResponseFactory.success(MessageConstant.GET_SUCCESS, response));
    }
}
