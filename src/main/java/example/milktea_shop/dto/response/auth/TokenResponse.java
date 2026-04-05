package example.milktea_shop.dto.response.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
