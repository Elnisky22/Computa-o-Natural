
public class ClonalgApp {

	public static void main(String[] args) {
		// Vari�veis de controle
		int[] espacoBusca = {-5, 5};
		int nGens = 25;
		int populacao = 20;
		double fator = 0.5;
		int rand = 2;
		int qtdMelhores = 5;
		
		// Execu��o do algoritmo
		Clonalg clonalg = new Clonalg(espacoBusca, nGens, populacao, fator, rand, qtdMelhores); // Gera o Clonalg com as vari�veis de controle
		AntiCorpo best = clonalg.clonalg(); // Recebe o melhor anticorpo
		
		System.out.println("\n\nO melhor anticorpo �: x=" + best.x + " | f(x)= " + best.y);
	}

}
