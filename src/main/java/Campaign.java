import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcello on 06/05/15.
 */
public class Campaign {

    private static final Double HOUR_WEIGHT = 60D, MINUTE_WEIGHT = 1D;
    protected Date preferredTime;

    public enum WEATHER_TYPE {
        STORM,
        RAIN,
        MEH,
        KIND_OF_NICE,
        SPANISH_SUMMER,
        HELL_IS_ON_EARTH
    }

    protected WEATHER_TYPE preferredWeather;

    Map<TagType, Double> interestingTags = new HashMap<>();

    public Campaign(String name, WEATHER_TYPE _prefWeather, Date _prefTime) {
        this.name = name;
        preferredWeather = _prefWeather;
        preferredTime = _prefTime;
    }

    private String name;


    public void populateRatios(final Map<TagType, Double> stub) {
        interestingTags.putAll(stub);
    }

    /**
     * The penalty caused by a given weather situation.
     *
     * @param currentWeather current weather at the screen location
     * @return The weather score
     */
    private double calculateWeatherFactor(WEATHER_TYPE currentWeather) {
        if (preferredWeather == null)
            return 0;
        return 1 - Math.abs(currentWeather.ordinal() - preferredWeather.ordinal());
    }

    /**
     * The penalty caused by a given time.
     *
     * @param currentTimestamp current weather at the screen location
     * @return The weather score
     */
    private double calculateTimeFactor(Date currentTimestamp) {
        if (preferredTime == null)
            return 1;
        return 1 - (Math.abs((currentTimestamp.getHours() * HOUR_WEIGHT + currentTimestamp.getMinutes() * MINUTE_WEIGHT) - (preferredTime.getHours() * HOUR_WEIGHT + preferredTime.getMinutes() * MINUTE_WEIGHT)) / (12 * HOUR_WEIGHT));
    }

    /**
     * Calculates the penalties for this campaign under the specified conditions.
     * Guaranteed return of, at least, 1.
     */
    public double calculateContextRelevancies(WEATHER_TYPE weatherCurrentSituation, Long currentTime) {
        return 1 + calculateTimeFactor(new Date(currentTime)) + calculateWeatherFactor(weatherCurrentSituation);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
