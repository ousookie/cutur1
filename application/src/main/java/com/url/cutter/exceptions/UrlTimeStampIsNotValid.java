package com.url.cutter.exceptions;

import org.springframework.http.HttpStatus;

public class UrlTimeStampIsNotValid extends RuntimeException {

    private final static Integer codeStatus = HttpStatus.NOT_FOUND.value();

    public static Integer getCodeStatus() {
        return codeStatus;
    }

}
