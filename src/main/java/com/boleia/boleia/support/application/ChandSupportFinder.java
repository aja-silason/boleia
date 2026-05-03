package com.boleia.boleia.support.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportGateway;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportNotFoundError;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportOutput;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChandSupportFinder {
    private final ChatSupportGateway gateway;

    public Result<List<ChatSupportOutput>, Void> findAll(){
        return this.gateway.findAll();
    }

    public Result<ChatSupportOutput, ChatSupportNotFoundError> findById(String id){
        return this.gateway.findById(id);
    }

}
