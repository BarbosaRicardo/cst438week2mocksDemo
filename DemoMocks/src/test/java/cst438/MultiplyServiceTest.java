package cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Random;


@SpringBootTest
public class MultiplyServiceTest {
	
	
	// Use MockBean for classes to be stubbed.
	@MockBean
	private Random mockGenerator;
	
	@MockBean
	private MultiplyChecker mockChecker;
	
	// This is the class to be tested.
	private MultiplyService ms;
	
	
	// This method is executed before each test
	@BeforeEach
     public void setUpEach() {
    	MockitoAnnotations.initMocks( this);
    	
    }
	
	/*
	 * test for a correct answer
	 */
	@Test
	public void test1() {
		
		// the stub random generator, will return 18 the first 
		// time it is called and 71 the second time.
		given(mockGenerator.nextInt(90)).willReturn(18, 71);
		
		// the stub checker will return true if the test data is given
		MultiplyProblem testCheckInput = new MultiplyProblem(28, 81, 2268);
		given(mockChecker.check(testCheckInput)).willReturn(true);
		
		ms = new MultiplyService(mockGenerator, mockChecker);
		MultiplyProblem mp = ms.generateProblem();
		
		// verify that expected MultiplyProblem is returned.
		// this required that MultiplyProblem implement equals method.
		assertEquals(new MultiplyProblem(28, 81), mp);
		
		// give a correct answer.
		mp.answer = 2268;
		
		// now test the checkAnswer method.
		MultiplyProblem actualResult = ms.checkAnswer(mp);
		
		// verify that "true" was returned.
		assertEquals(new MultiplyProblem(28, 81, 2268, true), actualResult);
		
		// verify the stub was called 2 times.
		verify(mockGenerator, times(2)).nextInt(90);
		// verify that the stub was called 1 time.  The input is not checked.
		verify(mockChecker, times(1)).check(ArgumentMatchers.any(MultiplyProblem.class));
		
	}
	/*
	 * test for a incorrect answer
	 */
	@Test
	public void test2() {
		
		MultiplyProblem test = new MultiplyProblem(28, 81);
	
		// set up stubs
		given(mockGenerator.nextInt(90)).willReturn(18, 71);
		given(mockChecker.check(new MultiplyProblem(28, 81, 2011))).willReturn(false);
		
		ms = new MultiplyService(mockGenerator, mockChecker);
		MultiplyProblem mp = ms.generateProblem();
		assertEquals(test, mp);
		
		// we give an incorrect answer
		mp.answer = 2011;
		
		MultiplyProblem actualResult = ms.checkAnswer(mp);
		
		// verify that false is returned.
		assertFalse(actualResult.correct);
		
	}
	
}
