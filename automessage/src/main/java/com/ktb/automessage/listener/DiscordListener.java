package com.ktb.automessage.listener;

import com.ktb.automessage.user.KTBUser;
import com.ktb.automessage.message.TypeMessage;
import com.ktb.automessage.message.CustomMessage;
import com.ktb.automessage.message.DefaultMessage;
import com.ktb.automessage.utils.MemberUtil;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * DiscordListener Class는 JDA의 ListenerAdapter를 상속받아, Discord의 이벤트를 처리하는 Class
 */
public class DiscordListener extends ListenerAdapter {

    /**
     * onMessageReceived : Discord Server의 메시지 이벤트를 처리하는 Method
     * 
     * @apiNote : !help 명령어를 입력하면, KTB Message Bot을 메시지를 전송하는 방법을 설명하는 메시지를 출력
     * @param event
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (content.equals("!help")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("KTB Message Bot을 통해 동료에게 메시지를 전송해보세요!").queue();
        }
    }

    /**
     * * target -> "required = true" => event의 fromUser과 targetUser가 null인 경우 발생 X
     * IMP : Discord는 자체적으로 일단 유효성 처리를 해주긴 함.
     */

    /**
     * onSlashCommandInteraction : 슬래시 명령어 이벤트를 처리하는 Method
     * 
     * @apiNote : send 명령어를 입력하면, 해당 명령어에 대한 처리를 진행
     * @param event
     * @TODO : Discord는 자체적으로 유효성 처리를 해주기 때문에, null인 경우와 TypeError 발생하지 않음 => CLI
     *       Version에서는 고려해야 함
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("send")) {
            DefaultMessage targetMessage;

            int type = event.getOption("type") == null ? 0 : event.getOption("type").getAsInt();
            String content = event.getOption("content") == null ? "" : event.getOption("content").getAsString();

            // target은 required = true로 설정되어 있어 null이 아님
            Member target = event.getOption("target").getAsMember();
            // KTBUser 객체 생성
            String[] fromUserData = MemberUtil.parseName(event.getMember().getNickname());
            String[] targetUserData = MemberUtil.parseName(target.getNickname());
            KTBUser fromUser = new KTBUser(fromUserData[1], fromUserData[0], fromUserData[2]);
            KTBUser targetUser = new KTBUser(targetUserData[1], targetUserData[0], targetUserData[2]);

            /**
             * * Case 1 : content가 input으로 들어올 때 -> type이 null인 경우 ( Error )
             * * Case 2 : content가 input으로 들어올 때 -> type이 null이 아닌 경우 ( Success )
             * TODO : type의 범위가 1 ~ 3인지 확인하는 Logic이 요구됨 -> 현재는 빈칸으로 처리되고 있다.
             */
            if (event.getOption("content") != null) {
                if (event.getOption("type") == null) {
                    event.reply("내용을 입력하려면, 메시지의 타입을 입력해주세요. 1 : 감사, 2 : 칭찬, 3 : 응원").setEphemeral(true).queue();
                    return;
                } else
                    targetMessage = new CustomMessage(fromUser, targetUser, type, content);
            } else {
                if (event.getOption("type") == null)
                    targetMessage = new DefaultMessage(fromUser, targetUser);
                else
                    targetMessage = new TypeMessage(fromUser, targetUser, type);
            }

            // Direct Message 전송
            target.getUser().openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage(targetMessage.getMessage()))
                    .queue(
                            success -> event.reply("DM을 성공적으로 보냈습니다!").setEphemeral(true).queue(),
                            error -> event.reply("DM을 보낼 수 없습니다.").setEphemeral(true).queue());

        }
    }
}
