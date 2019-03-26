import java.util.ArrayList;

// Classe que representa a Matriz de Atualiza��o de v�rtices
public class MatrizAtualizacao{
	public Vertice[] vertices;
	
	// Representa a Matriz de Atualiza��o dos v�rtices
	// � praticamente igual a de rastro, apenas trocam-se os valores ao iniciar
	public double[][] atualizacao;
	
	
	public MatrizAtualizacao(Vertice[] vertices){
		this.vertices = vertices;
		this.atualizacao = new double[vertices.length][vertices.length];
	}
	
	// Inicializa a Matriz de Atualiza��o, defini��o inicial como do artigo
	public void preencheAtualizacao(){
		for(int i=0;i<vertices.length;i++){
			for(int j=0;j<vertices.length;j++){ // 0 se i e j n�o forem adjacentes
				if(!vertices[i].adjacente(vertices[j])) atualizacao[i][j] = 0;
			}
		}
	}
	
	
	public void atualizaMatrizAtualizacao(ArrayList<Vertice> vertices, double k){
		for(int i=0;i<vertices.size();i++){
			Vertice tempi=vertices.get(i);
			for(int j=i;j<vertices.size();j++){
				// Se n�o s�o iguais, atualiza
				// N�o � necess�rio verificar a adjac�ncia dos v�rtices porque v�rtices adjacentes n�o podem estar na mesma classe de cor
				// pela forma que a classe de cor foi constru�da
				Vertice tempj=vertices.get(j);
				if (tempi != tempj){
					//System.out.println("entre al if de actualizaMatrizAct en MA");
					//System.out.println("k es "+k);
					//double f=1/k;
					//renglon, columna
					int r=tempi.id;
					int c=tempj.id;
					atualizacao[r][c]+=1/k;
				}
			}
		}
	}
	
	public String toString(){
		String temp="\n";
		for(int i=0;i<atualizacao.length;i++){
			for(int j=0;j<atualizacao.length;j++){
				System.out.printf(" %.2f ,", atualizacao[i][j]);
			}
			System.out.println();
		}
		return temp;
	}
}