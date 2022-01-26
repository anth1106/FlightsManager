package cat.proven.fligths.views;

/**
 * Main menu for flight application
 * @author anth
 */
public class MainMenu extends Menu {
    
    public MainMenu(){
        setTitle("Fligth application main menu");
        addOption(new Option("Exit", "exit"));
        //
        addOption(new Option("List all flights", "listallflights"));
        addOption(new Option("Add flight", "addflight"));
        addOption(new Option("Modify flight", "modifyflight"));
        addOption(new Option("Remove flight", "removeflight"));
        //
        addOption(new Option("List all passengers", "listallpassengers"));
        addOption(new Option("Add passenger", "addpassenger"));
        addOption(new Option("Modify passenger", "modifypassenger"));
        addOption(new Option("Remove passenger", "removepassenger"));
        //
        addOption(new Option("List passengers by flight", "Listpassengersbyflight"));
        addOption(new Option("Register passenger to flight", "registerpassengertoflight"));
        addOption(new Option("Unregister passenger from flight", "unregisterpassengerfromflight"));
    }
}
