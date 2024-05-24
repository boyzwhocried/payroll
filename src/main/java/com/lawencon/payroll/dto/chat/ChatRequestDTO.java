package com.lawencon.payroll.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestDTO {
    private String message;
    private String recipientId;
}
