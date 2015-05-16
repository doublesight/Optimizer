import com.sun.istack.internal.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcello on 06/05/15.
 */
public class Campaign {

    private static final Double HOUR_WEIGHT = 2.5D, MINUTE_WEIGHT = 1D;
    protected Date preferredTime;
    protected double timePenalty;

    public enum WEATHER_TYPE {
        STORM,
        RAIN,
        MEH,
        KIND_OF_NICE,
        SPANISH_SUMMER,
        HELL_IS_ON_EARTH
    }

    protected double weatherPenalty;
    protected WEATHER_TYPE preferredWeather;

    Map<TagType, Double> interestingTags = new HashMap<>();

    public Campaign(@NotNull String name, WEATHER_TYPE _prefWeather, Double _weatherImportance, Date _prefTime, Double _timeImportance) {
        this.name = name;
        preferredWeather = _prefWeather;
        weatherPenalty = _weatherImportance;
        preferredTime = _prefTime;
        timePenalty = _timeImportance;
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
    private double calculateWeatherPenalty(WEATHER_TYPE currentWeather) {
        if (preferredWeather == null)
            return 0;
        return Math.abs(currentWeather.ordinal() - preferredWeather.ordinal()) * weatherPenalty;
    }

    /**
     * The penalty caused by a given time.
     *
     * @param currentTimestamp current weather at the screen location
     * @return The weather score
     */
    private double calculateTimePenalty(Date currentTimestamp) {
        if (preferredTime == null)
            return 0;
        return Math.abs((currentTimestamp.getHours() * HOUR_WEIGHT + currentTimestamp.getMinutes() * MINUTE_WEIGHT) - (preferredTime.getHours() * HOUR_WEIGHT + preferredTime.getMinutes() * MINUTE_WEIGHT)) * timePenalty;
    }

    /**
     * Calculates the penalties for this campaign under the specified conditions.
     * Guaranteed return of, at least, 1.
     */
    public double calculateContextPenalties(WEATHER_TYPE weatherCurrentSituation, Long currentTime) {
        return 1 + calculateTimePenalty(new Date(currentTime)) + calculateWeatherPenalty(weatherCurrentSituation);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
