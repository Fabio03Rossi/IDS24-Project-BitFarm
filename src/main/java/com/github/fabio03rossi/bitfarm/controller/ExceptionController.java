package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.exception.AccessoNegatoException;
import com.github.fabio03rossi.bitfarm.exception.CarrelloVuotoException;
import com.github.fabio03rossi.bitfarm.exception.DatiNonTrovatiException;
import com.github.fabio03rossi.bitfarm.exception.ErroreInputDatiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = DatiNonTrovatiException.class)
    public ResponseEntity<Object> datiNonTrovati(DatiNonTrovatiException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ErroreInputDatiException.class)
    public ResponseEntity<Object> erroreInput(ErroreInputDatiException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessoNegatoException.class)
    public ResponseEntity<Object> accessoNegato(AccessoNegatoException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = CarrelloVuotoException.class)
    public ResponseEntity<Object> carrelloVuoto(CarrelloVuotoException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }
}
