package net.vogeez.authorization.service.exception.handler;

import net.vogeez.authorization.service.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * This {@link ControllerAdvice} class handles all exceptions that are thrown in the application.
 * It returns a {@link Map} with the error message and the status code. The status code is set
 * with the {@link ResponseStatus} annotation. If no {@link ResponseStatus} is set, the status
 * code will be 500. The {@link ExceptionHandler} annotation is used to specify which exception
 * should be handled by this class. If no {@link ExceptionHandler} is set, the exception will
 * be handled by the default exception handler.
 *
 * @see ControllerAdvice
 * @see ExceptionHandler
 * @see ResponseStatus
 *
 * @author : Niklas Tat
 * @since : 0.5
 */
@ControllerAdvice
public class ExceptionHandling {

    /**
     * This method handles all {@link MethodArgumentNotValidException}s.
     * It returns a {@link Map} with the error message and the status code.
     * The status code is set with the {@link ResponseStatus} annotation.
     *
     * @see MethodArgumentNotValidException
     * @see ResponseStatus
     * @see Map
     * @see HttpStatus
     * @see ExceptionHandler
     *
     * @param exception The exception that was thrown.
     * @return A {@link Map} with the error message.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (prev, next) -> next));
    }

    /**
     * This method handles all {@link UserAlreadyExistsException}s.
     * It returns a {@link Map} with the error message and the status code.
     * The status code is set with the {@link ResponseStatus} annotation.
     *
     * @see UserAlreadyExistsException
     * @see ResponseStatus
     * @see Map
     * @see HttpStatus
     * @see ExceptionHandler
     *
     * @param exception The exception that was thrown.
     * @return A {@link Map} with the error message.
     */
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> userAlreadyExistsException(UserAlreadyExistsException exception) {
        return Map.of("errorMessage", exception.getMessage());
    }

    /**
     * This method handles all {@link AuthenticationException}s.
     * It returns a {@link Map} with the error message and the status code.
     * The status code is set with the {@link ResponseStatus} annotation.
     *
     * @see AuthenticationException
     * @see ResponseStatus
     * @see Map
     * @see HttpStatus
     * @see ExceptionHandler
     *
     * @param exception The exception that was thrown.
     * @return A {@link Map} with the error message.
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> authenticationException(AuthenticationException exception) {
        return Map.of("errorMessage", exception.getMessage());
    }
}
