package example.milktea_shop.dto.response.user;

import example.milktea_shop.enums.RoleName;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserProfileResponse {
    private Long id;
    private String phone;
    private String email;
    private RoleName role;
    private Boolean enabled;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
