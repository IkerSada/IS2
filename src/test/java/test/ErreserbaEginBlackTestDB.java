package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

// Bidaiaren klasea test lokaletarako
class Ride {
	private String driver;
	private String from;
	private String to;
	private int nPlaces;
	private int price;
	private String egoera;

	public Ride(String driver, String from, String to, int nPlaces, int price, String egoera) {
		this.driver = driver;
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.egoera = egoera;
	}

	public String getDriver() { return driver; }
	public int getnPlaces() { return nPlaces; }
	public String getStatus() { return egoera; }
}

public class ErreserbaEginBlackTestDB {

	private Map<Integer, Ride> bidaiak;
	private Set<String> erabiltzaileak;

	@Before
	public void setUp() {
		bidaiak = new HashMap<>();
		bidaiak.put(1, new Ride("Jon", "Bilbo", "Donostia", 3, 25, "martxan")); 
		bidaiak.put(2, new Ride("Maria", "Bilbo", "Gasteiz", 0, 30, "martxan")); 
		bidaiak.put(3, new Ride("Ane", "Bilbo", "Gernika", 2, 20, "martxan")); 
		bidaiak.put(4, new Ride("Pablo", "Bilbo", "Vitoria", 1, 15, "martxan")); 


		erabiltzaileak = new HashSet<>();
		erabiltzaileak.add("Ander");
		erabiltzaileak.add("Jon");
		erabiltzaileak.add("Maria");
	}

	
	private Ride getRideById(int id) {
		return bidaiak.get(id);
	}

	private boolean isUserExists(String name) {
		return erabiltzaileak.contains(name);
	}

	@Test
	public void Test1() { // bidai existitzen da, bidaiaria2 existitzen da, lekuak > 0
		Ride r = getRideById(1);
		assertNotNull(r);
		assertTrue(r.getnPlaces() > 0);
		assertTrue(isUserExists("Jon"));
	}

	@Test
	public void Test2() { // bidai existitzen da, bidaiaria2 existitzen da, lekuak <= 0
		Ride r = getRideById(2);
		assertNotNull(r);
		assertEquals(0, r.getnPlaces());
		assertTrue(isUserExists("Maria"));
	}

	@Test
	public void Test3() { // bidai existitzen da, bidaiaria2 ez da existitzen, lekuak > 0
		Ride r = getRideById(3);
		assertNotNull(r);
		assertTrue(r.getnPlaces() > 0);
		assertFalse(isUserExists("Miren"));
	}

	@Test
	public void Test4() { // bidai existitzen da, bidaiaria2 ez da existitzen, lekuak <= 0
		Ride r = getRideById(4);
		assertNotNull(r);
		assertTrue(r.getnPlaces() > 0); // lekuak 1
		assertFalse(isUserExists("Miren"));
	}
}
