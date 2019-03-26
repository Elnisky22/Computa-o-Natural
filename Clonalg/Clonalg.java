import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clonalg{
	int[] espacoBusca;
	int nGens;
	int populacao;
	double fator;
	int rand;
	int qtdMelhores;
	
	// Construtor do Clonalg
	public Clonalg(int[] espacoBusca, int nGens, int populacao, double fator, int rand, int qtdMelhores) {
		this.espacoBusca = espacoBusca;
		this.nGens = nGens;
		this.populacao = populacao;
		this.fator = fator;
		this.rand = rand;
		this.qtdMelhores = qtdMelhores;
	}
	
	// Método principal, de execução do Clonalg
	public AntiCorpo clonalg(){
		List<AntiCorpo> anticorpos = gerarAnticorpos(populacao);
		List<AntiCorpo> melhorescorpos = AntiCorpo.getMelhores(anticorpos, qtdMelhores);
		List<AntiCorpo> clones;
		
		int i=0;
		while (i < nGens) {
			i++;
			
			AntiCorpo.atualizaFitness(anticorpos);
			melhorescorpos = AntiCorpo.getMelhores(anticorpos, qtdMelhores);
			
			AntiCorpo.atualizaFitness(melhorescorpos);
			clones = clonar(melhorescorpos, fator, qtdMelhores);
			
			AntiCorpo.atualizaFitness(clones);
			clones = hipermutar(clones, fator);
			
			anticorpos = atualizar(anticorpos, clones, qtdMelhores);
			
			mostrar(anticorpos, i);
		}
		
		return anticorpos.get(0);
	}
	
	// Método para mostrar os resultados
	public void mostrar(List<AntiCorpo> a, int i) {
		for (int j=0; j<a.size(); j++) {
			//System.out.println("Interação: " + i + " | Anticorpo: " + j + " | x= " + a.get(j).x + " y=" + a.get(j).y);
			System.out.printf("Iteração %d\t anticorpo: %d \t x= %f \t y= %f\n", i,j,a.get(j).x,a.get(j).y);
		}
		System.out.println();
	}
	
	// Método para atualizar a lista de anticorpos
	public List<AntiCorpo> atualizar(List<AntiCorpo> anticorpos, List<AntiCorpo> clones, int qtdMelhores){
		//System.out.println("Atualizando a lista com os anticorpos mais os clones...");
		for (int i=0; i < clones.size(); i++) {
			anticorpos.add(clones.get(i));
		}
		return AntiCorpo.getMelhores(anticorpos, populacao);
	}
	
	// Método para clonar anticorpos
	public List<AntiCorpo> clonar(List<AntiCorpo> melhorescorpos, double fator, int qtdMelhores){
		List<AntiCorpo> clones = new ArrayList<AntiCorpo>();
		int nc = (int) Math.floor(fator * qtdMelhores);
		
		for (int i=0; i < nc; i++) {
			//System.out.println("Adicionando o anticorpo " + i + ": x= " + melhorescorpos.get(i).x + ", y= " + melhorescorpos.get(i).y);
			clones.add(melhorescorpos.get(i));
		}
		return clones;
	}
	
	// Método para gerar anticorpos aleatoriamente
	public List<AntiCorpo> gerarAnticorpos(int populacao){
		List<AntiCorpo> anticorpos = new ArrayList<AntiCorpo>();
		Random x = new Random();
		
		System.out.println("Gerando anticorpos...");
		for (int i=0; i<populacao; i++) {
			AntiCorpo ant = new AntiCorpo(x.nextDouble() * 10);
			ant.x = ant.x - 5;
			ant.y = ant.fitness(ant.x);
			
			System.out.println(i + " Adicionando à lista: [" + ant.x + "," + ant.y + "]");
			anticorpos.add(ant);
		}
		System.out.println();
		return anticorpos;
	}
	
	// Método para hipermutação
	public List<AntiCorpo> hipermutar(List<AntiCorpo> clones, double fator){
		Random rand = new Random();
		//float f = (float) fator;
		
		for (int i=0; i < clones.size(); i++) {
			double probabilidade = Math.exp((fator*-1) * (1 / clones.get(i).y));
			double r = rand.nextDouble();
			if (r < (2.25 * probabilidade)) {
				int pos1 = rand.nextInt(clones.size()-1);
				clones.remove(pos1);
				
				int ra = rand.nextInt(10) - 5;
				AntiCorpo a = new AntiCorpo(ra);
				clones.add(a);
			}
		}
		return AntiCorpo.atualizaFitness(clones);
	}
	
}
