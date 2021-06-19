package com.easemob.im.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TODO: Ken need a standard javadoc for this POJO
 * no disturb settings
 * startHour and endHour must be within [0, 23] range
 * if startHour < endHour, within the same day
 * if startHour > endHour, today and the next day
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
     * @param startHour 0-23
     * @param endHour 0-23
     * @throws IllegalArgumentException if startHour of endHour is out of 0-23 range
     */
    @JsonCreator
    public EMNotificationNoDisturbing(@JsonProperty("notification_no_disturbing") Boolean noDisturb,
                              @JsonProperty("notification_no_disturbing_start") Integer startHour,
                              @JsonProperty("notification_no_disturbing_end") Integer endHour) {
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
