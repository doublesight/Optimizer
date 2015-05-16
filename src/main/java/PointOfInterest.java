import java.util.Date;
import java.util.Map;

/**
 * Created by marcello on 06/05/15.
 */
public class PointOfInterest {


    /**
     * Data used to create the score for the dimension.
     */
    protected double sizeBonus = 1.0D;
    protected double expectedDimension = 100;
    protected double logBase = 20;

    protected Coordinate coordinate;
    protected final Map<TagType, Double> tagMap;
    private String name;

    public PointOfInterest(final String _name, final Coordinate position, final Map<TagType, Double> tags) {
        name = _name;
        coordinate = position;
        tagMap = tags;
    }

    public double calculateScoreBasedOnContext(final TagType tag, Date preferredTime) {
        if (preferredTime == null)
            return calculateSizeBonus() * tagMap.get(tag);
        return calculateSizeBonus() * calculateTimeFactor(new Date(System.currentTimeMillis()), preferredTime) * tagMap.get(tag);
    }

    private static final Double HOUR_WEIGHT = 60D, MINUTE_WEIGHT = 1D;

    private double calculateTimeFactor(Date currentTimestamp, Date preferredTime) {
        if (preferredTime == null)
            return 1;
        return 1 - (Math.abs((currentTimestamp.getHours() * HOUR_WEIGHT + currentTimestamp.getMinutes() * MINUTE_WEIGHT) - (preferredTime.getHours() * HOUR_WEIGHT + preferredTime.getMinutes() * MINUTE_WEIGHT)) / (12 * HOUR_WEIGHT));
    }

    protected double calculateSizeBonus() {
        return Math.log(expectedDimension) / Math.log(logBase) / sizeBonus;
    }


    public String getName() {
        return name;
    }
}