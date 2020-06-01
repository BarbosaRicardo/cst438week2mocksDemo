package cst438;

import org.springframework.stereotype.Service;

@Service
public class MultiplyChecker {
	
	public boolean check(MultiplyProblem mp) {
		return mp.operand1*mp.operand2 == mp.answer ;
	}

}
