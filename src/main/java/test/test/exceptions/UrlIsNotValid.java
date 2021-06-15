package test.test.exceptions;

import org.springframework.http.HttpStatus;

public class UrlIsNotValid extends RuntimeException {
    private final Integer codeStatus = HttpStatus.NOT_FOUND.value();

    public Integer getCodeStatus() {
        return codeStatus;
    }
}
