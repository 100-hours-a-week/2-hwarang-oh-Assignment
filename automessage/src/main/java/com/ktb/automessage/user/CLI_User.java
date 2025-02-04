package com.ktb.automessage.user;

import com.ktb.automessage.utils.MemberUtil;

/**
 * CLI_User Class는 CLI에서 입력받은 Member를 KTBUser로 변환하는 역할을 하는 Class
 */
public class CLI_User {
    private final int id;
    private final String member;

    public CLI_User(int id, String member) {
        this.id = id;
        this.member = member;
    }

    public int getId() {
        return this.id;
    }

    public KTBUser toKTBUser() {
        String[] userData = MemberUtil.parseName(this.member);
        return new KTBUser(userData[1], userData[0], userData[2]);
    }
}
