package com.sahu.cryptoservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileConstant {
    ENCRYPTED_FILE_SUFFIX(".enc"),
    KEY_FILE_SUFFIX(".key"),
    GZIP_FILE_SUFFIX(".gz");

    private final String value;

    @Override
    public String toString() {
        return this.value;
    }

}
