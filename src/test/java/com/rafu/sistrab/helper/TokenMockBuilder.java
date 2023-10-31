package com.rafu.sistrab.helper;

import com.rafu.sistrab.rest.dto.TokenResponse;

public class TokenMockBuilder {
    public static TokenResponse getDefaultToken() {
        return TokenResponse.builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .duration("24h")
                .type("Bearer ")
                .build();
    }
}
