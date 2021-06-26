package com.url.cutter.exceptions;

import org.springframework.http.HttpStatus;

public class UrlIsNotValid extends RuntimeException {

    private final static int codeStatus = HttpStatus.NOT_FOUND.value();

    private final static String codeMessage = "That Url doesn't exists.";

    public static Integer getCodeStatus() {
        return codeStatus;
    }

    public static String getCodeMessage() {
        return codeMessage;
    }

}
