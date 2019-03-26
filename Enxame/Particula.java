package enxame;

import java.util.Random;

public class Particula {
	double x, y, velocidade, bFit;
	int id=0;
	Random r = new Random();
	
	public Particula() {
		x = (-15.0) + ((-5.0) - (-15.0)) * r.nextDouble();
		y = (-3.0) + ((3.0) - (-3.0)) * r.nextDouble();
		id++;
		System.out.println(" Gerado a partícula [" + id + "] nas coordenadas: (" + x + "," + y + ")");
	}
	
	public double fitness(Particula p) {
		double term1, term2;
		term1 = 100 * Math.sqrt(Math.abs(y - 0.01 * x*x));
		term2 = 0.01 * Math.abs(x + 10);
		
		return term1 + term2;
	}
}
