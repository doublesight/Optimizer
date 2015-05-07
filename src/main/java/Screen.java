import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * Super easy -> If the name ends with Penalty multiply, if with Bonus divide.
 */
public class Screen implements Runnable {

    //We could consider to use a quadratic distance penalty or something like that.
    protected static final double distancePenalty = 1.0D;

    //Keep tracks of the best campaign associated with the server
    protected Campaign currentCampaign = null;
    protected String url;
    protected Coordinate coordinate;
    protected List<Campaign> campaigns;
    protected List<PointOfInterest> pois;

    /*
        Badass multi-thread stuff in here. Deal with it
     */
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    /**
     * When a screen connects to the server we create an url.
     * Just after the handshake the screen should provide
     * its coordinates which will be used to perform
     * the computation.
     * @param coordinate Coordinates of the screen
     * @param url WebSocket url to which the data will be sent
     */
    public Screen(Coordinate coordinate, String url) {
        this.coordinate = coordinate;
        this.url = url;
    }

    /**
     * The run Method check a condition indefinitely and block at every loop.
     * It is the main thread that must signal it to wake up (on request, on campaign added, etc.)
     *
     */
    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                lock.lock();
                condition.await();
                getBestCampaign();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    /**
     * Method that must be called from the main thread to resume the computation
     */
    public void peformComputation(){
        try {
            lock.lock();
            condition.signal();
        } catch (Exception e){}
        finally {
            lock.unlock();
        }
    }

    /**
     * The method that starts the magic happens
     */
    protected void getBestCampaign(){
        if(campaigns == null || campaigns.size() == 0)
            return;
        Campaign candidate = null;
        double minScore = Double.MAX_VALUE;

        for(Campaign c: campaigns){
            double tmpValue = getCampaignScore(c);
            if(candidate == null || minScore > tmpValue)
                candidate = c;
        }

        sendDataTOClient(candidate);
    }

    /**
     * For each campaign we create a score.
     * To do so we need to filter POIs with tags
     * relevant to that campaign
     * @param campaign The current campaign under evaluation
     * @return The score of the campaign
     */
    protected double getCampaignScore(Campaign campaign){
        double result = 0D;
        for(TagType type: campaign.interestingTags.keySet()) {
            Stream<PointOfInterest> usefulPOIs = pois.stream().filter(poi -> poi.tagType == type);
            double tmpRes = usefulPOIs.mapToDouble(this::calculatePOIValue).sum();
            result += tmpRes * campaign.interestingTags.get(type);
        }
        return result;
    }

    /**
     * Method that calculates the value of this POI with respect to this screen.
     * @param poi The point of Interest under consideration
     * @return The value.
     */
    protected double calculatePOIValue(PointOfInterest poi) {
        return 1/Math.pow(coordinate.calculateDistance(poi.coordinate),2)/distancePenalty;
    }


    /**
     * Method that given an url sends the media to the client associated.
     * It uses WebSockets (for now).
     * @param candidateCampaign The campaign that optimizes value for this screen.
     */
    protected void sendDataTOClient(Campaign candidateCampaign){
        if(url != null && currentCampaign != candidateCampaign){
            //Logic to send the media to the client here
        }
        currentCampaign = candidateCampaign;
    }
}