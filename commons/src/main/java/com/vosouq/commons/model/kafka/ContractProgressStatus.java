package com.vosouq.commons.model.kafka;

public enum ContractProgressStatus {
    STARTED,
    ONGOING,
    EXTENDED,
    DONE;

    public static ContractProgressStatus resolve(String status) {
        for (ContractProgressStatus value : values()) {
            if (value.name().equals(status))
                return value;
        }
        throw new IllegalArgumentException("There is no ContractProgressStatus by type: " + status + " !");
    }

    public boolean isStarted() {
        return this.equals(STARTED);
    }

    public boolean isOngoing() {
        return this.equals(ONGOING);
    }

    public boolean isExtended() {
        return this.equals(EXTENDED);
    }

    public boolean isDone() {
        return this.equals(DONE);
    }

}
