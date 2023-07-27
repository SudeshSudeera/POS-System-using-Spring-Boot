package com.devstack.pos.adviser;

import com.devstack.pos.exception.DuplicateEntryException;
import com.devstack.pos.exception.EntryNotFoundException;
import com.devstack.pos.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWiderExceptionHandler {
    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponse> handleEntryNotFoundException(EntryNotFoundException e){
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(404,e.getMessage(),e), HttpStatus.NOT_FOUND
                );
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<StandardResponse> handleDuplicateEntryException(DuplicateEntryException e){
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(409,e.getMessage(),e), HttpStatus.CONFLICT
        );
    }
}
