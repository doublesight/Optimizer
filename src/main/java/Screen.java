import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.util.*;
import java.util.stream.Stream;

/**
 * For the demo each screen performs the calculations. We acknowledge that this is of course not the way to go. In the real implementation, calculations will be executed following a centralized architecture. This is not only much more efficient computationally speaking, but it also allows the system to know which contents should be sent where at every point in time, converting the screens in simple media players that reproduce what they get sent.
 */
public class Screen implements Runnable {

    //Keep tracks of the best campaign associated with the server
//    protected Campaign currentCampaign = null;
    protected String url;
    protected Coordinate coordinate;
    protected final List<Campaign> campaigns = new ArrayList<>();
    protected final List<PointOfInterest> pois = new ArrayList<>();

    /**
     * When a screen connects to the server we create an url.
     * Just after the handshake the screen should provide
     * its coordinates which will be used to perform
     * the computation.
     *
     * @param coordinate Coordinates of the screen
     */
    public Screen(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.url = null; //This will be the websocket url to which the data will be sent IRL
    }

    public void addCampaigns(Collection<Campaign> x) {
        campaigns.addAll(x);
    }

    public void addPois(Collection<PointOfInterest> x) {
        pois.addAll(x);
    }


    /**
     * The run Method check a condition indefinitely and block at every loop.
     * It is the main thread that must signal it to wake up (on request, on campaign added, etc.)
     */
    @Override
    public void run() {
        getBestCampaign();
    }

    /**
     * The method that starts the magic happens
     */
    protected Campaign getBestCampaign() {
        if (campaigns == null || campaigns.size() == 0)
            return null;
        Campaign candidate = null;
        double minValue = Double.MAX_VALUE;

        for (Campaign c : campaigns) {
            double tmpValue = getCampaignScore(c);
            if (candidate == null || tmpValue < minValue) {
                candidate = c;
                minValue = tmpValue;
            }
        }

        return candidate;
//        sendDataToClient(candidate);
    }

    /**
     * For each campaign we create a score.
     * To do so we need to filter POIs with tags
     * relevant to that campaign
     *
     * @param campaign The current campaign under evaluation
     * @return The score of the campaign
     */
    protected double getCampaignScore(Campaign campaign) {
        double result = 0D;
        for (TagType type : campaign.interestingTags.keySet()) {
            //Narrow down the POIs to those containing these tags
            Stream<PointOfInterest> usefulPOIs = pois.stream().filter(poi -> poi.tagMap.containsKey(type));
            double tmpRes = 1;

            for (Iterator<PointOfInterest> it = usefulPOIs.iterator(); it.hasNext(); ) {
                final PointOfInterest poi = it.next();
                tmpRes += calculatePOIScore(poi, type, campaign.preferredTime);
            }

            final double contextRelevancies = campaign.calculateContextRelevancies(getSimplifiedWeatherHere(), System.currentTimeMillis());

            result += tmpRes
                    * campaign.interestingTags.get(type)
                    * contextRelevancies;
        }

        System.out.println(campaign + ": " + result);

        return result;
    }

    /**
     * Very dirty method, we know. It is just for the demo, we promise :D
     */
    private Campaign.WEATHER_TYPE getSimplifiedWeatherHere() {
        //Do not put your API keys in the code children, very bad practice
        OpenWeatherMap owm = new OpenWeatherMap("a564b67a0c3fb3623baebb4f7a0f7f8f");

        CurrentWeather currentWeather = owm.currentWeatherByCoordinates(coordinate.getLatitude(), coordinate.getLongitude());

        final float humidity =
                currentWeather.getMainInstance().getHumidity();
        if (humidity > 80)
            return Campaign.WEATHER_TYPE.STORM;

        if (humidity > 60)
            return Campaign.WEATHER_TYPE.RAIN;

        //It is given in kelvin
        final double celsius = currentWeather.getMainInstance().getTemperature() - 273.15;

        if (celsius > 50)
            return Campaign.WEATHER_TYPE.HELL_IS_ON_EARTH;
        if (celsius > 40)
            return Campaign.WEATHER_TYPE.SPANISH_SUMMER;
        if (celsius > 30)
            return Campaign.WEATHER_TYPE.KIND_OF_NICE;

        return Campaign.WEATHER_TYPE.MEH;
    }

    /**
     * Method that calculates the value of this POI with respect to this screen.
     *
     * @param poi The point of Interest under consideration
     * @param tag The tag
     * @return The value.
     */
    protected double calculatePOIScore(PointOfInterest poi, TagType tag, Date preferredTime) {
        return Math.pow(coordinate.calculateDistance(poi.coordinate), 2)
                * poi.calculateScoreBasedOnContext(tag, preferredTime);
    }


    /**
     * Method that given an url sends the media to the client associated.
     * It uses WebSockets (for now).
     *
     * @param candidateCampaign The campaign that optimizes value for this screen.
     */
//    protected void sendDataToClient(Campaign candidateCampaign) {
//        if (url != null && currentCampaign != candidateCampaign) {
//            //Logic to send the media to the client here
//        }
//        currentCampaign = candidateCampaign;
//    }
}