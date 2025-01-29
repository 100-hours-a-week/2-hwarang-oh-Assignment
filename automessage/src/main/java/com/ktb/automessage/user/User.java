package com.ktb.automessage.user;

/**
 * IMP : 일반적인 User의 정보를 담는 Class
 * * kName : 한글 이름
 * ! User Class는 Discrod JDA의 User Class와 구분하기 위해 KTBUser로 extends 예정
 */
public class User {
    String kName;

    public User() {
        this.kName = "Unknown";
    }

    public User(String kName) {
        this.kName = kName;
    }

    @Override
    public String toString() {
        return this.kName;
    }

    public String getKName() {
        return this.kName;
    }
}
