package org.activitat17.demo;

public class Booking {
    private String locationNumber;
    private String clientId;
    private String clientName;
    private String agencyId;
    private String agencyName;
    private double price;
    private String roomType;
    private String hotelId;
    private String hotelName;
    private String checkInDate;
    private int roomNights;
    public Booking() {
    }
    public Booking(String locationNumber, String clientId, String clientName, String agencyId, String agencyName, double price, String roomType, String hotelId, String hotelName, String checkInDate, int roomNights) {
        this.locationNumber = locationNumber;
        this.clientId = clientId;
        this.clientName = clientName;
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.price = price;
        this.roomType = roomType;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.checkInDate = checkInDate;
        this.roomNights = roomNights;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public int getRoomNights() {
        return roomNights;
    }

    public void setRoomNights(int roomNights) {
        this.roomNights = roomNights;
    }
}
