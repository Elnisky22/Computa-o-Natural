// Classe que modela a Matriz de Rastros (Tij)
public class MatrizRastro{
	public Vertice[] vertices;
	
	// Representa a Matriz de Rastro dos vértices;
	public double[][] rastro;
	
	public MatrizRastro(Vertice[] vertices){
		this.vertices = vertices;
		this.rastro = new double[vertices.length][vertices.length];
	}
	
	// Preenche a Matriz de Rastro Tij e inicializa
	public void preencheRastro(){
		for(int i = 0; i < vertices.length;i++){
			for(int j = 0; j < vertices.length; j++){
				// 1 se i e j não forem adjacentes
				if(!vertices[i].adjacente(vertices[j])) rastro[i][j] = 1;
			}
		}
	}
	
	public String toString(){
		String temp = "\n";
		for(int i = 0; i < rastro.length; i++){
			for(int j = 0; j < rastro.length; j++){
				System.out.printf(" %.2f ,", rastro[i][j]);
			}
			System.out.println();
		}
		return temp;
	}
}