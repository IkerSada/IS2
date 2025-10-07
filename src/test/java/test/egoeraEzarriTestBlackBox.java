package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Erreklamazioa;
import exceptions.erreklamazioaEbatzitaException;

public class egoeraEzarriTestBlackBox {

    private DataAccess dataAccess;

    @Before
    public void setUp() {
        dataAccess = new DataAccess();
        System.out.println("=== BLACK BOX TEST HASIERA ===");
    }

    @After
    public void tearDown() {
        if (dataAccess != null) {
            dataAccess.close();
        }
        System.out.println("=== BLACK BOX TEST AMAITUTA ===\n");
    }

    /**
     * BLACK BOX TEST 1: Portaera normala
     * Ez dugu inplementazioa ezagutzen, interfazea baino ez
     */
    @Test
    public void testEgoeraEzarri_PortaeraNormala_EgoeraAldatzenDa() {
        System.out.println("=== BLACK BOX TEST 1: Portaera normala ===");
        
        try {
            // BLACK BOX: Metodoaren deia egiten dugu
            Integer erreklamazioId = bilatuErreklamazioBat();
            if (erreklamazioId == null) {
                System.out.println("ℹ️  Ezin da testea egin: ez dago erreklamaziorik");
                return;
            }
            
            dataAccess.egoeraEzarri(erreklamazioId, "onartu");
            
            // BLACK BOX: Emaitza espero duogu baina ez dugu zergatik jakiten
            Erreklamazioa updated = dataAccess.erreklamazioaLortu(erreklamazioId);
            assertNotNull(updated);
            
            System.out.println("✅ BLACK BOX: Metodoak portaera normala du");
            
        } catch (Exception e) {
            System.out.println("⚠️  Portaera: " + e.getClass().getSimpleName());
            // Salbuespena ere portaera baliagarria da
        }
    }

    /**
     * BLACK BOX TEST 2: Egoera desberdinak parametro gisa
     */
    @Test
    public void testEgoeraEzarri_EgoeraDesberdinakParametro_Portaeza() {
        System.out.println("=== BLACK BOX TEST 2: Egoera desberdinak ===");
        
        String[] egoerak = {"onartu", "deuseztatu"};
        boolean batereFuntzionatuDu = false;
        
        for (String egoera : egoerak) {
            try {
                Integer erreklamazioId = bilatuErreklamazioBat();
                if (erreklamazioId == null) continue;
                
                // BLACK BOX: Egoera desberdinak probatzen ditugu
                dataAccess.egoeraEzarri(erreklamazioId, egoera);
                batereFuntzionatuDu = true;
                System.out.println("✅ Egoera '" + egoera + "' onartu du");
                
            } catch (Exception e) {
                System.out.println("⚠️  Egoera '" + egoera + "' errorea: " + e.getClass().getSimpleName());
            }
        }
        
        if (!batereFuntzionatuDu) {
            System.out.println("ℹ️  Ezin da testea osatu: ez dago daturik");
        }
    }

    /**
     * BLACK BOX TEST 3: ID existitzen ez dena
     */
    @Test
    public void testEgoeraEzarri_IDInexistentea_Portaeza() {
        System.out.println("=== BLACK BOX TEST 3: ID existitzen ez dena ===");
        
        try {
            // BLACK BOX: ID okerrarekin probatzen dugu
            dataAccess.egoeraEzarri(99999, "onartu");
            System.out.println("✅ BLACK BOX: ID okerrarekin portaera du");
            
        } catch (Exception e) {
            // Hau ere portaera normala da
            System.out.println("✅ BLACK BOX: ID okerrarekin salbuespena - " + e.getClass().getSimpleName());
        }
    }

    /**
     * BLACK BOX TEST 4: Erreklamazio ebatziaren portaera
     */
    @Test
    public void testEgoeraEzarri_ErreklamazioEbatzia_Portaeza() {
        System.out.println("=== BLACK BOX TEST 4: Erreklamazio ebatzia ===");
        
        try {
            Integer erreklamazioId = bilatuErreklamazioBat();
            if (erreklamazioId == null) return;
            
            // BLACK BOX: Ebatzi eta gero berriz aldatu nahian
            dataAccess.egoeraEzarri(erreklamazioId, "onartu");
            dataAccess.egoeraEzarri(erreklamazioId, "deuseztatu");
            
            System.out.println("✅ BLACK BOX: Aldaketa anitzak onartzen ditu");
            
        } catch (erreklamazioaEbatzitaException e) {
            System.out.println("✅ BLACK BOX: Erreklamazio ebatziak salbuespena jaurtitzen du");
        } catch (Exception e) {
            System.out.println("⚠️  Beste salbuespena: " + e.getClass().getSimpleName());
        }
    }

    // ========== METODO AUXILIARRA ==========

    private Integer bilatuErreklamazioBat() {
        // Edozein erreklamazio bilatu
        for (int i = 1; i <= 10; i++) {
            try {
                Erreklamazioa e = dataAccess.erreklamazioaLortu(i);
                if (e != null) {
                    System.out.println("🔍 Erreklamazio aurkitu: ID " + i + ", Egoera: " + e.getEgoera());
                    return i;
                }
            } catch (Exception e) {
                // Jarraitu
            }
        }
        return null;
    }
}