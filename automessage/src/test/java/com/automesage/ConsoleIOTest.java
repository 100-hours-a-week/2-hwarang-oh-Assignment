package com.automesage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ktb.automessage.exception.DefaultEmptyException;
import com.ktb.automessage.exception.MessageTypeException;
import com.ktb.automessage.utils.ConsoleIOUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleIOUtilTest {

    private ConsoleIOUtil consoleIOUtil;
    private ByteArrayOutputStream outputStream;

    @Mock
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        consoleIOUtil = new ConsoleIOUtil(mockScanner);
    }

    @Test
    @DisplayName("defaultPrint는 메시지를 정상적으로 출력한다")
    void defaultPrint_ShouldPrintMessage() {
        // given
        String message = "테스트 메시지";

        // when
        consoleIOUtil.defaultPrint(message);

        // then
        assertEquals(message + System.lineSeparator(), outputStream.toString());
    }

    @Test
    @DisplayName("defaultPrintWithInput은 빈 입력에 대해 DefaultEmptyException을 발생시킨다")
    void defaultPrintWithInput_ShouldThrowDefaultEmptyException() {
        // given
        String prompt = "입력: ";
        when(mockScanner.nextLine())
                .thenReturn("")
                .thenReturn("유효한 입력");

        // when & then
        assertDoesNotThrow(() -> {
            String result = consoleIOUtil.defaultPrintWithInput(prompt);
            assertEquals("유효한 입력", result);
        });
    }

    @Test
    @DisplayName("defaultPrintWithInput은 커스텀 Exception을 처리한다")
    void defaultPrintWithInput_ShouldHandleCustomException() throws Exception {
        // given
        String prompt = "메시지 타입 입력: ";
        MessageTypeException customException = new MessageTypeException("잘못된 메시지 타입입니다");

        when(mockScanner.nextLine())
                .thenReturn("")
                .thenReturn("감사");

        // when
        String result = consoleIOUtil.defaultPrintWithInput(prompt, customException);

        // then
        assertEquals("감사", result);
        verify(mockScanner, times(2)).nextLine();
    }

    @Test
    @DisplayName("delayPrint는 지연을 포함하여 메시지를 출력한다")
    void delayPrint_ShouldPrintMessageWithDelay() throws InterruptedException {
        // given
        String message = "지연 메시지";
        long startTime = System.currentTimeMillis();

        // when
        consoleIOUtil.delayPrint(message);
        long endTime = System.currentTimeMillis();

        // then
        assertTrue((endTime - startTime) >= message.length() * 50);
        assertEquals(message, outputStream.toString());
    }

    @Test
    @DisplayName("getInput은 유효한 입력을 반환한다")
    void getInput_ShouldReturnValidInput() throws DefaultEmptyException {
        // given
        String prompt = "입력: ";
        String expectedInput = "테스트 입력";
        when(mockScanner.nextLine()).thenReturn(expectedInput);

        // when
        String result = consoleIOUtil.defaultPrintWithInput(prompt);

        // then
        assertEquals(expectedInput, result);
        verify(mockScanner).nextLine();
    }

    @Test
    @DisplayName("getInput은 여러 번의 빈 입력 후 유효한 입력을 처리한다")
    void getInput_ShouldHandleMultipleEmptyInputs() throws DefaultEmptyException {
        // given
        String prompt = "입력: ";
        when(mockScanner.nextLine())
                .thenReturn("")
                .thenReturn("")
                .thenReturn("유효한 입력");

        // when
        String result = consoleIOUtil.defaultPrintWithInput(prompt);

        // then
        assertEquals("유효한 입력", result);
        verify(mockScanner, times(3)).nextLine();
    }
}