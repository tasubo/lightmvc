package com.github.tasubo.lightmvc.messages;

class SimpleMessage implements Message {

    private Type type;
    private String content;

    public SimpleMessage(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return content;
    }
}
