package com.shp.fms.chat.model.request;

import com.shp.fms.chat.model.ChatMessage.MessageType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UpdateChatUserStatusRequest {
    private MessageType status; 		// 채팅방 접속 상태
    private String deviceToken;
}
