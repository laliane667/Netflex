import dependencies.Controller.ApplicationController;

public class Main {
    public static void main(String[] args) {
        ApplicationController appController = new ApplicationController();

        while(appController.isRunning()){
            appController.Run();
        }
    }
}
