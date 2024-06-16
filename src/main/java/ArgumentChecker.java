public class ArgumentChecker {
    public enum Result {
        OK,
        FAILED
    }

    public enum TimeType {
        RELATIVE,
        ABSOLUTE
    }

    String [] arguments;
    int stationId;
    int numberOfBuses;
    TimeType timeType;

    public ArgumentChecker(String [] arguments){
        this.arguments = arguments;
    }

    public Result parse(){
        if (this.arguments.length != 3) {
            System.out.println("Provide the following args: <stationId> <numberOfBuses> <relative|absolute>");
            return Result.FAILED;
        }

        try {
            stationId = Integer.parseInt(this.arguments[0]);
            numberOfBuses = Integer.parseInt(this.arguments[1]);
            timeType = TimeType.valueOf(this.arguments[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid arguments. Please provide <stationId> as an integer, <numberOfBuses> as an integer, and <relative|absolute> as a string.");
            return Result.FAILED;
        }

        return Result.OK;
    }

    public int getStationId() {
        return this.stationId;
    }

    public int getNumberOfBuses(){
        return this.numberOfBuses;
    }

    public TimeType getTimeType(){
        return this.timeType;
    }
}
