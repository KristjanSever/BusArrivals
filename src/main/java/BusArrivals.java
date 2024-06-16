import java.sql.*;
import java.util.*;

public class BusArrivals {

    private static final String databaseConnectionURI = "jdbc:sqlite:schedule.db";
    private static final String sqlQuery = """
            SELECT
                st.trip_id,
                st.arrival_time,
                st.departure_time,
                s.stop_id,
                s.stop_name,
                s.stop_code,
                t.trip_headsign
            FROM
                stop_times st
                JOIN stops s ON st.stop_id = s.stop_id
                JOIN trips t ON st.trip_id = t.trip_id
            WHERE
                st.stop_id = ?
                AND TIME(st.arrival_time) BETWEEN TIME(?) AND TIME(?, '+2 hours')
            ORDER BY
                st.arrival_time
            LIMIT (?);
            """;
    int stationId;
    int numberOfBuses;
    Time startTime;
    String stopName;
    ArgumentChecker.TimeType timeType;

    public BusArrivals(int stationId, int numberOfBuses, Time startTime, ArgumentChecker.TimeType timeType){
        this.stationId = stationId;
        this.numberOfBuses = numberOfBuses;
        this.startTime = startTime;
        this.timeType = timeType;
    }

    public BusArrivals(int stationId, int numberOfBuses, ArgumentChecker.TimeType timeType){
        this.stationId = stationId;
        this.numberOfBuses = numberOfBuses;
        this.startTime = new Time();
        this.timeType = timeType;
    }

    public void execute(){
        startTime = new Time(10, 10);
        ResultSet rs;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(databaseConnectionURI);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, stationId);
            preparedStatement.setString(2, startTime.toString());
            preparedStatement.setString(3, startTime.toString());
            preparedStatement.setInt(4, numberOfBuses);

            rs = preparedStatement.executeQuery();

            if (!rs.isBeforeFirst() ) {
                System.out.println("No results.");
            }
        } catch (SQLException e) {
            System.out.println("Creating query and getting result failed:\n" + Arrays.toString(e.getStackTrace()));
            return;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, ArrayList<TripEntry>> hashMap = new HashMap<>();

        try {
            while (rs.next()) {
                int id = rs.getInt("stop_id");
                int stopCode = rs.getInt("stop_code");
                stopName = rs.getString("stop_name");
                String arrivalTime = rs.getString("arrival_time");
                String tripHeadsign = rs.getString("trip_headsign");

                TripEntry trip = new TripEntry(id, stopCode, stopName, arrivalTime, tripHeadsign);
                if (!hashMap.containsKey(tripHeadsign)) {
                    hashMap.put(tripHeadsign, new ArrayList<>());
                }
                hashMap.get(tripHeadsign).add(trip);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        outputResult(hashMap);
    }

    private void outputResult(HashMap<String, ArrayList<TripEntry>> hashMap){
        System.out.println("Bus stop " + stopName);

        hashMap.forEach((tripHeadsign, tripList) -> {
            ArrayList<String> times = new ArrayList<>();
            tripList.forEach(trip -> {
                Time time = new Time(trip.arrivalTime());

                if(this.timeType == ArgumentChecker.TimeType.RELATIVE){
                    times.add(this.startTime.relativeToInMinutes(time));
                } else {
                    times.add(time.toString());
                }

            });
            System.out.println(tripHeadsign + ": " + String.join(", ", times));
        });
    }
}

record TripEntry(int id, int stopCode, String stopName, String arrivalTime, String tripHeadsign) {
    @Override
    public String toString() {
        return id + "\t" + stopCode + "\t" + stopName + "\t" + arrivalTime + "\t" + tripHeadsign;
    }
}
