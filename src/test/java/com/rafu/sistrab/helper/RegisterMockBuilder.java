package com.rafu.sistrab.helper;

import com.rafu.sistrab.rest.dto.RegisterRequest;

public class RegisterMockBuilder {
    public static RegisterRequest getDefaultMock() {
        return RegisterRequest.builder()
                .name("DefaultName")
                .email("default@mail.com")
                .password("defaultPassword")
                .taxa("70.00")
                .horasMax("160")
                .build();
    }
}
