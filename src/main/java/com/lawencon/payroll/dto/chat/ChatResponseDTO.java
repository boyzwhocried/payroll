package com.lawencon.payroll.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponseDTO {
    private String id;
    private String message;
    private String recipientId;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;
    private Integer version;
    private Boolean isActive;
}
