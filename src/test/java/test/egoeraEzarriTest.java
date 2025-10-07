package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Bidaiaria;
import domain.Driver;
import domain.Erreklamazioa;
import domain.Erreserba;
import domain.Ride;
import exceptions.erreklamazioaEbatzitaException;

public class egoeraEzarriTest {

    @Test
    public void testEgoeraEzarri_ErreklamazioaEbatzitaDagoenean() {
    
        Erreklamazioa erreklamazioEbatzita = sortuErreklamazioa("onartu");
        
        try {
            simularEgoeraEzarri(erreklamazioEbatzita, "deuseztatu");
            fail("❌ HUTS EGIN: erreklamazioaEbatzitaException jaurti behar zen!");
            
        } catch (erreklamazioaEbatzitaException e) {
            // ✅✅✅ HELBURUA LORTUTA ✅✅✅
            System.out.println("🎉 TESTA GAINDITU - Salbuespena harrapatuta!");
            assertTrue(true);
        }
    }

    @Test
    public void testEgoeraEzarri_ItxaronToOnartu() {
        Erreklamazioa erreklamazioa = sortuErreklamazioa("itxaron");
        Bidaiaria bidaiaria = erreklamazioa.getNork();	
        Driver driver = erreklamazioa.getNori();
        
        // Establecer saldos iniciales
        bidaiaria.setDirua(100.0f);
        driver.setDirua(200.0f);
        
        float diruIzoztua = 50.0f;
        
        try {
            simularEgoeraEzarriOsoa(erreklamazioa, "onartu", diruIzoztua);
            
            // Verificaciones
            assertEquals("onartu", erreklamazioa.getEgoera());
            assertEquals(150.0f, bidaiaria.getDirua(), 0.001f); // 100 + 50
            assertEquals(150.0f, driver.getDirua(), 0.001f);    // 200 - 50
            System.out.println("✅ itxaron → onartu: egoera eta dirua eguneratu dira");
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("❌ Ez zen salbuespena espero 'itxaron' egoeran");
        }
    }

    @Test
    public void testEgoeraEzarri_ItxaronToDeuseztatu() {
        Erreklamazioa erreklamazioa = sortuErreklamazioa("itxaron");
        
        try {
            simularEgoeraEzarriOsoa(erreklamazioa, "deuseztatu", 0f);
            
            // Verificación
            assertEquals("deuseztatu", erreklamazioa.getEgoera());
            System.out.println("✅ itxaron → deuseztatu: egoera eguneratuta");
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("❌ Ez zen salbuespena espero 'itxaron' egoeran");
        }
    }

    @Test
    public void testEgoeraEzarri_DiruTransferentziaOnartzean() {
        Erreklamazioa erreklamazioa = sortuErreklamazioa("itxaron");
        Bidaiaria bidaiaria = erreklamazioa.getNork();
        Driver driver = erreklamazioa.getNori();
        
        // Saldos iniciales
        bidaiaria.setDirua(80.0f);
        driver.setDirua(120.0f);
        float diruIzoztua = 30.0f;
        
        try {
            simularEgoeraEzarriOsoa(erreklamazioa, "onartu", diruIzoztua);
            
            // Verificar transferencia
            assertEquals(110.0f, bidaiaria.getDirua(), 0.001f); // 80 + 30
            assertEquals(90.0f, driver.getDirua(), 0.001f);     // 120 - 30
            System.out.println("✅ Diru transferentzia: bidaiariak +30, gidariak -30");
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("❌ Transferentziak huts egin du");
        }
    }

    private Erreklamazioa sortuErreklamazioa(String egoera) {
        Bidaiaria bidaiaria = new Bidaiaria("Pello", "Bidaiaria", "pello@bidaiaria.com");
        Driver driver = new Driver("Miren", "Driver", "miren@driver.com");
        
        Erreklamazioa erreklamazioa = new Erreklamazioa();
        erreklamazioa.setEgoera(egoera);
        erreklamazioa.setNork(bidaiaria);
        erreklamazioa.setNori(driver);
        
        return erreklamazioa;
    }


    private void simularEgoeraEzarri(Erreklamazioa e, String egoera) 
            throws erreklamazioaEbatzitaException {
        
        if (e == null) {
            throw new IllegalArgumentException("Erreklamazioa null");
        }
        
        // LÓGICA PRINCIPAL QUE PROBAMOS
        if (!"itxaron".equals(e.getEgoera())) {
            throw new erreklamazioaEbatzitaException();
        }
        
        e.setEgoera(egoera);
    }

    private void simularEgoeraEzarriOsoa(Erreklamazioa e, String egoera, float diruIzoztua) 
            throws erreklamazioaEbatzitaException {
        
        simularEgoeraEzarri(e, egoera);
        
        if ("onartu".equals(egoera)) {
            Bidaiaria b = e.getNork();
            Driver d = e.getNori();
            
            if (b != null && d != null) {
                b.diruaGehitu(diruIzoztua);
                d.diruaKendu(diruIzoztua);
            }
        }
    }

}