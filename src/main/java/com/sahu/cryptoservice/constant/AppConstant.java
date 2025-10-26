package com.sahu.cryptoservice.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstant {
    // AES GCM standard IV length is 12 bytes
    public final int IV_LENGTH = 12;
    public final int GCM_TAG_LENGTH = 16;
    public final String AES_FORMAT = "%s#%s";

    //Error Messages
    public final String ALGORITHM_TYPE_MUST_NOT_BE_NULL = "Algorithm type must not be null";
}
