package example.milktea_shop.dto.request.user;

import example.milktea_shop.enums.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequest {

    @NotNull(message = "Role không được để trống")
    private RoleName role;
}
