package example.milktea_shop.controller;

import example.milktea_shop.dto.request.auth.LoginRequest;
import example.milktea_shop.dto.request.auth.RegisterRequest;
import example.milktea_shop.dto.response.ApiResponse;
import example.milktea_shop.dto.response.ResponseFactory;
import example.milktea_shop.dto.response.auth.LoginResponse;
import example.milktea_shop.service.serviceinterface.AuthService;
import example.milktea_shop.util.MessageConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseFactory.success(MessageConstant.REGISTER_SUCCESS));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ResponseFactory.success(MessageConstant.LOGIN_SUCCESS, response));
    }
}
