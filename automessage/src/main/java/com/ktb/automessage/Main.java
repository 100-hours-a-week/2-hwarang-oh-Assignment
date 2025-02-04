package com.ktb.automessage;

import com.ktb.automessage.exception.MemberNameException;
import com.ktb.automessage.utils.ContentsUtil;

/**
 * TODO : 현재 사용하는 JDABuilder는 무엇인가?
 * * Docs : https://jda.wiki/using-jda/getting-started/
 * * Bot Token : GitHub에 올리면 안되는 정보 ( 본인의 Bot Token을 사용해야 함 )
 */
public class Main {
        public static void main(String[] args) throws InterruptedException, MemberNameException {

                // CLI Version
                ContentsUtil contentProcess = new ContentsUtil();

                boolean isStart = contentProcess.startProcess();
                if (!isStart) return;
                boolean isLogin = contentProcess.loginProcess();
                if (!isLogin) return;
                contentProcess.welcomeMessage();
                contentProcess.InfoMessage();
                contentProcess.helpMessage();
                contentProcess.defaultProcess();
        }
}