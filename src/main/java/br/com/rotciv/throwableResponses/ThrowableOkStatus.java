package br.com.rotciv.throwableResponses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class ThrowableOkStatus extends RuntimeException {
    public ThrowableOkStatus(String message) {
        super(message);
    }
}
