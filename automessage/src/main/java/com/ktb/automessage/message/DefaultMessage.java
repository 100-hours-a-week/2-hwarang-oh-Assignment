package com.ktb.automessage.message;

import com.ktb.automessage.user.KTBUser;

public class DefaultMessage {
    protected KTBUser fromUser;
    protected KTBUser targetUser;

    public DefaultMessage() {
        this.fromUser = new KTBUser();
        this.targetUser = new KTBUser();
    }

    public DefaultMessage(KTBUser fromUser, KTBUser targetUser) {
        this.fromUser = fromUser;
        this.targetUser = targetUser;
    }

    /**
     * @param null
     * @return Default Message ( String )
     */
    public String getDefaultContent() {
        return this.targetUser.greet() + "\n" + this.fromUser.introduce();
    }

    public String getMessage() {
        return getDefaultContent();
    }
}