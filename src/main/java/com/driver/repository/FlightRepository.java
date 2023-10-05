package com.driver.repository;//package com.driver.repository;
//
//import com.driver.model.City;
//import com.driver.model.Flight;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//@Repository
//public class FlightRepository {
//
//    HashMap<Integer,Flight> flightMap=new HashMap<>();
//    Map<Integer,Integer> flightCancelMap = new HashMap<>();
//    Map<Integer,List<Integer>> flightPassengerMap = new HashMap<>();//flightId, passengerIds
//
//    public List<Integer> getAllBookingsWithFlightId() {
//        return new ArrayList<>(flightPassengerMap.keySet());
//    }
//
//    public void addSampleFlights() {
//        Flight flight1 = new Flight(1, City.BANGLORE, City.DELHI, 200, new Date(), 5.5);
//        Flight flight2 = new Flight(2, City.BANGLORE, City.DELHI, 150, new Date(), 2.5);
//        Flight flight3 = new Flight(3, City.JAIPUR, City.DELHI, 180, new Date(), 4.0);
//        Flight flight4 = new Flight(4, City.JAIPUR, City.DELHI, 180, new Date(), 3.0);
//        Flight flight5 = new Flight(5, City.BANGLORE, City.CHANDIGARH, 150, new Date(), 6);
//
//
//
//        addFlight(flight1);
//        addFlight(flight2);
//        addFlight(flight3);
//        addFlight(flight4);
//        addFlight(flight5);
//    }
//    public void addFlight(Flight flight) {
//        flightMap.put(flight.getFlightId(),flight);
//    }
//    public List<Flight> getAllFlight() {
//            return new ArrayList<>(flightMap.values());
//    }
//    public int numberOfTicketForFlight(Integer flightId) {
//        if (flightMap.containsKey(flightId)) {
//            return flightMap.get(flightId).getBookedTickets();
//        } else {
//            return 0;
//        }
//    }
//    public Flight getFlightById(Integer flightId) {
//        if(flightMap.containsKey(flightId)){
//            return flightMap.get(flightId);
//        }
//        return null;
//
//    }
//
//    public int getCancelBookings(Integer flightId) {
//        if(flightCancelMap.containsKey(flightId)){
//            return flightCancelMap.get(flightId);
//            //  return 1;
//        }
//        else return  1;
//    }
//
//
//    public String cancelATicket(Integer flightId, Integer passengerId) {
//        List<Integer> pIds = flightPassengerMap.get(flightId);
//        if(!pIds.contains(passengerId)) return "FAILURE";
//        List<Integer> newList = new ArrayList<>();
//        int n = pIds.size();
//        for(int i = 0 ; i < n ; i++ ){
//            if(pIds.get(i) == passengerId){
//                newList.add(i);
//            }
//        }
//        for(int i = 0 ; i < newList.size() ; i++){
//            int idx = newList.get(i);
//            pIds.remove(idx);
//
//        }
//
//        flightCancelMap.put(flightId,flightCancelMap.getOrDefault(0,1)+1);
//        return "SUCCESS";
//    }
//}


import com.driver.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightRepository {

    private final Map<Integer, Flight> flightMap = new HashMap<>();
    private final Map<Integer, List<Integer>> flightPassengerMap = new HashMap<>();
    Map<Integer,Integer> flightCancelMap = new HashMap<>();

    public void addSampleFlights() {
        // Add sample flights here
    }

    public void addFlight(Flight flight) {
        flightMap.put(flight.getFlightId(), flight);
        flightPassengerMap.put(flight.getFlightId(), new ArrayList<>()); // Initialize passenger list for this flight
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flightMap.values());
    }

    public int numberOfPassengersForFlight(Integer flightId) {
        List<Integer> passengers = flightPassengerMap.get(flightId);
        return passengers != null ? passengers.size() : 0;
    }

    public boolean isFlightFull(Integer flightId, int maxCapacity) {
        return numberOfPassengersForFlight(flightId) >= maxCapacity;
    }

    public String bookTicket(Integer flightId, Integer passengerId) {
        Flight flight = flightMap.get(flightId);

        if (flight == null) {
            return "FAILURE: Flight not found";
        }

        List<Integer> passengers = flightPassengerMap.get(flightId);

        if (isFlightFull(flightId, flight.getMaxCapacity())) {
            return "FAILURE: Flight is full";
        }

        if (passengers.contains(passengerId)) {
            return "FAILURE: Passenger already booked for this flight";
        }

        passengers.add(passengerId);
        return "SUCCESS";
    }

    public String cancelTicket(Integer flightId, Integer passengerId) {
        Flight flight = flightMap.get(flightId);

        if (flight == null) {
            return "FAILURE: Flight not found";
        }

        List<Integer> passengers = flightPassengerMap.get(flightId);

        if (!passengers.contains(passengerId)) {
            return "FAILURE: Passenger not booked for this flight";
        }

        passengers.remove(passengerId);
        return "SUCCESS";
    }
        public int getCancelBookings(Integer flightId) {
        if(flightCancelMap.containsKey(flightId)){
            return flightCancelMap.get(flightId);
            //  return 1;
        }
        else return  1;
    }

        public int numberOfTicketForFlight(Integer flightId) {
        if (flightMap.containsKey(flightId)) {
            return flightMap.get(flightId).getBookedTickets();
        } else {
            return 0;
        }
    }

        public Flight getFlightById(Integer flightId) {
        if(flightMap.containsKey(flightId)){
            return flightMap.get(flightId);
        }
        return null;

    }
}
