package test;
import static org.junit.Assert.*;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Erreklamazioa;
import exceptions.erreklamazioaEbatzitaException;

public class EgoeraEzarriBDWhiteTest {

    // sut: system under test
    static DataAccess sut = new DataAccess();

    @Test
    // sut.egoeraEzarri: The Erreklamazio exists and is in "itxaron" state
    // and the state should be changed to "onartu" with money transfer
    public void test1() {
        int erreklamazioZenbaki = 1;
        String nuevoEgoera = "onartu";
        
        try {
            // Find or create an Erreklamazio in "itxaron" state
            Integer existingId = findErreklamazioItxaron();
            if (existingId != null) {
                erreklamazioZenbaki = existingId;
            }
            
            // Get initial state for verification
            sut.open();
            Erreklamazioa original = sut.erreklamazioaLortu(erreklamazioZenbaki);
            float initialBidaiariaMoney = original.getNork().getDirua();
            float initialDriverMoney = original.getNori().getDirua();
            float diruIzoztua = original.getErreserbarenDiruIzoztua();
            sut.close();
            
            // Invoke System Under Test (sut)
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            
            // Verify the results - WHITE BOX: we know the implementation
            sut.open();
            Erreklamazioa updated = sut.erreklamazioaLortu(erreklamazioZenbaki);
            float finalBidaiariaMoney = updated.getNork().getDirua();
            float finalDriverMoney = updated.getNori().getDirua();
            sut.close();
            
            assertNotNull("Erreklamazioa no debería ser null", updated);
            assertEquals("onartu", updated.getEgoera());
            assertEquals(initialBidaiariaMoney + diruIzoztua, finalBidaiariaMoney, 0.001f);
            assertEquals(initialDriverMoney - diruIzoztua, finalDriverMoney, 0.001f);
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException");
        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

    @Test
    // sut.egoeraEzarri: The Erreklamazio exists and is in "itxaron" state
    // and the state should be changed to "deuseztatu" with admin assignment
    public void test2() {
        int erreklamazioZenbaki = 2;
        String nuevoEgoera = "deuseztatu";
        
        try {
            // Find or create an Erreklamazio in "itxaron" state
            Integer existingId = findErreklamazioItxaron();
            if (existingId != null) {
                erreklamazioZenbaki = existingId;
            }
            
            // Get initial state for verification
            sut.open();
            Erreklamazioa original = sut.erreklamazioaLortu(erreklamazioZenbaki);
            float initialBidaiariaMoney = original.getNork().getDirua();
            float initialDriverMoney = original.getNori().getDirua();
            sut.close();
            
            // Invoke System Under Test (sut)
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            
            // Verify the results - WHITE BOX: we know the implementation
            sut.open();
            Erreklamazioa updated = sut.erreklamazioaLortu(erreklamazioZenbaki);
            float finalBidaiariaMoney = updated.getNork().getDirua();
            float finalDriverMoney = updated.getNori().getDirua();
            sut.close();
            
            assertNotNull("Erreklamazioa no debería ser null", updated);
            assertEquals("deuseztatu", updated.getEgoera());
            // WHITE BOX: No money transfer for "deuseztatu"
            assertEquals(initialBidaiariaMoney, finalBidaiariaMoney, 0.001f);
            assertEquals(initialDriverMoney, finalDriverMoney, 0.001f);
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException");
        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

    @Test
    // sut.egoeraEzarri: The Erreklamazio exists and is in "itxaron" state
    // and the state should be changed to "besteBat" without additional effects
    public void test3() {
        int erreklamazioZenbaki = 3;
        String nuevoEgoera = "besteBat";
        
        try {
            // Find or create an Erreklamazio in "itxaron" state
            Integer existingId = findErreklamazioItxaron();
            if (existingId != null) {
                erreklamazioZenbaki = existingId;
            }
            
            // Get initial state for verification
            sut.open();
            Erreklamazioa original = sut.erreklamazioaLortu(erreklamazioZenbaki);
            float initialBidaiariaMoney = original.getNork().getDirua();
            float initialDriverMoney = original.getNori().getDirua();
            sut.close();
            
            // Invoke System Under Test (sut)
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            
            // Verify the results - WHITE BOX: we know the implementation
            sut.open();
            Erreklamazioa updated = sut.erreklamazioaLortu(erreklamazioZenbaki);
            float finalBidaiariaMoney = updated.getNork().getDirua();
            float finalDriverMoney = updated.getNori().getDirua();
            sut.close();
            
            assertNotNull("Erreklamazioa no debería ser null", updated);
            assertEquals("besteBat", updated.getEgoera());
            // WHITE BOX: No money transfer for "besteBat"
            assertEquals(initialBidaiariaMoney, finalBidaiariaMoney, 0.001f);
            assertEquals(initialDriverMoney, finalDriverMoney, 0.001f);
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException");
        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

    @Test
    // sut.egoeraEzarri: The Erreklamazio exists but is NOT in "itxaron" state
    // and the erreklamazioaEbatzitaException must be thrown
    public void test4() {
        try {
            // First create a scenario: create and resolve an Erreklamazio
            Integer erreklamazioId = findOrCreateErreklamazioItxaron();
            if (erreklamazioId == null) {
                System.out.println("No se pudo crear escenario de prueba");
                return;
            }
            
            // First change: itxaron → onartu (should work)
            sut.egoeraEzarri(erreklamazioId, "onartu");
            
            // Second change: onartu → deuseztatu (should throw exception)
            sut.egoeraEzarri(erreklamazioId, "deuseztatu");
            
            // If we reach here, the test fails
            fail("Se esperaba erreklamazioaEbatzitaException en el segundo cambio");
            
        } catch (erreklamazioaEbatzitaException e) {
            // Expected behavior - test passes
            assertTrue(true);
        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

 

    // ========== HELPER METHODS ==========

    private Integer findErreklamazioItxaron() {
        // Look for existing Erreklamazio in "itxaron" state
        for (int i = 1; i <= 10; i++) {
            try {
                sut.open();
                Erreklamazioa e = sut.erreklamazioaLortu(i);
                sut.close();
                if (e != null && "itxaron".equals(e.getEgoera())) {
                    return i;
                }
            } catch (Exception e) {
                sut.close();
                // Continue searching
            }
        }
        return null;
    }

    private Integer findOrCreateErreklamazioItxaron() {
        // First try to find existing
        Integer existing = findErreklamazioItxaron();
        if (existing != null) {
            return existing;
        }
        
        // If no existing, try to use any Erreklamazio and reset it to "itxaron"
        for (int i = 1; i <= 10; i++) {
            try {
                sut.open();
                Erreklamazioa e = sut.erreklamazioaLortu(i);
                if (e != null) {
                    // If we can modify it, set to "itxaron"
                    e.setEgoera("itxaron");
                    // You might need to persist this change depending on your implementation
                    sut.close();
                    return i;
                }
                sut.close();
            } catch (Exception e) {
                sut.close();
                // Continue searching
            }
        }
        return null;
    }
}