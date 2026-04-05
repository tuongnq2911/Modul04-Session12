package example.milktea_shop.service.serviceinterface;

import example.milktea_shop.dto.request.auth.LoginRequest;
import example.milktea_shop.dto.request.auth.RegisterRequest;
import example.milktea_shop.dto.response.auth.LoginResponse;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
