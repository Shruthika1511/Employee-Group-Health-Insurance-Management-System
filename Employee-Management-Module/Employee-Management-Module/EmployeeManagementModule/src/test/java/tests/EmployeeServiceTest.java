package tests;

import java.sql.SQLException;
import java.util.Date;

import com.entity.Policy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.controller.PolicyController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestPolicyController {

    @Mock
    Policy policy;
	
	@InjectMocks
	PolicyController policyController;
	
    @BeforeEach
    public void setUp() throws SQLException {
        lenient().when(policy.getPolicyId()).thenReturn(1234327);
        lenient().when(policy.toString()).thenReturn(String.valueOf(1234327));
        lenient().when(policy.getStartDate()).thenReturn(new Date(2025, 1, 1));
        lenient().when(policy.getEndDate()).thenReturn(new Date(2024, 12, 31));
    }
    
    @Test
    public void testPolicyId() {

    	IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> {
    		policyController.createPolicy(policy);
    	});

        assertEquals("Policy Id is invalid.", argumentException.getMessage());
    }

    @Test
    public void testPolicyEndDate(){
        IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> {
            policyController.createPolicy(policy);
        });

        assertEquals("Policy End Date is smaller than start date.", argumentException.getMessage());

    }

}
