package com.shp.fms.chat.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String title;
    
    private String toId;
    private String toName;
    
    private String fromId;
    private String fromName;
}
