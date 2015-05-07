
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcello on 06/05/15.
 */
public class Campaign {

    /**
     * Super naive implementation for weather.
     * values are
     *      0 = storm
     *      1 = rain
     *      2 = meh weather (Swedish good weather :) )
     *      3 = kinda nice
     *      4 = summer in italy
     *      5 = hell is on earth (Not used for now)
     */
    protected double weatherPenalty = 1.0D;
    protected int preferredWeather = 2;

    byte[] media;
    int mediaType;
    Map<TagType,Double> interestingTags = new HashMap<>();


    /**
     * The weather score multiplied by the weather impact factor.
     * @param weatherCurrentSituation current weather at the screen location
     * @return The weather score
     */
    protected double calculateWeatherScore(int weatherCurrentSituation){
        return Math.abs(weatherCurrentSituation - preferredWeather) * weatherPenalty;
    }


    public double calculateScoreBasedOnContext(int weatherCurrentSituation) {
        return calculateWeatherScore(weatherCurrentSituation);
    }
}
