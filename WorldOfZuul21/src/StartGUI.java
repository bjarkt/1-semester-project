/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Acq.IBusiness;
import Acq.IData;
import Acq.IUI;
import Business.BusinessFacade;
import Data.DataFacade;
import Presentation.UI;

/**
 *
 * @author Nikolaj Brandt Filipsen
 */
public class StartGUI {

    public static void main(String[] args) {
        // Create data facade
        IData data = new DataFacade();

        // Create business facade
        IBusiness business = new BusinessFacade();

        // Inject data into busines
        business.injectData(data);

        // Create UI
        IUI ui = new UI();
        // Inject business into UI
        ui.injectBusiness(business);

        ui.startApplication(args);
    }
}
