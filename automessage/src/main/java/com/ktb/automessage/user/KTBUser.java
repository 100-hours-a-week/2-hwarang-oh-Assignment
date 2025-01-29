package com.ktb.automessage.user;

/**
 * IMP : Auto Message Bot의 User에 해당하는 Class
 * * kName : 한글 이름 (super), eName : 영어 이름, track : 트랙 과정
 * ! KTB User Class를 통해, Discord의 User Class와 구분
 */
public class KTBUser extends User {
    private static final String UNKNOWN = "Unknown";
    String eName;
    String track;

    public KTBUser() {
        super();
        this.eName = UNKNOWN;
        this.track = UNKNOWN;
    }

    public KTBUser(String kName) {
        super(kName);
        this.eName = UNKNOWN;
        this.track = UNKNOWN;
    }

    public KTBUser(String kName, String eName, String track) {
        super(kName);
        this.eName = eName;
        this.track = track;
    }

    @Override
    public String toString() {
        return this.eName;
    }

    public String getEName() {
        return this.eName;
    }

    public String getTrack() {
        return this.track;
    }

    public String greet() {
        return "안녕하세요, " + this.eName + " :)";
    }

    public String introduce() {
        return "저는 " + this.track + "과정의 " + this.eName + "/" + this.kName + " 입니다.";
    }
}
