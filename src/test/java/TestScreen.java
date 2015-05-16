import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jorge on 2015-05-16.
 */
public class TestScreen {

    @org.junit.Test
    public void testScreen() throws ParseException {
        final Campaign c1 = new Campaign("ebook-sale", null, null, null, null), c2 = new Campaign("zara-summer", null, null, null, null), c3 = new Campaign("restaurant-suits-get-discount", null, null, new SimpleDateFormat("hh:mm").parse("12:00"), 0.9D), c4 = new Campaign("umbrellas-2x1", Campaign.WEATHER_TYPE.RAIN, 1D, null, null), c5 = new Campaign("gym-24/7-new-season", null, null, null, null);
        final Map<TagType, Double> ratios1 = new HashMap<>(), ratios2 = new HashMap<>(), ratios3 = new HashMap<>(), ratios4 = new HashMap<>(), ratios5 = new HashMap<>();

        Collection<Campaign> campaigns = new ArrayList<>();
        campaigns.add(c1);
        campaigns.add(c2);
        campaigns.add(c3);
        campaigns.add(c4);
        campaigns.add(c5);

        //The first campaign is about e-books
        ratios1.put(TagType.BOOKS, 0.8D);
        ratios1.put(TagType.TECH, 0.2D);

        //The second campaign comes straightaway from Zara
        ratios2.put(TagType.FASHION, 1D);

        //The third campaign promotes a classy restaurant (note how the test sets the ideal time to lunch)
        ratios3.put(TagType.FOOD, 0.95D);
        ratios3.put(TagType.FASHION, 0.05D);

        //Fashionable umbrellas all over the place!
        ratios4.put(TagType.FASHION, 1D);

        //Gym
        ratios5.put(TagType.GYM, 1D);

        c1.populateRatios(ratios1);
        c2.populateRatios(ratios2);
        c3.populateRatios(ratios3);
        c4.populateRatios(ratios4);
        c5.populateRatios(ratios5);

        final Screen sut1 = new Screen(new Coordinate(57.707065F, 11.968627F)),
                sut2 = new Screen(new Coordinate(57.708545F, 11.973627F));

        sut1.addCampaigns(campaigns);
        sut2.addCampaigns(campaigns);

        final Map<TagType, Double> tags1 = new HashMap<>(), tags2 = new HashMap<>(), tags3 = new HashMap<>(), tags4 = new HashMap<>(), tags5 = new HashMap<>(), tags6 = new HashMap<>(), tags7 = new HashMap<>();

        //MCDonalds
        tags1.put(TagType.FOOD, 1D);

        //Starbucks
        tags2.put(TagType.FASHION, 0.2D);
        tags2.put(TagType.FOOD, 0.8D);

        //Elgiganten
        tags3.put(TagType.TECH, 1D);

        //Esprit men
        tags4.put(TagType.FASHION, 1D);

        //H&M
        tags5.put(TagType.FASHION, 1D);

        //Nordic Wellness
        tags6.put(TagType.GYM, 1D);

        //Akademiska
        tags7.put(TagType.GYM, 1D);

        final PointOfInterest poi1 = new PointOfInterest(new Coordinate(57.708365F, 11.970181F), tags1), poi2 = new PointOfInterest(new Coordinate(57.708706F, 11.972493F), tags2), poi3 = new PointOfInterest(new Coordinate(57.707603F, 11.970117F), tags3), poi4 = new PointOfInterest(new Coordinate(57.707158F, 11.968023F), tags4), poi5 = new PointOfInterest(new Coordinate(57.708675F, 11.970046F), tags5), poi6 = new PointOfInterest(new Coordinate(57.705388F, 11.964836F), tags6), poi7 = new PointOfInterest(new Coordinate(57.707717F, 11.978705F), tags7);

        Collection<PointOfInterest> pois = new ArrayList<>();
        pois.add(poi1);
        pois.add(poi2);
        pois.add(poi3);
        pois.add(poi4);
        pois.add(poi5);
        pois.add(poi6);
        pois.add(poi7);

        sut1.addPois(pois);
        sut2.addPois(pois);

        final Campaign bestForScreen1 = sut1.getBestCampaign(), bestForScreen2 = sut2.getBestCampaign();
        //TODO Assert
    }
}
