package example.milktea_shop.dto.response.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserInfoResponse user;
}
