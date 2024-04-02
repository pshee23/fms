package com.shp.fms.chat.model.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CreateChatRoomRequest implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    
    private String employeeId;
    private String memberId;
}
