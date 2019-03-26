import java.util.ArrayList;

// Classe que modela um v�rtice do grafo e sua lista de adjacencias
public class Vertice{
	// Identificador, deve ser �nico
	public int id;
	//Cor, inicialmente -1 (sem cor)
	public int color;
	
	// Lista de v�rtices adjacentes
	public ArrayList<Vertice> adjacentes;
	
	public Vertice(int id){
		this.id = id;
		this.color = -1;
		this.adjacentes = new ArrayList<Vertice>();
	}
	
	public Vertice(int id, int color){
		this.id = id;
		this.color = color;
		this.adjacentes = new ArrayList<Vertice>();
	}
	
	// Adiciona o v�rtice do par�metro como ajdacente ao v�rtice .this
	public void adicionaAdjacente(Vertice v){
		this.adjacentes.add(v);
	}
	
	// Adiciona os v�rtices do vetor par�metro como adjacentes de .this
	public void addVerticesAdjacentes(Vertice[] vertices){
		for(int i=0;i<vertices.length;i++){
			this.adjacentes.add(vertices[i]);
		}
	}
	
	public void setColor(int color){
		this.color=color;
	}
	
	public int getColor(){
		return this.color;
	}
	
	public int getId(){
		return this.id;
	}
	
	// Retorna verdadeiro se o v�rtice V � adjacente a .this e falso se n�o
	public boolean adjacente(Vertice v){
		return this.adjacentes.contains(v);
	}
	
	public String toString(){
		return "v"+id;
	}
}