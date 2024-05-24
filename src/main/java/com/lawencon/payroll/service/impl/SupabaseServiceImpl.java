package com.lawencon.payroll.service.impl;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.payroll.model.Chat;
import com.lawencon.payroll.service.SupabaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupabaseServiceImpl implements SupabaseService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String SUPABASE_URL = "https://sstqmskfzianclywiuwu.supabase.co";
    // private final String SUPABASE_KEY = "your-supabase-key";

    @Override
    public void notifySupabase(Chat chat) {
        URI uri = URI.create(SUPABASE_URL + "/chats");
        restTemplate.postForEntity(uri, chat, Chat.class);
    }
}
