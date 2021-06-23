package com.easemob.im.server.model;

public class EMMetadataUsage {

    private Long bytesUsed;

    public EMMetadataUsage(Long bytesUsed) {
        this.bytesUsed = bytesUsed;
    }

    public Long getBytesUsed() {
        return bytesUsed;
    }

    @Override
    public String toString() {
        return "EMMetadataUsage{" +
                "bytesUsed=" + bytesUsed +
                '}';
    }
}
