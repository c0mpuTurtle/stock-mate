package com.emotionBot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyboardYn {
    YES("Y"),
    NO("N");
    private final String YN;
}
