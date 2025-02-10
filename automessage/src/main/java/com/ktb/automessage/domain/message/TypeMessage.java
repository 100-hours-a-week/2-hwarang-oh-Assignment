package com.ktb.automessage.domain.message;

import com.ktb.automessage.domain.user.KTBUser;

/**
 * TypeMessage Class는 Type ( 감사, 칭찬, 응원 )의 Message를 생성하는 역할을 합니다.
 * 
 * @see DefaultMessage
 */
public class TypeMessage extends DefaultMessage {
    // private final int type; // Deprecated
    private final MessageType type;

    /**
     * Default Constructor
     * 기본값으로 Type 0으로 생성 -> ( 의미 X, 사실상 오류 )
     * 
     * @param null
     */
    public TypeMessage() {
        super();
        this.type = MessageType.DEFAULT;
    }

    /**
     * Constructor ( No Type )
     * 기본값으로 Default로 생성 -> ( 의미 X -> Default Message와 동일 )
     * 
     * @param fromUser
     * @param targetUser
     */
    public TypeMessage(KTBUser fromUser, KTBUser targetUser) {
        super(fromUser, targetUser);
        this.type = MessageType.DEFAULT;
    }

    /**
     * Constructor ( With Type )
     * 
     * @param fromUser   : 보내는 User ( Myself )
     * @param targetUser : 받는 User
     * @param type       : Type ( 1 : 감사, 2 : 칭찬, 3 : 응원 )
     */
    public TypeMessage(KTBUser fromUser, KTBUser targetUser, MessageType type) {
        super(fromUser, targetUser);
        this.type = type;
    }

    /**
     * Type을 반환합니다.
     * 
     * @return Type ( MessageType )
     */
    public MessageType getType() {
        return this.type;
    }

    /**
     * Type Message를 생성합니다.
     * 
     * @apiNote Type 감사, Type 칭찬, Type 응원
     * @param type
     * @return Type Message ( String )
     */
    private String makeTypeMessage(MessageType type) {
        String targetName = super.targetUser.getEName();
        return switch (type) {
            case THANKS -> targetName + "님, 감사합니다 :)";
            case PRAISE -> targetName + "의 열정은 정말 대단한 것 같습니다!";
            case CHEER -> targetName + "님의 " + super.targetUser.getTrack() + "과정을 응원합니다!";
            default -> "";
        };
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
