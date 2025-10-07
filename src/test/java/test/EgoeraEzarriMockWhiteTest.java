package test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Admin;
import domain.Bidaiaria;
import domain.Driver;
import domain.Erreklamazioa;
import domain.Erreserba;
import exceptions.erreklamazioaEbatzitaException;

public class EgoeraEzarriMockWhiteTest {
    
    static DataAccess sut;
    
    protected MockedStatic<Persistence> persistenceMock;

    @Mock
    protected EntityManagerFactory entityManagerFactory;
    @Mock
    protected EntityManager db;
    @Mock
    protected EntityTransaction et;
    
    @Mock
    protected Erreklamazioa erreklamazioaMock;
    
    @Mock
    protected Bidaiaria bidaiariaMock;
    
    @Mock
    protected Driver driverMock;
    
    @Mock
    protected Erreserba erreserbaMock;
    
    @Mock
    protected Admin adminMock;
    
    @Mock
    protected TypedQuery<Admin> adminQueryMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
        persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
            .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
        Mockito.doReturn(et).when(db).getTransaction();
        sut = new DataAccess(db);
    }
    
    @After
    public void tearDown() {
        persistenceMock.close();
    }

    @Test
    // sut.egoeraEzarri: WHITE BOX - We know the implementation: 
    // if(e.getEgoera().equals("itxaron")) and if(egoera.equals("onartu"))
    // Should change state and transfer money
    public void test1() {
        int erreklamazioZenbaki = 1;
        String nuevoEgoera = "onartu";
        
        try {
            // WHITE BOX: Configure mocks based on known implementation
            when(db.find(Erreklamazioa.class, erreklamazioZenbaki)).thenReturn(erreklamazioaMock);
            when(erreklamazioaMock.getEgoera()).thenReturn("itxaron"); // First condition passes
            when(erreklamazioaMock.getNork()).thenReturn(bidaiariaMock);
            when(erreklamazioaMock.getNori()).thenReturn(driverMock);
            when(erreklamazioaMock.getErreserbarenDiruIzoztua()).thenReturn(50.0f);
            
            // WHITE BOX: We know the method flow
            sut.open();
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            sut.close();
            
            // WHITE BOX: Verify specific implementation details
            // 1. Check state change
            verify(erreklamazioaMock).setEgoera("onartu");
            
            // 2. Check money transfer (we know this happens only for "onartu")
            verify(bidaiariaMock).diruaGehitu(50.0f);
            verify(driverMock).diruaKendu(50.0f);
            
            // 3. Check movement logs (we know these are called)
            verify(bidaiariaMock).addMugimendua("Dirua itzuli zaizu erreklamazioaren onarpenarengatik");
            verify(driverMock).addMugimendua("Dirua kendu zaizu erreklamazioaren onarpenarengatik");
            
            // 4. Check transaction handling
            verify(et).begin();
            verify(et).commit();
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException - WHITE BOX: sabemos que debe pasar el if");
        }
    }

    @Test
    // sut.egoeraEzarri: WHITE BOX - We know the implementation:
    // if(e.getEgoera().equals("itxaron")) and else if(egoera.equals("deuseztatu"))
    // Should change state and assign to admin
    public void test2() {
        int erreklamazioZenbaki = 2;
        String nuevoEgoera = "deuseztatu";
        
        try {
            // WHITE BOX: Configure mocks based on known implementation
            when(db.find(Erreklamazioa.class, erreklamazioZenbaki)).thenReturn(erreklamazioaMock);
            when(erreklamazioaMock.getEgoera()).thenReturn("itxaron"); // First condition passes
            
            // WHITE BOX: Mock the admin query that we know is executed
            when(db.createQuery("SELECT a FROM Admin a", Admin.class)).thenReturn(adminQueryMock);
            when(adminQueryMock.getResultList()).thenReturn(java.util.Collections.singletonList(adminMock));
            
            sut.open();
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            sut.close();
            
            // WHITE BOX: Verify specific implementation details
            // 1. Check state change
            verify(erreklamazioaMock).setEgoera("deuseztatu");
            
            // 2. Check admin assignment (we know this happens only for "deuseztatu")
            verify(adminMock).addErreklamazioa(erreklamazioaMock);
            
            // 3. Check NO money transfer (we know this doesn't happen for "deuseztatu")
            verify(bidaiariaMock, never()).diruaGehitu(anyFloat());
            verify(driverMock, never()).diruaKendu(anyFloat());
            
            // 4. Check transaction handling
            verify(et).begin();
            verify(et).commit();
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException - WHITE BOX: sabemos que debe pasar el if");
        }
    }

    @Test
    // sut.egoeraEzarri: WHITE BOX - We know the implementation:
    // if(e.getEgoera().equals("itxaron")) but egoera is neither "onartu" nor "deuseztatu"
    // Should only change state without additional effects
    public void test3() {
        int erreklamazioZenbaki = 3;
        String nuevoEgoera = "besteBat";
        
        try {
            // WHITE BOX: Configure mocks based on known implementation
            when(db.find(Erreklamazioa.class, erreklamazioZenbaki)).thenReturn(erreklamazioaMock);
            when(erreklamazioaMock.getEgoera()).thenReturn("itxaron"); // First condition passes
            
            sut.open();
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            sut.close();
            
            // WHITE BOX: Verify specific implementation details
            // 1. Check state change
            verify(erreklamazioaMock).setEgoera("besteBat");
            
            // 2. Check NO money transfer (we know this doesn't happen for "besteBat")
            verify(bidaiariaMock, never()).diruaGehitu(anyFloat());
            verify(driverMock, never()).diruaKendu(anyFloat());
            
            // 3. Check NO admin query (we know this doesn't happen for "besteBat")
            verify(db, never()).createQuery(anyString(), eq(Admin.class));
            
            // 4. Check transaction handling
            verify(et).begin();
            verify(et).commit();
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException - WHITE BOX: sabemos que debe pasar el if");
        }
    }

    @Test
    // sut.egoeraEzarri: WHITE BOX - We know the implementation:
    // if(e.getEgoera().equals("itxaron")) is FALSE
    // Should throw erreklamazioaEbatzitaException
    public void test4() {
        int erreklamazioZenbaki = 4;
        String nuevoEgoera = "onartu";
        
        try {
            // WHITE BOX: Configure mocks - Erreklamazio is NOT in "itxaron" state
            when(db.find(Erreklamazioa.class, erreklamazioZenbaki)).thenReturn(erreklamazioaMock);
            when(erreklamazioaMock.getEgoera()).thenReturn("onartu"); // First condition FAILS
            
            sut.open();
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            sut.close();
            
            // WHITE BOX: If we reach here, the test fails because we know the exception should be thrown
            fail("WHITE BOX: Sabemos que debe lanzar excepción cuando !itxaron");
            
        } catch (erreklamazioaEbatzitaException e) {
            // WHITE BOX: Expected - verify transaction rollback behavior
            verify(et).begin();
            verify(et, never()).commit(); // Should rollback on exception
            assertTrue(true);
        }
    }


}