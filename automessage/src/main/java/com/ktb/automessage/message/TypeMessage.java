package com.ktb.automessage.message;

import com.ktb.automessage.user.KTBUser;

public class TypeMessage extends DefaultMessage {
    int type;

    public TypeMessage() {
        super();
        this.type = 0;
    }

    public TypeMessage(KTBUser fromUser, KTBUser targetUser) {
        super(fromUser, targetUser);
        this.type = 0;
    }

    public TypeMessage(KTBUser fromUser, KTBUser targetUser, int type) {
        super(fromUser, targetUser);
        this.type = type;
    }

    /**
     * @param type 1: 감사, 2: 칭찬, 3: 응원
     * @return Type Message ( String )
     */
    public String getTypeContent(int type) {
        switch (type) {
            case 1:
                return super.targetUser.getEName() + "님, 감사합니다 :)";
            case 2:
                return super.targetUser.getEName() + "의 열정은 정말 대단한 것 같습니다다!";
            case 3:
                return super.targetUser.getEName() + "님의 " + super.targetUser.getTrack() + "과정을 응원합니다!";
            default:
                return "";
        }
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + getTypeContent(this.type);
    }
}
