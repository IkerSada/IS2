package test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import dataAccess.DataAccess;

@RunWith(MockitoJUnitRunner.class)
public class egoeraEzarriTest {

    @Mock
    private EntityManager db;

    @InjectMocks
    private DataAccess sut;
	
    @Test
    public void testGetTravelerById_ReturnsTraveler_WhenExists() {
      
    }

}
