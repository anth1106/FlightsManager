package cat.proven.fligths.model;

import cat.proven.fligths.model.persist.FlightDao;
import cat.proven.fligths.model.persist.PassengerDao;
import java.util.List;

/**
 * Model service for flight application
 *
 * @author anth
 */
public class Model {

    //ATTRIBUTES
    FlightDao flightDao;
    PassengerDao passengerDao;

    //CONSTRUCTORS
    public Model() throws ClassNotFoundException {
        //instantiate Dao
        flightDao = new FlightDao();
        passengerDao = new PassengerDao();
    }

    //METHODS
    /**
     * searches all flights on database
     *
     * @return list of all flights or null in case of error
     */
    public List<Flight> searchAllFlights() {
        List<Flight> result = null;
        result = flightDao.selectAllFlights();
        return result;
    }

    /**
     * adds a flight to database, prevents code duplicates
     *
     * @param flight to add
     * @return 1 if succesfull, -1 if code already exist, 0 otherwise
     */
    public int addFlight(Flight flight) {
        int result = 0;
        if (flight != null) {
            Flight duplicate = searchFlightByCode(flight.getCode());
            if (duplicate != null) {
                result = -1;
            } else {
                result = flightDao.insert(flight);
            }
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * modifies a flight from database, prevents code duplicates
     *
     * @param flight fligth with the new data
     * @param code code of the flight to modify
     * @return 1 if succesfull, -1 if code does not exist,-2 if code is
     * duplicate 0 otherwise
     */
    public int modifyFlight(Flight flight, String code) {
        int result = 0;
        if (flight != null) {
            Flight exist = searchFlightByCode(code);
            if (exist != null) {
                Flight duplicate = null;
                if (code != flight.getCode()) {
                    duplicate = searchFlightByCode(flight.getCode());
                }
                if (duplicate == null) {
                    result = flightDao.update(flight, code);
                } else {
                    result = -2;
                }
            } else {
                result = -1;
            }
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * removes a flight from database
     *
     * @param flight flight to remove
     * @return 1 if successfull, 0 otherwise
     */
    public int removeFlight(Flight flight) {
        int result = 0;
        if (flight != null) {
            Flight exist = searchFlightByCode(flight.getCode());
            if (exist != null) {
                result = flightDao.delete(flight.getCode());
            } else {
                result = -1;
            }
        }
        return result;
    }

    /**
     * searches all passenger on database
     *
     * @return a list with all passengers or null in case of error
     */
    public List<Passenger> searchAllPassenger() {
        List<Passenger> result = null;
        result = passengerDao.selectAllPassengers();
        return result;
    }

    /**
     * adds a passenger to database, prevents code duplicates
     *
     * @param passenger to add
     * @return 1 if succesfull, -1 if phone is duplicate, 0 otherwise
     */
    public int addPassenger(Passenger passenger) {
        int result = 0;
        if (passenger != null) {
            boolean duplicate = searchPassengerDuplicates(passenger);
            if (duplicate) {
                result = -1;
            } else {
                result = passengerDao.insert(passenger);
            }
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * modifies a flight from database, prevents code duplicates
     *
     * @param passenger passenger with the new data
     * @param phone phone of the passenger to modify
     * @return 1 if succesfull, -1 if phone does not exist, -2 if phone is
     * duplicate 0 otherwise
     */
    public int modifyPassenger(Passenger passenger, int phone) {
        int result = 0;
        if (passenger != null) {
            Passenger pOld = new Passenger(phone);
            boolean exist = searchPassengerDuplicates(pOld);
            if (exist) {
                boolean duplicate = false;
                if (phone != passenger.getPhone()) {
                    duplicate = searchPassengerDuplicates(passenger);
                }
                if (!duplicate) {
                    String sphone = String.valueOf(phone);
                    result = passengerDao.update(passenger, sphone);
                } else {
                    result = -2;
                }
            } else {
                result = -1;
            }
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * removes a passenger from database
     *
     * @param passenger passenger to remove
     * @return 1 if successfull, 0 otherwise, -1 if does not exist
     */
    public int removePassenger(Passenger passenger) {
        int result = 0;
        if (passenger != null) {
            boolean exist = searchPassengerDuplicates(passenger);
            if (exist) {
                result = passengerDao.delete(passenger.getPhone());
            } else {
                result = -1;
            }
        }
        return result;
    }

    /**
     * searches all passenger from selected flight on database
     *
     * @param flight flight to search from
     * @return the list of passengers or null in case of error
     */
    public List<Passenger> searchPassengerByFlight(Flight flight) {
        List<Passenger> result = null;
        //TODO
        return result;
    }

    /**
     * registers a passenger into the list of passengers in the given flight
     *
     * @param passenger passenger to register into the flight
     * @param flight the flight to modify
     * @return 1 if succesful, -1 if passenger is already in the flight,-2 if
     * flight is full, 0 otherwise
     */
    public int registerPassengerToFlight(Passenger passenger, Flight flight) {
        int result = 0;
        return result;
    }

    /**
     * unregisters a passenger from the list of passengers in the given flight
     *
     * @param passenger passenger to register from the flight
     * @param flight the flight to modify
     * @return 1 if succesful, -1 if passenger is not in the flight, -2 if
     * flight is empty, 0 otherwise
     */
    public int unregisterPassengerFromFlight(Passenger passenger, Flight flight) {
        int result = 0;
        return result;
    }

    private boolean searchPassengerDuplicates(Passenger passenger) {
        boolean result = false;
        List<Passenger> passengers = passengerDao.selectAllPassengers();
        for (Passenger p : passengers) {
            if (p.getPhone() == passenger.getPhone()) {
                result = true;
            }
        }
        return result;
    }

    public Flight searchFlightByCode(String code) {
        Flight f = null;
        f = flightDao.selectByCode(code);
        return f;
    }

}
