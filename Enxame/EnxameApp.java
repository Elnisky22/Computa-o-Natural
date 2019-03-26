package enxame;

import java.util.ArrayList;
import java.util.List;

public class EnxameApp {

	public static void main(String[] args) {
		int qtdPart = 20;
		List<Particula> particulas = new ArrayList<Particula>();
		Enxame enxame;
		
		for (int i = 0; i < qtdPart; i++) {
			Particula p = new Particula();
			particulas.add(p);
		}
		enxame = new Enxame(particulas);
		
		Particula best = enxame.particleSwarmOptimization();
		
		System.out.println("A mínimo global encontrado é: " + best.bFit);
	}

}
