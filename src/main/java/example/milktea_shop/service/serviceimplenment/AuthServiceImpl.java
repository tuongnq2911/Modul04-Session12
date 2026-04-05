package example.milktea_shop.service.serviceimplenment;

import example.milktea_shop.dto.request.auth.LoginRequest;
import example.milktea_shop.dto.request.auth.RegisterRequest;
import example.milktea_shop.dto.response.auth.LoginResponse;
import example.milktea_shop.dto.response.auth.TokenResponse;
import example.milktea_shop.dto.response.auth.UserInfoResponse;
import example.milktea_shop.entity.User;
import example.milktea_shop.enums.RoleName;
import example.milktea_shop.exception.DuplicateResourceException;
import example.milktea_shop.repository.UserRepository;
import example.milktea_shop.security.jwt.JwtTokenProvider;
import example.milktea_shop.service.serviceinterface.AuthService;
import example.milktea_shop.util.MessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(MessageConstant.DUPLICATE_EMAIL);
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException(MessageConstant.DUPLICATE_PHONE);
        }

        User user = User.builder()
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleName.ROLE_CUSTOMER)
                .enabled(true)
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception ex) {
            throw new BadCredentialsException(MessageConstant.INVALID_CREDENTIALS);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException(MessageConstant.INVALID_CREDENTIALS));

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserInfoResponse.builder()
                        .id(user.getId())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .build();
    }
}
