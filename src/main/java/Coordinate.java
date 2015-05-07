/**
 * Created by marcello on 07/05/15.
 */
public class Coordinate {
    protected double latitude;
    protected double longitude;

    public Coordinate(double latitute, double longitude) {
        this.latitude = latitute;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double calculateDistance(Coordinate other) {
        return Math.sqrt(   Math.pow(this.latitude - other.getLatitude(), 2) +
                            Math.pow(this.longitude - other.getLongitude(), 2)
        );
    }
}
