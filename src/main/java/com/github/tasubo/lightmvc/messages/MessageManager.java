package com.github.tasubo.lightmvc.messages;

import java.util.List;

public interface MessageManager {

    void addMessage(Message.Type type, String content);

    void addMessages(List<Message> messages);

    List<Message> getMessages();

    List<Message> getFlushMessages();
}
