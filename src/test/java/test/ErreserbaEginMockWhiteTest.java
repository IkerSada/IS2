package test;

import domain.Bidaiaria;
import domain.Driver;
import domain.Ride;
import domain.Erreserba;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ErreserbaEginMockWhiteTest {

    private MockDataAccess da;

    @Before
    void setup() {
        da = new MockDataAccess();
    }

    @Test
    void testErreserbaEginErreserbaBerria() {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        Bidaiaria b = new Bidaiaria("TEST@gmail.com", "bidaiari", "Magdalena Sevillano");
        b.setDirua(100);

        Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez");
        Ride r = driver1.addRide("Bilbo", "Donostia", new java.util.Date(year-1900, month, 15), 4, 7);
        r.setRideNumber(1);

        da.addRide(r);
        da.addBidaiaria(b);

        da.erreserbaEgin(1, b, 1);

        Erreserba e = b.erreserbaBilatu(1);
        assertEquals("itxaron", e.getEgoera());
        assertEquals(1, e.getnPlaces());
    }

    @Test
    void testErreserbaDeuseztatua() {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        Bidaiaria b = new Bidaiaria("TEST2@gmail.com", "bidaiari", "Maria Perez");
        b.setDirua(100);

        Driver driver1 = new Driver("driver2@gmail.com", "Jon Martinez");
        Ride r = driver1.addRide("Gasteiz", "Bilbo", new java.util.Date(year-1900, month, 20), 4, 7);
        r.setRideNumber(2);
        da.addRide(r);
        da.addBidaiaria(b);
        Erreserba eAurrez = b.addErreserba(r, 2);
        eAurrez.setEgoera("deuseztatu");

        da.erreserbaEgin(2, b, 1);

        Erreserba e = b.erreserbaBilatu(2);
        assertEquals("itxaron", e.getEgoera());
        assertEquals(1, e.getnPlaces()); 
    }

    @Test
    void testErreserbaExistitzenEgoeraEzDeuseztatu() {
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        Bidaiaria b = new Bidaiaria("TEST3@gmail.com", "bidaiari", "Jon Lopez");
        b.setDirua(100);

        Driver driver1 = new Driver("driver3@gmail.com", "Laura Garcia");
        Ride r = driver1.addRide("Donostia", "Bilbo", new java.util.Date(year-1900, month, 25), 4, 7);
        r.setRideNumber(3);

        da.addRide(r);
        da.addBidaiaria(b);

  
        Erreserba eAurrez = b.addErreserba(r, 1);
        eAurrez.setEgoera("itxaron");

        da.erreserbaEgin(3, b, 1);

        Erreserba e = b.erreserbaBilatu(3);
        assertEquals("itxaron", e.getEgoera());
        assertEquals(2, e.getnPlaces());
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
                        .findFirst().orElse(null);
        }

        public void erreserbaEgin(int bidaiZenbaki, Bidaiaria b, int eserlekuKop) {
            Ride r = findRide(bidaiZenbaki);
            if (r == null) throw new RuntimeException("Ride ez da aurkitu");

            Erreserba e = b.erreserbaBilatu(bidaiZenbaki);
            if (e == null) {
                e = b.addErreserba(r, eserlekuKop);
            } else {
                if ("deuseztatu".equals(e.getEgoera())) {
                    e.setnPlaces(0);
                }
                e.setEgoera("itxaron");
                e.updatePlaces(eserlekuKop);
            }

            r.updateSeat(eserlekuKop);

            float prezioa = r.getBidaiarenPrezioa(eserlekuKop);
            b.diruaKendu(prezioa);
            e.eguneratuDiruIzoztua(prezioa);
            b.addMugimendua("Diruzorrotik erreserbaren prezioa kobratu da (diru hori izoztuta)");
        }
    }
}
