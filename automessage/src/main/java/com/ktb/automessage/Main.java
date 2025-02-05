package com.ktb.automessage;

import com.ktb.automessage.exception.MemberNameException;
import com.ktb.automessage.listener.DiscordListener;
import com.ktb.automessage.utils.ContentsUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

/**
 * TODO : 현재 사용하는 JDABuilder는 무엇인가?
 * * Docs : https://jda.wiki/using-jda/getting-started/
 * * Bot Token : GitHub에 올리면 안되는 정보 ( 본인의 Bot Token을 사용해야 함 )
 */
public class Main {
        public static void main(String[] args) throws InterruptedException {

                // Discord Version (JDA)
                // String token = "Your Bot Token";
                // JDA jda =
                // JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT)
                // .addEventListeners(new DiscordListener()).build();

                // CommandListUpdateAction commands = jda.updateCommands();
                // commands.addCommands(Commands.slash("send", "Makes the bot say what you
                // Tell")
                // .addOption(OptionType.USER, "target", "보낼 사람", true)
                // .addOption(OptionType.INTEGER, "type", "1 : 감사, 2 : 칭찬, 3 : 응원", false)
                // .addOption(OptionType.STRING, "content", "보내고 싶은 추가 메시지", false));
                // commands.queue();

                // CLI Version
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