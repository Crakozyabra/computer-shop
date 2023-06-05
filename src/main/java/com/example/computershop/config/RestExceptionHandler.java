package com.example.computershop.config;

import com.example.computershop.error.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.computershop.error.ErrorType.*;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class RestExceptionHandler {
    public static final String ERR_PFX = "ERR# ";

    public static final String SERIAL_PRODUCER_UNIQUE_CONSTRAINT = "serial_producer_unique_constraint";

    public static final String UNIQUE_OR_PRIMARY_KEY_VIOLATION = "unique index or primary key violation";

    public static final String REFERENTIAL_INTEGRITY_CONSTRAINT_VIOLATION = "referential integrity constraint violation";

    @Getter
    private final MessageSource messageSource;

    // https://stackoverflow.com/a/52254601/548473
    static final Map<Class<? extends Throwable>, ErrorType> HTTP_STATUS_MAP = new LinkedHashMap<>() {
        {
            put(NotFoundException.class, NOT_FOUND);
            put(DataConflictException.class, DATA_CONFLICT);
            put(IllegalRequestDataException.class, BAD_REQUEST);
            put(AppException.class, APP_ERROR);
            put(EntityNotFoundException.class, DATA_CONFLICT);
            put(DataIntegrityViolationException.class, DATA_CONFLICT);
            put(IllegalArgumentException.class, BAD_DATA);
            put(NoHandlerFoundException.class, NOT_FOUND);
            put(BindException.class, BAD_REQUEST);
            put(ValidationException.class, BAD_REQUEST);
            put(HttpRequestMethodNotSupportedException.class, BAD_REQUEST);
            put(MissingServletRequestParameterException.class, BAD_REQUEST);
            put(HttpMessageNotReadableException.class, BAD_DATA);
        }
    };

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ProblemDetail notReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return processException(ex, request, Map.of());
    }

    @ExceptionHandler(BindException.class)
    ProblemDetail bindException(BindException ex, HttpServletRequest request) {
        return processException(ex, request, Map.of("invalid_params", getErrorMap(ex.getBindingResult())));
    }

    //  https://howtodoinjava.com/spring-mvc/spring-problemdetail-errorresponse/#5-adding-problemdetail-to-custom-exceptions
    @ExceptionHandler(Exception.class)
    ProblemDetail exception(Exception ex, HttpServletRequest request) {
        return processException(ex, request, Map.of());
    }


    ProblemDetail processException(@NonNull Exception ex, HttpServletRequest request,
                                   Map<String, Object> additionalParams) {
        String detail = getRootCause(ex).getMessage().toLowerCase();
        if (detail.contains(SERIAL_PRODUCER_UNIQUE_CONSTRAINT)) {
            detail = "Unique products serial number per producer constraint";
        }
        else if (detail.contains(UNIQUE_OR_PRIMARY_KEY_VIOLATION)) {
            detail = "Unique index or primary key violation";
        }
        else if (detail.contains(REFERENTIAL_INTEGRITY_CONSTRAINT_VIOLATION)) {
            detail = "Such producers id does not exists";
        }
        else {
            detail = getRootCause(ex).getMessage();
        }
        String path = request.getRequestURI();
        Class<? extends Exception> exClass = ex.getClass();
        Optional<ErrorType> optType = HTTP_STATUS_MAP.entrySet().stream()
                .filter(
                        entry -> entry.getKey().isAssignableFrom(exClass)
                )
                .findAny().map(Map.Entry::getValue);
        if (optType.isPresent()) {
            log.error(ERR_PFX + "Exception {} at request {}", ex, path);
            return createProblemDetail(ex, optType.get(), detail, additionalParams);
        } else {
            Throwable root = getRootCause(ex);
            log.error(ERR_PFX + "Exception " + root + " at request " + path, root);
            return createProblemDetail(ex, APP_ERROR, "Exception " + root.getClass().getSimpleName(),
                    additionalParams);
        }
    }

    private ProblemDetail createProblemDetail(Exception ex, ErrorType type, String defaultDetail,
                                              @NonNull Map<String, Object> additionalParams) {
        ErrorResponse.Builder builder = ErrorResponse.builder(ex, type.status, defaultDetail);
        ProblemDetail pd = builder.build().updateAndGetBody(messageSource, LocaleContextHolder.getLocale());
        additionalParams.forEach(pd::setProperty);
        return pd;
    }

    private Map<String, String> getErrorMap(BindingResult result) {
        Map<String, String> invalidParams = new LinkedHashMap<>();
        for (ObjectError error : result.getGlobalErrors()) {
            invalidParams.put(error.getObjectName(), getErrorMessage(error));
        }
        for (FieldError error : result.getFieldErrors()) {
            invalidParams.put(error.getField(), getErrorMessage(error));
        }
        log.warn("BindingException: {}", invalidParams);
        return invalidParams;
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error.getCode(), error.getArguments(), error.getDefaultMessage(),
                LocaleContextHolder.getLocale());
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    private static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}