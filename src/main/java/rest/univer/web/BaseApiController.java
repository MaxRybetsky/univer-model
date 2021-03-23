package rest.univer.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.exceptions.PersonIncorrectData;

public abstract class BaseApiController {
    @ExceptionHandler
    public ResponseEntity<PersonIncorrectData> handleIncorrectId(
            NoSuchPersonException exception
    ) {
        PersonIncorrectData data = new PersonIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PersonIncorrectData> handleIncorrectInput(
            MethodArgumentTypeMismatchException exception
    ) {
        PersonIncorrectData data = new PersonIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
