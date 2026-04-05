package example.milktea_shop.dto.response;

public final class ResponseFactory {
    private ResponseFactory() {

    }
    public static <T> SuccessResponse<T> success(String message,T data) {
        return new SuccessResponse<>(message,data);
    }
    public static SuccessResponse<Object> success(String message) {
        return new SuccessResponse<>(message,null);
    }
    public static ErrorResponse error(String message,Object error) {
        return new ErrorResponse(message,error);
    }
    public static ErrorResponse error(String message) {
        return new ErrorResponse(message,null);
    }
}

