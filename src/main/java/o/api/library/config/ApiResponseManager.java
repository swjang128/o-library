package o.api.library.config;

import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiResponseManager {
    private int status;
    private String message;
    private Object data;

    @Description("Setting up API response without data")
    public static ApiResponseManager ok() {
        return ApiResponseManager.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @Description("Setting up API response with data")
    public static ApiResponseManager success(Object data) {
        return ApiResponseManager.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @Description("Setting up API response when error occurred")
    public static ApiResponseManager error(HttpStatus status, String message) {
        return ApiResponseManager.builder()
                .status(status.value())
                .message(message)
                .build();
    }
}