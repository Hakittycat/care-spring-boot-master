package in.joshbetz.careproject.advice;

import in.joshbetz.careproject.exception.RequestException;
import in.joshbetz.careproject.request.MessageResponse;
import in.joshbetz.careproject.request.RequestExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RequestExceptionAdvice {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RequestExceptionResponse handleRequestException(RequestException e) {
        return new RequestExceptionResponse(e.getMessage());
    }
}
