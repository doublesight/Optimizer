/**
 * Super easy -> If the name ends with Penalty divide, if with Bonus multiply.
 */
public class Screen implements Runnable {

    protected static final double distancePenalty = 1.0D;
    protected Campaign currentCampaign = null;
    protected String url;

    public Screen(String url) {
        this.url = url;
    }

    /**
     * For testing purpose only
     */
    Screen(){}

    @Override
    public void run() {

    }
}
