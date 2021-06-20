package com.easemob.im.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A immutable representation of no disturb settings
 * startHour and endHour must be integers within [0,23] range
 * If noDisturb = true the user won't get notification during the [startHour,endHour] time interval
 * This time interval can be either within the same day or within 2 consecutive days
 */
public class EMNotificationNoDisturbing {
    @JsonProperty("notification_no_disturbing")
    private boolean noDisturb;

    @JsonProperty("notification_no_disturbing_start")
    private int startHour;

    @JsonProperty("notification_no_disturbing_end")
    private int endHour;

    /**
     *
     * @param noDisturb true if no-disturb is turned on, false by default
     * @param startHour must be within [0-23]
     * @param endHour must be within [0-23]
     * @throws IllegalArgumentException if either startHour or endHour is out of [0,23] range
     */
    @JsonCreator
    public EMNotificationNoDisturbing(@JsonProperty("notification_no_disturbing") Boolean noDisturb,
                              @JsonProperty("notification_no_disturbing_start") Integer startHour,
                              @JsonProperty("notification_no_disturbing_end") Integer endHour) {
        /*
        在REST API返回的json中, 这三个参数都可能为null
        这里依据im-push-service对null值的处理逻辑, 将其全部转换为基本类型
        从而使SDK的用户不需要再对null值做特殊处理
        */
        if (noDisturb != null) {
            this.noDisturb = noDisturb;
        } else {
            this.noDisturb = false;
        }
        if (startHour != null && (startHour > 23 || startHour < 0)) {
            throw new IllegalArgumentException(String.format("startHour = %d is out of valid 24 hours bound", startHour));
        }
        if (endHour != null && (endHour > 23 || endHour < 0)) {
            throw new IllegalArgumentException(String.format("endHour = %d is out of valid 24 hours bound", endHour));
        }
        if (startHour == null && endHour == null) {
            this.startHour = 0;
            this.endHour = 23;
        }
        else if (startHour != null && endHour != null) {
            this.startHour = startHour;
            this.endHour = endHour;
        }
        else if (startHour == null) {
            this.startHour = 0;
            this.endHour = endHour;
        }
        else if (endHour == null) {
            this.startHour = startHour;
            this.endHour = 23;
        }
    }

    public boolean getNoDisturb() {
        return noDisturb;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}
