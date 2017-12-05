package Acq;

public interface IUI {
    /**
     * Inject the business facade into the presentation layer.
     *
     * @param business a business facade.
     */
    void injectBusiness(IBusiness business);

    /**
     * Start the JavaFX application
     *
     * @param args string args from main method
     */
    void startApplication(String[] args);
}
