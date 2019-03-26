package enxame;

import java.util.List;
import java.util.Random;

public class Enxame {
	List<Particula> particulas;
	Random r = new Random();
	Particula best = new Particula();
	int count = 0;
	
	public Enxame(List <Particula> particulas) {
		this.particulas = particulas;
	}
	
	public Particula particleSwarmOptimization() {
		for (Particula p : particulas) {
			if (p.bFit < best.bFit) best = p;
			p.velocidade = velocidade(p);
		}
		while (count < 100) {
			
			count++;
		}
		
		return best;
	}
	
	public double velocidade(Particula p) {
		//Variáveis para cálculo da velocidade
		double a = -1.23, b = 2.41, c = 0.879, x = (0.0) + ((1.0) - (0.0)) * r.nextDouble(), y = (0.0) + ((1.0) - (0.0)) * r.nextDouble();
		
		p.velocidade = (a * p.velocidade + b * x * (p.bFit - p.x) + c * y * (best.bFit - p.bFit));
		return p.velocidade;
	}
}
