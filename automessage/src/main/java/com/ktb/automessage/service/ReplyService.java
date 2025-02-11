package com.ktb.automessage.service;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Queue;
import java.util.Random;

import com.ktb.automessage.domain.reply.Reply;
import com.ktb.automessage.domain.user.KTBUser;

public class ReplyService {
    private final ReplyThread replyThread = new ReplyThread();
    private final Queue<Reply> replyQueue = new ConcurrentLinkedDeque<>();
    private final Random random = new Random();
    private final String[] replyTemplates = {
            "%s님의 메시지 잘 받았습니다! 감사합니다 😊",
            "안녕하세요 %s님! 메시지 확인했어요~",
            "%s님, 좋은 하루 되세요! 메시지 감사합니다",
            "메시지 잘 읽었습니다, %s님! 다음에 또 연락해주세요"
    };

    public void addMessageToQueue(KTBUser from, KTBUser to, String message) {
        String replyMEssage = String.format(
                replyTemplates[random.nextInt(replyTemplates.length)],
                from.getKName());
        replyQueue.offer(new Reply(from, to, replyMEssage));
    }

    public void startReplyThread() {
        replyThread.start();
    }

    public void stopReplyThread() {
        replyThread.interrupt();
    }

    private class ReplyThread extends Thread {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(random.nextInt(4000) + 3000);
                    if (!replyQueue.isEmpty()) {
                        Reply reply = replyQueue.poll();
                        System.out.println("\n" + "━".repeat(50));
                        System.out.println("📨 새로운 답장이 도착했습니다!");
                        System.out.printf("FROM: %s\nTO: %s\nMESSAGE: %s\n", reply.getTo().getKName(),
                                reply.getFrom().getKName(), reply.getMessage());
                        System.out.println("━".repeat(50) + "\n");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
