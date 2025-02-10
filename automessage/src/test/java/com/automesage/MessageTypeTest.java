package com.automesage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktb.automessage.domain.message.MessageType;

class MessageTypeTest {

    @Test
    @DisplayName("MessageType Enum 테스트")
    void 키워드로_검색하면_올바른_타입을_반환한다() {
        // given
        String thanksKeyword = "감사";
        String praiseKeyword = "칭찬";
        String cheerKeyword = "응원";

        // when
        assertEquals(MessageType.THANKS, MessageType.fromKeyword(thanksKeyword));
        assertEquals(MessageType.PRAISE, MessageType.fromKeyword(praiseKeyword));
        assertEquals(MessageType.CHEER, MessageType.fromKeyword(cheerKeyword));
    }
}
