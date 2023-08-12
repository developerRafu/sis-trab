package com.rafu.sistrab.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String text;
    private String complemento;
    private List<String> details;
}
