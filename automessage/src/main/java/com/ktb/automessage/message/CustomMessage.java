package com.ktb.automessage.message;

import com.ktb.automessage.user.KTBUser;

public class CustomMessage extends TypeMessage {
    String customContent;

    public CustomMessage() {
        super();
        this.customContent = "";
    }

    public CustomMessage(KTBUser fromUser, KTBUser targetUser) {
        super(fromUser, targetUser);
        this.customContent = "";
    }

    public CustomMessage(KTBUser fromUser, KTBUser targetUser, int type) {
        super(fromUser, targetUser, type);
        this.customContent = "";
    }

    public CustomMessage(KTBUser fromUser, KTBUser targetUser, int type, String customContent) {
        super(fromUser, targetUser, type);
        this.customContent = customContent;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + this.customContent;
    }
}
