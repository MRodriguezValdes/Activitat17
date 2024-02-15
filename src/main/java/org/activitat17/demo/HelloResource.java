package org.activitat17.demo;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Path("/bookings-data")
public class HelloResource {
    private static List<Booking> bookings;
    private static File xmlFile;

    static {
        init();
    }

    public static void init() {
        try {
            xmlFile = new File("bookings.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            BookingHandler bookingHandler = new BookingHandler();
            saxParser.parse(xmlFile, bookingHandler);
            bookings = bookingHandler.getBookings();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(); // Maneja las excepciones adecuadamente en tu aplicación
        }
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Booking> getBookingsJson() {
        return bookings;
    }

    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBooking(Booking booking) {
        bookings.add(booking);
        writeBookingsToXML();
        init();
        return Response.status(Response.Status.CREATED).entity(booking).build();
    }


    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("id") String id, Booking updatedBooking) {
        try {
            // Validar si la reserva actualizada es nula
            if (updatedBooking == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("La reserva actualizada es nula").build();
            }

            boolean found = false;
            for (Booking booking : bookings) {
                if (booking.getLocationNumber().equals(id)) {
                    // Actualiza los atributos de la reserva con los valores proporcionados en updatedBooking
                    booking.setClientId(updatedBooking.getClientId());
                    booking.setClientName(updatedBooking.getClientName());
                    booking.setAgencyId(updatedBooking.getAgencyId());
                    booking.setAgencyName(updatedBooking.getAgencyName());
                    booking.setPrice(updatedBooking.getPrice());
                    booking.setRoomType(updatedBooking.getRoomType());
                    booking.setHotelId(updatedBooking.getHotelId());
                    booking.setHotelName(updatedBooking.getHotelName());
                    booking.setCheckInDate(updatedBooking.getCheckInDate());
                    booking.setRoomNights(updatedBooking.getRoomNights());

                    found = true;
                    break;
                }
            }

            if (!found) {
                // La reserva no fue encontrada
                return Response.status(Response.Status.NOT_FOUND).entity("Reserva no encontrada para el ID proporcionado: " + id).build();
            }

            // Llama al método para escribir en el archivo XML y para inicializar las reservas
            writeBookingsToXML();
            init();

            // Retorna la reserva actualizada
            return Response.ok(bookings).build();
        } catch (Exception e) {
            // Manejar excepciones
            e.printStackTrace();
            return Response.serverError().entity("Error al actualizar la reserva: " + e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/delete/{id}")
    public Response deleteBooking(@PathParam("id") String id) {

        // Busca y remueve la reserva si coincide con el ID proporcionado
        boolean removed = bookings.removeIf(booking -> booking.getLocationNumber().equals(id));

        if (removed) {
            // Si se eliminó exitosamente, llama al método para escribir en el archivo XML y para inicializar las reservas
            writeBookingsToXML();
            init();
            return Response.status(Response.Status.NO_CONTENT).build(); // Se eliminó exitosamente
        } else {
            return Response.status(Response.Status.NOT_FOUND).build(); // No se encontró la reserva
        }
    }

    private void writeBookingsToXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("bookings");
            doc.appendChild(rootElement);

            for (Booking booking : bookings) {
                Element bookingElement = doc.createElement("booking");
                rootElement.appendChild(bookingElement);

                bookingElement.setAttribute("location_number", booking.getLocationNumber());

                Element clientElement = doc.createElement("client");
                clientElement.setAttribute("id_client", booking.getClientId());
                clientElement.appendChild(doc.createTextNode(booking.getClientName()));
                bookingElement.appendChild(clientElement);

                Element agencyElement = doc.createElement("agency");
                agencyElement.setAttribute("id_agency", booking.getAgencyId());
                agencyElement.appendChild(doc.createTextNode(booking.getAgencyName()));
                bookingElement.appendChild(agencyElement);

                Element priceElement = doc.createElement("price");
                priceElement.appendChild(doc.createTextNode(String.valueOf(booking.getPrice())));
                bookingElement.appendChild(priceElement);

                Element roomElement = doc.createElement("room");
                roomElement.setAttribute("id_type", booking.getRoomType());
                roomElement.appendChild(doc.createTextNode(booking.getRoomType()));
                bookingElement.appendChild(roomElement);

                Element hotelElement = doc.createElement("hotel");
                hotelElement.setAttribute("id_hotel", booking.getHotelId());
                hotelElement.appendChild(doc.createTextNode(booking.getHotelName()));
                bookingElement.appendChild(hotelElement);

                Element checkInElement = doc.createElement("check_in");
                checkInElement.appendChild(doc.createTextNode(booking.getCheckInDate()));
                bookingElement.appendChild(checkInElement);

                Element roomNightsElement = doc.createElement("room_nights");
                roomNightsElement.appendChild(doc.createTextNode(String.valueOf(booking.getRoomNights())));
                bookingElement.appendChild(roomNightsElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);

            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

}