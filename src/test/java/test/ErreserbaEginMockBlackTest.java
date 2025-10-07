package test;

import domain.Bidaiaria;
import domain.Driver;
import domain.Ride;
import domain.Erreserba;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



import java.util.*;

public class ErreserbaEginMockBlackTest {

    private MockDataAccess da;
    private Driver driver1;
    private Ride r1;
    private Bidaiaria b1;

    @Before
    void setUp() {
        da = new MockDataAccess();

        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        driver1 = new Driver("driver1@gmail.com", "Jon Etxeberria");
        r1 = driver1.addRide("Bilbo", "Donostia", new java.util.Date(year - 1900, month, 10), 4, 10);
        r1.setRideNumber(1);
        b1 = new Bidaiaria("bidaiaria@gmail.com", "bidaiari", "Maite Arrieta");
        b1.setDirua(100);

        da.addRide(r1);
        da.addBidaiaria(b1);
    }

    //bidaia existitzen da, bidaiaria existitzen da, eserlekuKop > 0, erreseba egiten da
    @Test
    void test1() {
        da.erreserbaEgin(1, b1, 2);
        Erreserba e = b1.erreserbaBilatu(1);

        assertNotNull(e);
        assertEquals("itxaron", e.getEgoera());
        assertEquals(2, e.getnPlaces());
    }

    //bidaia existitzen da, bidaiaria existitzen da, eserlekuKop <= 0, ez du ezer egiten
    @Test
    void test2() {
        da.erreserbaEgin(1, b1, 0);
        Erreserba e = b1.erreserbaBilatu(1);
        assertNull(e);
    }

    //bidaia existitzen da, bidaiaria EZ da existitzen, errorea
    @Test
    void test3() {
        Bidaiaria bEz = new Bidaiaria("ez@gmail.com", "bidaiari", "Ez dago");
        assertThrows(RuntimeException.class, () -> da.erreserbaEgin(1, bEz, 1));
    }

    //bidaia EZ da existitzen, bidaiaria existitzen da, errorea
    @Test
    void test4() {
        assertThrows(RuntimeException.class, () -> da.erreserbaEgin(99, b1, 1));
    }

    static class MockDataAccess {
        private List<Ride> rides = new ArrayList<>();
        private List<Bidaiaria> bidaiariak = new ArrayList<>();

        public void addRide(Ride r) {
            rides.add(r);
        }

        public void addBidaiaria(Bidaiaria b) {
            bidaiariak.add(b);
        }

        public Ride findRide(int rideNumber) {
            return rides.stream()
                    .filter(r -> r.getRideNumber() != null && r.getRideNumber() == rideNumber)
                    .findFirst()
                    .orElse(null);
        }

        public boolean existBidaiaria(Bidaiaria b) {
            return bidaiariak.contains(b);
        }

        public void erreserbaEgin(int bidaiZenbaki, Bidaiaria b, int eserlekuKop) {
            Ride r = findRide(bidaiZenbaki);
            if (r == null)
                throw new RuntimeException("Bidaia ez da existitzen");
            if (!existBidaiaria(b))
                throw new RuntimeException("Bidaiaria ez da existitzen");
            if (eserlekuKop <= 0)
                return;

            Erreserba e = b.erreserbaBilatu(bidaiZenbaki);
            if (e == null)
                e = b.addErreserba(r, eserlekuKop);

            e.setEgoera("itxaron");
            e.setnPlaces(eserlekuKop);
            r.updateSeat(eserlekuKop);
        }
    }
}
