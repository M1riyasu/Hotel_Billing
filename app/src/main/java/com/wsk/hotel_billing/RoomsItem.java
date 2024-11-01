package com.wsk.hotel_billing;

import java.util.List;

public class RoomsItem {
    private String roomId;
    private String roomType;
    private String roomBedType;
    private String roomTotalNumberOfGuests;
    private List<String> roomFeatures;
    private String roomPriceForNight;

    public RoomsItem(String roomId, String roomType, String roomBedType, String roomTotalNumberOfGuests, List<String> roomFeatures, String roomPriceForNight) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomBedType = roomBedType;
        this.roomTotalNumberOfGuests = roomTotalNumberOfGuests;
        this.roomFeatures = roomFeatures;
        this.roomPriceForNight = roomPriceForNight;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomBedType() {
        return roomBedType;
    }

    public String getRoomTotalNumberOfGuests() {
        return roomTotalNumberOfGuests;
    }

    public List<String> getRoomFeatures() {
        return roomFeatures;
    }

    public String getRoomPriceForNight() {
        return roomPriceForNight;
    }
}
