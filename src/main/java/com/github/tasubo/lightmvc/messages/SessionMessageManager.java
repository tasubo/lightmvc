package com.github.tasubo.lightmvc.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.github.tasubo.lightmvc.messages.Message.Type;

public class SessionMessageManager implements MessageManager, Serializable {

    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    protected void clear() {
        messages.clear();
    }

    @Override
    public List<Message> getFlushMessages() {
        List<Message> msg =
                (List<Message>) ((ArrayList<Message>) getMessages()).clone();
        clear();
        return msg;
    }

    @Override
    public void addMessages(List<Message> messages) {
        for (Message message : messages) {
            this.messages.add(message);
        }

    }

    @Override
    public void addMessage(Type type, String content) {
        Message message = new SimpleMessage(type, content);
        addMessage(message);
    }
}
