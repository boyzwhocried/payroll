package com.lawencon.payroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lawencon.payroll.dto.chat.ChatRequestDTO;
import com.lawencon.payroll.dto.chat.ChatResponseDTO;
import com.lawencon.payroll.model.Chat;
import com.lawencon.payroll.model.User;
import com.lawencon.payroll.repository.ChatRepository;
import com.lawencon.payroll.repository.UserRepository;
import com.lawencon.payroll.service.SupabaseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupabaseService supabaseService;

    @GetMapping
    public List<ChatResponseDTO> getAllChats() {
        return chatRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    @PostMapping
    public ChatResponseDTO postChat(@RequestBody ChatRequestDTO chatRequestDTO) {
        Chat chat = convertToEntity(chatRequestDTO);
        Chat savedChat = chatRepository.save(chat);
        supabaseService.notifySupabase(savedChat);
        return convertToResponseDTO(savedChat);
    }

    private ChatResponseDTO convertToResponseDTO(Chat chat) {
        ChatResponseDTO dto = new ChatResponseDTO();
        dto.setId(chat.getId().toString());
        dto.setMessage(chat.getMessage());
        dto.setRecipientId(chat.getRecipientId().getId().toString());
        dto.setCreatedBy(chat.getCreatedBy());
        dto.setCreatedAt(chat.getCreatedAt().toString());
        dto.setUpdatedBy(chat.getUpdatedBy());
        dto.setUpdatedAt(chat.getUpdatedAt().toString());
        dto.setVersion(chat.getVer());
        dto.setIsActive(chat.getIsActive());
        return dto;
    }

    private Chat convertToEntity(ChatRequestDTO chatRequestDTO) {
        Chat chat = new Chat();
        chat.setMessage(chatRequestDTO.getMessage());
        User recipient = userRepository.findById(chatRequestDTO.getRecipientId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        chat.setRecipientId(recipient);
        return chat;
    }
}
