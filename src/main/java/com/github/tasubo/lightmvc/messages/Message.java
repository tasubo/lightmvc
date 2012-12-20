package com.github.tasubo.lightmvc.messages;

public interface Message {

    public enum Type {

        ERROR, NOTICE, WARNING
    }

    String getContent();

    Type getType();
}
