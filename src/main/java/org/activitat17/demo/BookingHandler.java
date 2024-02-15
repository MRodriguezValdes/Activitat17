import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Vector;

public class BookingHandler extends DefaultHandler {
    private Vector<Booking> bookings = new Vector<>();
    private Booking currentBooking;
    private StringBuilder data;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("booking")) {
            String locationNumber = attributes.getValue("location_number");
            currentBooking = new Booking(locationNumber, null, null, null, null, 0.0, null, null, null, null, 0);
        } else if (qName.equalsIgnoreCase("client")) {
            String clientId = attributes.getValue("id_client");
            currentBooking.setClientId(clientId);
        } else if (qName.equalsIgnoreCase("agency")) {
            String agencyId = attributes.getValue("id_agency");
            currentBooking.setAgencyId(agencyId);
        } else if (qName.equalsIgnoreCase("room")) {
            String roomType = attributes.getValue("id_type");
            currentBooking.setRoomType(roomType);
        } else if (qName.equalsIgnoreCase("hotel")) {
            String hotelId = attributes.getValue("id_hotel");
            currentBooking.setHotelId(hotelId);
        }
        data = new StringBuilder();
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length).trim());
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("client")) {
            currentBooking.setClientName(data.toString());
        } else if (qName.equalsIgnoreCase("agency")) {
            currentBooking.setAgencyName(data.toString());
        } else if (qName.equalsIgnoreCase("price")) {
            currentBooking.setPrice(Double.parseDouble(data.toString().replace(",",".")));
        } else if (qName.equalsIgnoreCase("room")) {
            currentBooking.setRoomType(data.toString());
        } else if (qName.equalsIgnoreCase("hotel")) {
            currentBooking.setHotelName(data.toString());
        } else if (qName.equalsIgnoreCase("check_in")) {
            currentBooking.setCheckInDate(data.toString());
        } else if (qName.equalsIgnoreCase("room_nights")) {
            currentBooking.setRoomNights(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("booking")) {
            // Add the completed Booking object to the Vector
            bookings.add(currentBooking);
        }
    }

    public Vector<Booking> getBookings() {
        return bookings;
    }
}
