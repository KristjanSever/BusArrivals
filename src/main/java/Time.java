import java.time.LocalTime;

public class Time {
    int hours;
    int minutes;

    public Time(int hours, int minutes){
        this.hours = hours;
        this.minutes = minutes;
    }

    public Time(String hoursMinutes){
        String [] parsed = hoursMinutes.split(":");
        this.hours = Integer.parseInt(parsed[0]);
        this.minutes = Integer.parseInt(parsed[1]);
    }

    public Time(){
        LocalTime currentTime = LocalTime.now();
        this.hours = currentTime.getHour();
        this.minutes = currentTime.getMinute();
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hours, minutes);
    }

    public String relativeToInMinutes(Time t2){
        int minutes1 = this.hours * 60 + this.minutes;
        int minutes2 = t2.hours * 60 + t2.minutes;
        return minutes2 - minutes1 + "min";
    }

}
