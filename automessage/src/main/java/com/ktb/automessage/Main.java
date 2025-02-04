package com.ktb.automessage;

import com.ktb.automessage.exception.MemberNameException;
import com.ktb.automessage.user.User;
import com.ktb.automessage.utils.ContentsUtil;
import com.ktb.automessage.utils.MemberUtil;
import com.ktb.automessage.utils.UserDataUtil;

import java.io.FileReader;
import java.util.Scanner;

/**
 * TODO : 현재 사용하는 JDABuilder는 무엇인가?
 * * Docs : https://jda.wiki/using-jda/getting-started/
 * * Bot Token : GitHub에 올리면 안되는 정보 ( 본인의 Bot Token을 사용해야 함 )
 */
public class Main {
        public static void main(String[] args) throws InterruptedException, MemberNameException {
                // String token =
                // "";
                // // Your Bot Token Here
                // JDA jda =
                // JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT)
                // .addEventListeners(new DiscordListener()).build();

                // Discord Bot Version
                // CommandListUpdateAction commands = jda.updateCommands();
                // commands.addCommands(
                // Commands.slash("send", "Makes the bot say what you Tell")
                // .addOption(OptionType.USER, "target", "보낼 사람", true)
                // .addOption(OptionType.INTEGER, "type", "1 : 감사, 2 : 칭찬, 3 : 응원", false)
                // .addOption(OptionType.STRING, "content", "보내고 싶은 추가 메시지", false));
                // commands.queue();

                // CLI Version
                // System.out.println(UserDataUtil.loadUserData());
                ContentsUtil contentProcess = new ContentsUtil();

                boolean isStart = contentProcess.startProcess();
                if (!isStart)
                        return;
                boolean isLogin = contentProcess.loginProcess();
                if (!isLogin)
                        return;
                contentProcess.welcomeMessage();
                contentProcess.InfoMessage();
                contentProcess.helpMessage();
                contentProcess.defaultProcess();
        }
}