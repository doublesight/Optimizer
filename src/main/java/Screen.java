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
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Something");
    }

    protected void getBestCampaign(){

    }





    protected void sendDataTOClient(Campaign candidateCampaign){
        if(url != null && currentCampaign != candidateCampaign){
            //Logic to send the media to the client here
        }
        currentCampaign = candidateCampaign;
    }
}