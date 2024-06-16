import java.time.LocalTime;


public class Main {
    public static void main(String [] args){
        ArgumentChecker argumentChecker = new ArgumentChecker(args);

        if(argumentChecker.parse() == ArgumentChecker.Result.FAILED) {
            return;
        }

        BusArrivals busArrivals = new BusArrivals(argumentChecker.getStationId(), argumentChecker.getNumberOfBuses(), argumentChecker.getTimeType());
        busArrivals.execute();
    }
}
