package com.ktb.automessage.domain.user;

/**
 * User Class는 User의 정보를 담는 Class입니다.
 * 
 * @apiNote User Class는 JDA의 User Class와 구분하기 위해 KTBUser로 extends 후 사용
 */
public class User {
    private String kName;

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

    public void setKName(String kName) {
        this.kName = kName;
    }

    public String getKName() {
        return this.kName;
    }
}
