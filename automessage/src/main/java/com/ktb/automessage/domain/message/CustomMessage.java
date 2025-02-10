package com.ktb.automessage.domain.message;

import com.ktb.automessage.domain.user.KTBUser;

/**
 * CustomMessage Class는 Custom Message를 생성하는 역할을 합니다.
 * 
 * @see TypeMessage
 * @see DefaultMessage
 */
public class CustomMessage extends TypeMessage {
    private final String customContent;

    /**
     * Default Constructor
     * super() -> User가 없고 Type 0인 TypeMessage 생성 -> User가 없는 DefaultMessage 생성 ->
     * 의미 X
     * 
     * @param null
     * @see TypeMessage
     * @see DefaultMessage
     */
    public CustomMessage() {
        super();
        this.customContent = "";
    }

    /**
     * Constructor ( No Type )
     * super(fromUser, targetUser) -> Type 0인 TypeMessage 생성 -> DefaultMessage 생성
     * customContent가 없기 때문에, customContent는 ""로 초기화
     * 
     * @param fromUser   : 보내는 User ( Myself )
     * @param targetUser : 받는 User
     */
    public CustomMessage(KTBUser fromUser, KTBUser targetUser) {
        super(fromUser, targetUser);
        this.customContent = "";
    }

    /**
     * Constructor ( With Type, No Custom Content )
     * super(fromUser, targetUser, type) -> Type이 있는 TypeMessage 생성
     * customContent가 없기 때문에, customContent는 ""로 초기화
     * 
     * @param fromUser   : 보내는 User ( Myself )
     * @param targetUser : 받는 User
     * @param type       : Type ( 1 : 감사, 2 : 칭찬, 3 : 응원 )
     */
    public CustomMessage(KTBUser fromUser, KTBUser targetUser, MessageType type) {
        super(fromUser, targetUser, type);
        this.customContent = "";
    }

    /**
     * Constructor ( With Type, Custom Content )
     * super(fromUser, targetUser, type) -> Type이 있는 TypeMessage 생성
     * customContent가 있기 때문에, customContent로 초기화
     * 
     * @param fromUser      : 보내는 User ( Myself )
     * @param targetUser    : 받는 User
     * @param type          : Type ( 1 : 감사, 2 : 칭찬, 3 : 응원 )
     * @param customContent : Custom Content
     */
    public CustomMessage(KTBUser fromUser, KTBUser targetUser, MessageType type, String customContent) {
        super(fromUser, targetUser, type);
        this.customContent = customContent;
    }

    @Override
    public MessageType getType() {
        return super.getType();
    }

    /**
     * Custom Message를 생성합니다.
     * 
     * @apiNote Type Message + Custom Content
     * @return Custom Message ( String )
     */
    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + this.customContent;
    }
}
