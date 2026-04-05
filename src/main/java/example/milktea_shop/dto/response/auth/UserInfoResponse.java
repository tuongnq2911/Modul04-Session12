package example.milktea_shop.dto.response.auth;

import example.milktea_shop.enums.RoleName;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserInfoResponse {
    private Long id;
    private String phone;
    private String email;
    private RoleName role;
}
