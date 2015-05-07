import static org.junit.Assert.*;

/**
 * Created by marcello on 07/05/15.
 */
public class CoordinateTest {

    private static final double epsilon = 0.0001D;

    @org.junit.Test
    public void testCalculateDistance() throws Exception {
        Coordinate origin = new Coordinate(0,0);
        Coordinate other  = new Coordinate(1,1);
        assertEquals(origin.calculateDistance(other), Math.sqrt(2), epsilon);
    }
}