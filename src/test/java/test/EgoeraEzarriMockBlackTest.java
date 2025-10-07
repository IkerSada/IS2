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

public class EgoeraEzarriMockBlackTest {
    
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
    // sut.egoeraEzarri: The Erreklamazio exists and is in "itxaron" state
    // and the state should be changed to "onartu" successfully
    public void test1() {
        int erreklamazioZenbaki = 1;
        String nuevoEgoera = "onartu";
        
        try {
            // Configure the state through mocks
            when(db.find(Erreklamazioa.class, erreklamazioZenbaki)).thenReturn(erreklamazioaMock);
            when(erreklamazioaMock.getEgoera()).thenReturn("itxaron");
            when(erreklamazioaMock.getNork()).thenReturn(bidaiariaMock);
            when(erreklamazioaMock.getNori()).thenReturn(driverMock);
            when(erreklamazioaMock.getErreserbarenDiruIzoztua()).thenReturn(50.0f);
            
            // Invoke System Under Test (sut)
            sut.open();
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            sut.close();
            
            // Verify the results - BLACK BOX: we only verify external behavior
            verify(erreklamazioaMock).setEgoera("onartu");
            verify(bidaiariaMock).diruaGehitu(50.0f);
            verify(driverMock).diruaKendu(50.0f);
            verify(et).begin();
            verify(et).commit();
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException");
        }
    }

    @Test
    // sut.egoeraEzarri: The Erreklamazio exists and is in "itxaron" state
    // and the state should be changed to "deuseztatu" successfully
    public void test2() {
        int erreklamazioZenbaki = 2;
        String nuevoEgoera = "deuseztatu";
        
        try {
            // Configure the state through mocks
            when(db.find(Erreklamazioa.class, erreklamazioZenbaki)).thenReturn(erreklamazioaMock);
            when(erreklamazioaMock.getEgoera()).thenReturn("itxaron");
            
            // Mock the admin query
            TypedQuery<Admin> adminQueryMock = mock(TypedQuery.class);
            Admin adminMock = mock(Admin.class);
            when(db.createQuery(anyString(), eq(Admin.class))).thenReturn(adminQueryMock);
            when(adminQueryMock.getResultList()).thenReturn(java.util.Collections.singletonList(adminMock));
            
            // Invoke System Under Test (sut)
            sut.open();
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            sut.close();
            
            // Verify the results - BLACK BOX: we only verify external behavior
            verify(erreklamazioaMock).setEgoera("deuseztatu");
            verify(adminMock).addErreklamazioa(erreklamazioaMock);
            verify(et).begin();
            verify(et).commit();
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException");
        }
    }

    @Test
    // sut.egoeraEzarri: The Erreklamazio exists and is in "itxaron" state
    // and the state should be changed to "besteBat" successfully
    public void test3() {
        int erreklamazioZenbaki = 3;
        String nuevoEgoera = "besteBat";
        
        try {
            // Configure the state through mocks
            when(db.find(Erreklamazioa.class, erreklamazioZenbaki)).thenReturn(erreklamazioaMock);
            when(erreklamazioaMock.getEgoera()).thenReturn("itxaron");
            
            // Invoke System Under Test (sut)
            sut.open();
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            sut.close();
            
            // Verify the results - BLACK BOX: we only verify external behavior
            verify(erreklamazioaMock).setEgoera("besteBat");
            // No money transfer or admin assignment for "besteBat"
            verify(bidaiariaMock, never()).diruaGehitu(anyFloat());
            verify(driverMock, never()).diruaKendu(anyFloat());
            verify(et).begin();
            verify(et).commit();
            
        } catch (erreklamazioaEbatzitaException e) {
            fail("No se esperaba erreklamazioaEbatzitaException");
        }
    }

    @Test
    // sut.egoeraEzarri: The Erreklamazio exists but is NOT in "itxaron" state
    // and the erreklamazioaEbatzitaException must be thrown
    public void test4() {
        int erreklamazioZenbaki = 4;
        String nuevoEgoera = "onartu";
        
        try {
            // Configure the state through mocks - Erreklamazio already resolved
            when(db.find(Erreklamazioa.class, erreklamazioZenbaki)).thenReturn(erreklamazioaMock);
            when(erreklamazioaMock.getEgoera()).thenReturn("onartu"); // Already resolved
            
            // Invoke System Under Test (sut)
            sut.open();
            sut.egoeraEzarri(erreklamazioZenbaki, nuevoEgoera);
            sut.close();
            
            // If we reach here, the test fails
            fail("Se esperaba erreklamazioaEbatzitaException");
            
        } catch (erreklamazioaEbatzitaException e) {
            // Expected behavior - test passes
            verify(et).begin();
            verify(et, never()).commit(); // Should rollback on exception
            assertTrue(true);
        }
    }

 
}