package cat.proven.fligths.controllers;

import cat.proven.fligths.model.Flight;
import cat.proven.fligths.model.Model;
import cat.proven.fligths.model.Passenger;
import cat.proven.fligths.views.MainView;
import java.util.List;

/**
 * Main Controller for flight application
 *
 * @author anth
 */
public class MainController {

    private Model model;
    private MainView view;

    //Contructor
    public MainController(Model model) {
        this.model = model;
        this.view = new MainView(this);
    }

    //GETTER
    public Model getModel() {
        return model;
    }

    /**
     * start running controller
     */
    public void start() {
        view.show();
    }

    /**
     * processes actions received from view
     *
     * @param action to process
     */
    public void processAction(String action) {
        switch (action) {
            case "exit": //exit application.
                exitApplication();
                break;
            case "listallflights": //list all flights.
                doListAllFlights();
                break;
            case "addflight": //add a flight
                doAddFlight();
                break;
            case "modifyflight": //modify a flight
                doModifyFlight();
                break;
            case "removeflight": //remove a flight
                doRemoveFlight();
                break;
            case "listallpassengers": //list all passengers
                doListAllPassengers();
                break;
            case "addpassenger": //add a passenger
                doAddPassenger();
                break;
            case "modifypassenger": //modify a passenger
                doModifyPassenger();
                break;
            case "removepassenger": //remove a passenger
                doRemovePassenger();
                break;
            case "Listpassengersbyflight": //List passengers by flight
                doListPassengersByFlight();
                break;
            case "registerpassengertoflight": //Register passenger to flight
                doRegisterPassengerToFlight();
                break;
            case "unregisterpassengerfromflight": //Unregister passenger from flight
                doUnregisterPassengerFromFlight();
                break;
            default:
                view.showMessage("Unknow option!");
                break;
        }
    }

    /**
     * exits application
     */
    private void exitApplication() {
        String answer = view.inputString("Exit. Are you sure?(Y/N): ");
        if (answer != null) {
            if (answer.equalsIgnoreCase("y")) {
                view.close();
            }
        }
    }

    /**
     * List all flights from database
     */
    private void doListAllFlights() {
        //retrieve data from model
        List<Flight> result = model.searchAllFlights();
        //display data to user
        if (result != null) {//succesful retrieval of data
            view.showFlightList(result);
        } else {//error retrieving data
            view.showMessage("Error retrieving data");
        }
    }

    /**
     * show to the user a form to fill with data, try to add the new flight to
     * the database, displays a message to the user with the result of the
     * insert
     */
    private void doAddFlight() {
        Flight f = view.showFlightForm();
        int result = model.addFlight(f);
        switch (result) {
            case 0:
                view.showMessage("flight not added");
                break;
            case 1:
                view.showMessage("flight added succesful");
                break;
            case -1:
                view.showMessage("code is duplicate");
                break;
            default:
                view.showMessage("error unknown");
                break;
        }
    }

    /**
     * ask the user for the code of the flight to modify, show to the user a
     * form to fill with data, try to modify the flight from database, display a
     * message to the user with the result of the modification
     */
    private void doModifyFlight() {
        try {
            String code = view.inputString("input the code of the flight to modify: ");
            if (code != null) {
                Flight f = model.searchFlightByCode(code);
                if (f != null) {
                    view.showFlight(f);
                    String modify = view.inputString("do you want to modify this flight? y/n");
                    if (modify.equalsIgnoreCase("y")) {
                        Flight fNew = view.showFlightForm();
                        if (fNew != null) {
                            int result = model.modifyFlight(fNew, code);
                            switch (result) {
                                case 0:
                                    view.showMessage("flight not modified");
                                    break;
                                case 1:
                                    view.showMessage("flight modified succesful");
                                    break;
                                case -1:
                                    view.showMessage("flight does not exist");
                                    break;
                                case -2:
                                    view.showMessage("flight's code already exist");
                                    break;
                                default:
                                    view.showMessage("error unknown");
                                    break;
                            }
                        } else if (modify.equalsIgnoreCase("n")) {
                            view.showMessage("modification has been cancelled");
                        } else {
                            view.showMessage("invalid answer");
                        }
                    } else {
                        view.showMessage("error reading new flight data");
                    }
                } else {
                    view.showMessage("flight does not exist");
                }
            } else {
                view.showMessage("error reading code");
            }
        }catch(RuntimeException ex){
            view.showMessage(ex.getMessage());
        }
    }

    private void doRemoveFlight() {
        String code = view.inputString("input the code of the flight to remove: ");
        if (code != null) {
            Flight f = model.searchFlightByCode(code);
            if (f != null) {
                view.showFlight(f);
                String delete = view.inputString("you sure you want to remove the flight? y/n");
                if (delete.equalsIgnoreCase("y")) {
                    int result = model.removeFlight(f);
                    switch (result) {
                        case 0:
                            view.showMessage("flight not removed");
                            break;
                        case 1:
                            view.showMessage("flight removed succesful");
                            break;
                        case -1:
                            view.showMessage("flight does not exist");
                            break;
                        default:
                            view.showMessage("error unknown");
                            break;
                    }
                } else if (delete.equalsIgnoreCase("n")) {
                    view.showMessage("remove has been cancelled");
                } else {
                    view.showMessage("invalid answer");
                }
            } else {
                view.showMessage("flight does not exist");
            }
        } else {
            view.showMessage("error reading code");
        }
    }

    /**
     * list all passengers from database
     */
    private void doListAllPassengers() {
        //retrieve data from model
        List<Passenger> result = model.searchAllPassenger();
        //display data to user
        if (result != null) {//succesful retrieval of data
            view.showPassengerList(result);
        } else {//error retrieving data
            view.showMessage("Error retrieving data");
        }
    }

    private void doAddPassenger() {
        Passenger p = view.showPassengerForm();
        int result = model.addPassenger(p);
        switch (result) {
            case 0:
                view.showMessage("passenger not added");
                break;
            case 1:
                view.showMessage("passenger added succesful");
                break;
            case -1:
                view.showMessage("code is duplicate");
                break;
            default:
                view.showMessage("error unknown");
                break;
        }
    }

    private void doModifyPassenger() {
        try {
            int phone = 0;
            phone = Integer.parseInt(view.inputString("input the phone of the passenger to modify"));
            if (phone != 0) {
                Passenger p = view.showPassengerForm();
                int result = model.modifyPassenger(p, phone);
                switch (result) {
                    case 0:
                        view.showMessage("passenger not modified");
                        break;
                    case 1:
                        view.showMessage("passenger modified succesful");
                        break;
                    case -1:
                        view.showMessage("passenger does not exist");
                        break;
                    case -2:
                        view.showMessage("passenger's code already exist");
                        break;
                    default:
                        view.showMessage("error unknown");
                        break;
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println("Error al transformar a numero");
        }
    }

    private void doRemovePassenger() {
        Passenger p = new Passenger(Integer.parseInt(view.inputString("input the phone of the passenger to modify")));
        int result = model.removePassenger(p);
        switch (result) {
            case 0:
                view.showMessage("passenger not removed");
                break;
            case 1:
                view.showMessage("passenger removed succesful");
                break;
            case -1:
                view.showMessage("passenger does not exist");
                break;
            default:
                view.showMessage("error unknown");
                break;
        }
    }

    private void doListPassengersByFlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doRegisterPassengerToFlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doUnregisterPassengerFromFlight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
