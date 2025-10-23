package com.sahu.cryptoservice.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AesConstant {
    public final String ALGORITHM = "AES/GCM/NoPadding";
    public final String ALGORITHM_INSTANCE = "AES";
    public final int AES_KEY_SIZE = 256;
    public final int GCM_IV_LENGTH = 12;
    public final int GCM_TAG_LENGTH = 16;
}
