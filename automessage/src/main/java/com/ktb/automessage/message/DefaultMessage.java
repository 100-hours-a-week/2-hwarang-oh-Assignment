package com.ktb.automessage.message;

import com.ktb.automessage.user.KTBUser;

/**
 * Default Message Class는 두 사용자 간의 기본 Message를 생성하는 역할을 합니다.
 */
public class DefaultMessage {
    protected KTBUser fromUser;
    protected KTBUser targetUser;

    /**
     * Default Constructor
     * 기본값으로 KTBUser Instance 생성 -> ( 의미 X, 사실상 오류 )
     * 
     * @param null
     */
    public DefaultMessage() {
        this.fromUser = new KTBUser();
        this.targetUser = new KTBUser();
    }

    /**
     * Constructor
     * 
     * @param fromUser   : 보내는 User ( Myself )
     * @param targetUser : 받는 User
     */
    public DefaultMessage(KTBUser fromUser, KTBUser targetUser) {
        this.fromUser = fromUser;
        this.targetUser = targetUser;
    }

    /**
     * Default Message를 생성합니다. ( private )
     * 
     * @return Default Message ( String )
     * @see KTBUser
     * @param null
     */
    private String makeDefaultMessage() {
        return this.targetUser.greet() + " " + this.fromUser.introduce();
    }

    /**
     * Default Message를 반환합니다.
     * 
     * @return Default Message ( String )
     * @param null
     */
    public String getMessage() {
        return makeDefaultMessage();
    }
}