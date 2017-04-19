import android.location.Location;

import com.rit.se.treasurehuntvuz.TreasureGenerator;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TreasureGenerator_Test {

    @Test
    public void test_generateTreasureLocations_returnsPopulatedList() {
        Location initLocation = new Location("dummy_provider");
        initLocation.setLatitude(43.0777120);
        initLocation.setLongitude(-77.6476840);
        int amount = 1;
        assertThat(TreasureGenerator.generateTreasureLocations(initLocation, amount).isEmpty(), is(false));
        // is() is a Hamcrest matcher, meant to go nicely with assertThat()
        // use junit.Assert methods to do validation checks
    }
    
    // ...
}
