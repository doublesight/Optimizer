/**
 * Created by marcello on 07/05/15.
 */
public class Coordinate {
    private float latitude;
    private float longitude;

    public Coordinate(float latitute, float longitude) {
        this.latitude = latitute;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public double calculateDistance(Coordinate other) {
        return Math.sqrt(Math.pow(this.latitude - other.getLatitude(), 2) +
                        Math.pow(this.longitude - other.getLongitude(), 2)
        );
    }
}
