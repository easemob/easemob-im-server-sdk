package com.easemob.im.server.api.message.missed;

import java.util.Objects;

public class MissedMessageCount {

    private final String queueName;

    private final int messageCount;

    public MissedMessageCount(String queueName, int messageCount) {
        this.queueName = queueName;
        this.messageCount = messageCount;
    }

    public String getQueueName() {
        return queueName;
    }

    public int getMessageCount() {
        return messageCount;
    }

    @Override
    public String toString() {
        return "MissedMessageCount{" +
            "queueName='" + queueName + '\'' +
            ", messageCount=" + messageCount +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MissedMessageCount)) {
            return false;
        }
        MissedMessageCount count = (MissedMessageCount) o;
        return messageCount == count.messageCount &&
            queueName.equals(count.queueName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queueName, messageCount);
    }

}