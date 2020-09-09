package ru.graduation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.graduation.util.exception.NotFoundException;
import ru.graduation.util.exception.VotingClosedException;

import javax.servlet.http.HttpServletRequest;

import static ru.graduation.util.ValidationUtil.getMessage;
import static ru.graduation.util.ValidationUtil.logAndGetRootCause;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionInfoHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception e, HttpServletRequest req) {
        Throwable rootCause = logAndGetRootCause(log, req, e, true);
        return new ResponseEntity<>(getMessage(rootCause), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VotingClosedException.class)
    public ResponseEntity<Object> handleVotingClosedException(Exception e, HttpServletRequest req) {
        Throwable rootCause = logAndGetRootCause(log, req, e, true);
        return new ResponseEntity<>(getMessage(rootCause), new HttpHeaders(), HttpStatus.LOCKED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> conflict(Exception e, HttpServletRequest req) {
        Throwable rootCause = logAndGetRootCause(log, req, e, false);
        return new ResponseEntity<>(getMessage(rootCause), new HttpHeaders(), HttpStatus.CONFLICT);
    }
}
