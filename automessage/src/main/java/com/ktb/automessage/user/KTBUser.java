package com.ktb.automessage.user;

/**
 * KTBUser Class는 User의 정보를 담는 Class입니다.
 * 
 * @see User
 */
public class KTBUser extends User {
    private static final String UNKNOWN = "Unknown";
    private String eName;
    private String track;

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

    public void setKName(String kName) {
        super.setKName(kName);
    }

    public void setEName(String eName) {
        this.eName = eName;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getEName() {
        return this.eName;
    }

    public String getTrack() {
        return this.track;
    }

    @Override
    public String toString() {
        return this.eName + "(" + super.getKName() + ")/" + this.track;
    }

    public String greet() {
        return "안녕하세요, " + this.eName + " :)";
    }

    public String introduce() {
        return "저는 " + this.track + "과정의 " + this.eName + "/" + super.getKName() + " 입니다.";
    }
}
