package cst438;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MultiplyService {
	
	@Autowired
	private Random generator;
	
	@Autowired
	private MultiplyChecker mc;
	
	public MultiplyService() { }
	
	// this constructor is used in test to stub out
	// generator and multiplyChecker.
	public MultiplyService(Random generator, MultiplyChecker mc) {
		this.generator = generator;
		this.mc = mc;
	}
	
	public MultiplyProblem generateProblem() {
		// generate 2 random 2 digit integers in range [10, 99]
		int a = generator.nextInt(90)+10;
		int b = generator.nextInt(90)+10;
		MultiplyProblem mp = new MultiplyProblem(a, b);
		return mp;		
	}
	
	public MultiplyProblem checkAnswer(MultiplyProblem mp) {
		mp.correct = mc.check(mp);
		return mp;
		
	}

}
