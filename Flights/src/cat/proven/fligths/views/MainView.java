package cat.proven.fligths.views;

import cat.proven.fligths.controllers.MainController;
import cat.proven.fligths.model.Flight;
import cat.proven.fligths.model.Passenger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main View for flights application
 *
 * @author anth
 */
public class MainView {

    private MainController controller;
    private boolean exit;
    private MainMenu mainMenu;

    public MainView(MainController controller) {
        this.controller = controller;
        this.mainMenu = new MainMenu();
    }

    /**
     * Makes the view visible and starts interacting with user
     */
    public void show() {
        exit = false;
        // control loop for user interaction
        do {
            mainMenu.show();
            String action = mainMenu.getSelectedOptionActionCommand();
            if (action != null) {
                controller.processAction(action);
            }

        } while (!exit);
    }

    /**
     * show message.
     *
     * @param message the message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * displays a message and gets users answer
     *
     * @param message to display
     * @return user`s answer or null in case of error
     */
    public String inputString(String message) {
        showMessage(message);
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    /**
     * activates view closes the view
     */
    public void close() {
        this.exit = true;
    }

    /**
     * displays a list of flights
     *
     * @param data
     */
    public void showFlightList(List<Flight> data) {
        if (data != null) {
            for (Flight elem : data) {
                System.out.println(elem.toString());
            }
            System.out.format("%d elements displayed\n", data.size());
        } else {
            showMessage("Null data");
        }
    }

    /**
     * displays a list of passengers
     *
     * @param data
     */
    public void showPassengerList(List<Passenger> data) {
        if (data != null) {
            for (Passenger elem : data) {
                showMessage(elem.toString());
            }
            System.out.format("%d elements displayed\n", data.size());
        } else {
            showMessage("Null data");
        }
    }

    /**
     * displays a form of a flight to the user and return a new flight
     *
     * @return
     */
    public Flight showFlightForm() {
        Flight f = null;
        try {
            showMessage("=====FLIGHT=====");
            Scanner sc = new Scanner(System.in);

            showMessage("Code: ");
            String code = sc.nextLine();

            showMessage("Capacity: ");
            int capacity = sc.nextInt();
            sc.nextLine();

            showMessage("Date: ");
            String sdate = sc.nextLine();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);

            showMessage("Time: ");
            String stime = sc.nextLine();
            Date time = new SimpleDateFormat("HH:mm:ss").parse(stime);

            f = new Flight(0L, code, capacity, date, time);
        } catch (NumberFormatException ex) {
            f = null;
            showMessage("Error in a numeric paramether");
        } catch (ParseException ex) {
            //Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            showMessage("Error Parsing date paramethers");
            f = null;
        } catch (InputMismatchException ex) {
            f = null;
        }
        return f;
    }

    /**
     * displays a form of a flight to the user and return a new flight
     *
     * @return
     */
    public Passenger showPassengerForm() {
        Passenger p = null;
        try {
            showMessage("=====PASSENGER=====");
            Scanner sc = new Scanner(System.in);

            showMessage("Phone: ");
            String sphone = sc.nextLine();
            int phone = Integer.valueOf(sphone);

            showMessage("Name: ");
            String name = sc.nextLine();

            showMessage("Minor: ");
            showMessage("Is user minor? Y/N");
            String sminor = sc.nextLine();
            boolean minor = true;
            if (sminor.equalsIgnoreCase("Y")) {
                minor = true;
            } else if (sminor.equalsIgnoreCase("N")) {
                minor = false;
            } else {
                showMessage("Invalid minor input");
            }

            p = new Passenger(0L, phone, name, minor);
        } catch (NumberFormatException ex) {
            p = null;
            showMessage("Error in a numeric paramether");
        } catch (InputMismatchException ex) {
            p = null;
        }
        //Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        return p;
    }

    public void showFlight(Flight f) {
        System.out.println(f);
    }

    public void showPassenger(Passenger p) {
        System.out.println(p);
    }
}
