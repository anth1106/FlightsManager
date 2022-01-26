package cat.proven.fligths;

import cat.proven.fligths.controllers.MainController;
import cat.proven.fligths.model.Model;

/**
 * Main class for Fligths application
 *
 * @author anth
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Model model = new Model();
            MainController controller = new MainController(model);
            controller.start();
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
