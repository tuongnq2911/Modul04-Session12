package example.milktea_shop.dto.response;

import java.time.LocalDateTime;

public class SuccessResponse<T> extends ApiResponse<T> {
    public SuccessResponse(String message, T data) {
        super("SUCCESS",message,data,null, LocalDateTime.now());
    }
}
