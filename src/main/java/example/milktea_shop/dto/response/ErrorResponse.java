package example.milktea_shop.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse extends ApiResponse<Object> {
    public ErrorResponse(String message, Object error) {
        super("ERROR",message,null,error, LocalDateTime.now());
    }
}
