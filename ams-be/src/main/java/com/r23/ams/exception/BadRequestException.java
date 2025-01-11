package com.r23.ams.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

    @Data
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1L;
        private int errorCode;
        public static final int DEFAULT_CODE = 10200;
        public static final int EXIST_IDENTIFY = 10201;
        public BadRequestException(int errorCode, String message) {
            super(message);
            this.errorCode = errorCode;
        }
        public BadRequestException(String message) {
            super(message);
            this.errorCode = DEFAULT_CODE;
        }
    }
