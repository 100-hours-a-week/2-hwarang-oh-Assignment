package com.ktb.automessage.message;

import com.ktb.automessage.user.KTBUser;

/**
 * TypeMessage Class는 Type ( 감사, 칭찬, 응원 )의 Message를 생성하는 역할을 합니다.
 * 
 * @see DefaultMessage
 */
public class TypeMessage extends DefaultMessage {
    private final int type;

    /**
     * Default Constructor
     * 기본값으로 Type 0으로 생성 -> ( 의미 X, 사실상 오류 )
     * 
     * @param null
     */
    public TypeMessage() {
        super();
        this.type = 0;
    }

    /**
     * Constructor ( No Type )
     * 기본값으로 Type 0으로 생성 -> ( 의미 X -> Default Message와 동일 )
     * 
     * @param fromUser
     * @param targetUser
     */
    public TypeMessage(KTBUser fromUser, KTBUser targetUser) {
        super(fromUser, targetUser);
        this.type = 0;
    }

    /**
     * Constructor ( With Type )
     * 
     * @param fromUser   : 보내는 User ( Myself )
     * @param targetUser : 받는 User
     * @param type       : Type ( 1 : 감사, 2 : 칭찬, 3 : 응원 )
     */
    public TypeMessage(KTBUser fromUser, KTBUser targetUser, int type) {
        super(fromUser, targetUser);
        this.type = type;
    }

    /**
     * Type을 반환합니다.
     * @return Type ( int )
     */
    public int getType() {
        return this.type;
    }

    /**
     * Type Message를 생성합니다.
     * 
     * @apiNote Type 1 : 감사, Type 2 : 칭찬, Type 3 : 응원 ( Else : "" )
     * @param type
     * @return Type Message ( String )
     */
    private String makeTypeMessage(int type) {
        String targetName = super.targetUser.getEName();
        switch (type) {
            case 1:
                return targetName + "님, 감사합니다 :)";
            case 2:
                return targetName + "의 열정은 정말 대단한 것 같습니다!";
            case 3:
                return targetName + "님의 " + super.targetUser.getTrack() + "과정을 응원합니다!";
            default:
                return "";
        }
    }

    /**
     * Type Message를 반환합니다.
     * 
     * @apiNote Default Message + Type Message
     * @return Type Message ( String )
     */
    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + makeTypeMessage(this.type);
    }
}
