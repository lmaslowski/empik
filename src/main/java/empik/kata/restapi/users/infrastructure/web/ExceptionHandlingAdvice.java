package empik.kata.restapi.users.infrastructure.web;

import empik.kata.restapi.users.model.domain.UserNotFoundException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleDoesNotExist(Exception e) {
        return new ErrorDto.ErrorDtoBuilder().message(e.getMessage()).exception(e.getClass().getCanonicalName()).build();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handle(Exception e) {
        return new ErrorDto.ErrorDtoBuilder().message(e.getMessage()).exception(e.getClass().getCanonicalName()).build();
    }

    @Builder
    @Getter
    public static class ErrorDto {
        private final String exception;
        private final String message;
    }
}