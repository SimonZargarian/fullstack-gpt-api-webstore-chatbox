package com.kokak.webstoregpttest.controller;

import com.kokak.webstoregpttest.open_ai.OpenAIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private OpenAIServiceImpl openAIServiceImpl;

    @PostMapping("/chat")
    public String chatWithOpenAI(@RequestBody Map<String, String> request) {
        String userInput = request.get("message");
       // return null;
        return openAIServiceImpl.generateProductRelatedResponse(userInput);
    }

}
