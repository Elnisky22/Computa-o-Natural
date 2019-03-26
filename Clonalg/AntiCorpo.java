import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class AntiCorpo implements Comparable<AntiCorpo>{
	double x,y;
	
	// Construtor do Anticorpo
	public AntiCorpo(double x) {
		this.x = x;
	}
	
	// Método para calcular o fitness (f(x)) do Anticorpo
	public double fitness(double x) {
		double result = 0;
		for (int i=0; i < 3; i++) {
			result += Math.pow(x,2.0);
		}
		return result;
	}
	
	// Método para atualizar o fintess de uma lista de anticorpos
	public static List<AntiCorpo> atualizaFitness(List<AntiCorpo> a){
		for (int i=0; i< a.size();i++) {
			a.get(i).y = a.get(i).fitness(a.get(i).x);
		}
		return a;
	}
	
	@Override
	public int compareTo(AntiCorpo a) {
		if (Math.abs(this.y) < Math.abs(a.y)) return -1;
		if (Math.abs(this.y) > Math.abs(a.y)) return 1;
		return 0;
	}
	
	// Método para selecionar os 5 melhores anticorpos
	public static List<AntiCorpo> getMelhores(List<AntiCorpo> anticorpos, int qtdMelhores){
		List<AntiCorpo> auxiliares = new ArrayList<AntiCorpo>();
		
		//System.out.println("\nOrganizando os melhores...");
		Collections.sort(anticorpos);
		
		for (int i=0; i< qtdMelhores; i++) {
			//System.out.println("Anticorpo " + i + ": [" + anticorpos.get(i).x + ";" + anticorpos.get(i).y + "]");
			auxiliares.add(anticorpos.get(i));
		}
		return auxiliares;
	}
	
	/*public List<AntiCorpo> mutacao(List<AntiCorpo> a, int pos1, int pos2){
		
	}*/
}
