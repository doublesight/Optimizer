import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

    public PointOfInterest(final Coordinate position, final Map<TagType, Double> tags) {
        coordinate = position;
        tagMap = tags;
    }

    public double calculateScoreBasedOnContext(final TagType tag, LocalTime preferredTime, double timePenalty) {
        return calculateSizeBonus() * calculateTimeScore(LocalTime.now(), preferredTime, timePenalty) * tagMap.get(tag);
    }

    /**
     * The time difference in minutes multiplied by the penalty factor.
     * Time is calculated from the shortest path;
     * Examples:
     * from 23.00 to 01.00 is 120 minutes
     * from 01.00 to 23.00 is 120 minutes
     *
     * @param now current time
     * @return the time score
     */
    protected double calculateTimeScore(LocalTime now, LocalTime preferredTime, double timePenalty) {
        double res = ChronoUnit.MINUTES.between(preferredTime, now);
        if (res < 0)
            res += +1440;
        else if (res > 720)
            res = Math.abs(res - 1440);

        return res * timePenalty;
    }


    /**
     * The expected amount of people associated with this point of interest.
     * This factor needs to be leveled since if the data is not available
     * it would be penalized too much. For this purpose we use a logarithm operation
     * with base 20 (needs tuning)
     *
     * @return
     */
    protected double calculateSizeBonus() {
        return Math.log(expectedDimension) / Math.log(logBase) / sizeBonus;
    }


}