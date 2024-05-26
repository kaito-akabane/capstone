package com.example.ProjectCC.controller;

import com.example.ProjectCC.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/chatting/saveMsg")
    @ResponseBody
    public void save(@RequestBody Map<String, String> map) {
        messageService.saveMsg(map.get("sender"), map.get("receiver"), map.get("content"));
    }
}