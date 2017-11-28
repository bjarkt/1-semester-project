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
        IData data = new DataFacade();
        IBusiness business = new BusinessFacade();
        business.injectData(data);

        IUI ui = new UI();
        ui.injectBusiness(business);

        ui.startApplication(args);
    }
}
