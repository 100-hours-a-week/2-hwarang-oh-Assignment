package com.ktb.automessage.domain.reply;

import java.time.LocalDateTime;

import com.ktb.automessage.domain.user.KTBUser;

public class Reply {
    private final KTBUser from;
    private final KTBUser to;
    private final String message;
    private final LocalDateTime time;

    public Reply(KTBUser from, KTBUser to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public KTBUser getFrom() {
        return from;
    }

    public KTBUser getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
