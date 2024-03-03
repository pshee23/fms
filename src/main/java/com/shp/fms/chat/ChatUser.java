package com.shp.fms.chat;

import java.io.Serializable;

import com.shp.fms.chat.ChatMessage.MessageType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ChatUser implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;
    
//    private String name;
    
    private String id;
    
    private String deviceToken;
    
    private MessageType status;
}
