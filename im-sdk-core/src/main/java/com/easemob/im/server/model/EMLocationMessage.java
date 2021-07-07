package com.easemob.im.server.model;

import java.util.Objects;

public class EMLocationMessage extends EMMessage {

    private double longitude;

    private double latitude;

    private String address;

    public EMLocationMessage() {
        super(MessageType.LOCATION);
    }

    public double longitude() {
        return this.longitude;
    }

    public EMLocationMessage longitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public double latitude() {
        return this.latitude;
    }

    public EMLocationMessage latitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String address() {
        return this.address;
    }

    public EMLocationMessage address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        EMLocationMessage that = (EMLocationMessage) o;
        return Double.compare(that.longitude, longitude) == 0
                && Double.compare(that.latitude, latitude) == 0 && Objects
                .equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), longitude, latitude, address);
    }

    @Override
    public String toString() {
        return "EMLocationMessage{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                '}';
    }
}
