package com.shp.fms.chat.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.shp.fms.chat.model.ChatMessage.MessageType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ChatUserStatus implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    
    private MessageType status; 		// 채팅방 접속 상태 // TODO 240328 다른 enum으로 변경?
    private LocalDateTime lastEnter;	// 채팅방 마지막 접속 시간
    
    public ChatUserStatus() {
    	this.status = MessageType.JOIN;
    	this.lastEnter = LocalDateTime.now();
    }
    
    public void updateStatus(MessageType type) {
    	this.status = type;
    	this.lastEnter = LocalDateTime.now();
    }
   
}
