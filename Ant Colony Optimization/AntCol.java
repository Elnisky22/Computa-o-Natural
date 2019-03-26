import java.util.ArrayList;

public class AntCol{
	
	public static int rodadas = 40;
	public static int formigas = 20;
	
	// Controle de evaporação
	public static double p = 0.8;

	// Matriz de Rastro (Tij)
	public static MatrizRastro mRastro;
	
	// Matriz de atualização (Dij)
	public static MatrizAtualizacao mAtualizacao;
	
	
	public static void main(String [] args){
		/* Definição do grafo a ser usado */

		//Inicialização dos vértices
		Vertice v0=new Vertice(0);
		Vertice v1=new Vertice(1);
		Vertice v2=new Vertice(2);
		Vertice v3=new Vertice(3);
		Vertice v4=new Vertice(4);
		Vertice v5=new Vertice(5);
		Vertice v6=new Vertice(6);

		// Inicialização das adjacências
		v0.addVerticesAdjacentes(new Vertice[]{v1,v3});
		v1.addVerticesAdjacentes(new Vertice[]{v0,v2,v3,v5});
		v2.addVerticesAdjacentes(new Vertice[]{v1,v5});
		v3.addVerticesAdjacentes(new Vertice[]{v0,v1,v4});
		v4.addVerticesAdjacentes(new Vertice[]{v3,v5,v6});
		v5.addVerticesAdjacentes(new Vertice[]{v1,v2,v4,v6});
		v6.addVerticesAdjacentes(new Vertice[]{v4,v5});
		
		// Array de vértices para inicializar a matriz
		Vertice[] vetVertices={v0,v1,v2,v3,v4,v5,v6};
		
		// Lista Geral de Vertices V
		ArrayList<Vertice> V = new ArrayList<Vertice>();
		
		//Adiciona os vértices à lista V
		addVerticesLista(V,vetVertices);
		/* Fim da definição do grafo */
		
		/* Inicialização da Matriz de Rastro Tij */
		MatrizRastro mRastro = new MatrizRastro(vetVertices);
		mRastro.preencheRastro();
		
		// Lista para guardar soluções anteriores (necessário para atualizar Tij)
		ArrayList<ArrayList<ArrayList<Vertice>>> solucoes = new ArrayList<ArrayList<ArrayList<Vertice>>>();
		
		for(int ciclos = 1; ciclos <= rodadas; ciclos++){
			/* Inicialização da Matriz de Atualização Dij */
			mAtualizacao = new MatrizAtualizacao(vetVertices);
			mAtualizacao.preencheAtualizacao();
			
			//Lista para guardar as classes de cores na solução
			ArrayList<ArrayList<Vertice>> classesDeCores = new ArrayList<ArrayList<Vertice>>();
			
			for(int ii = 0; ii < formigas; ii++){
				// Definição do conjunto de vértices não coloridos (X)
				ArrayList<Vertice> naoColoridos = new ArrayList<Vertice>(V);
				
				// Cor
				int k = 0;
				
				while(naoColoridos.size() != 0){
					
					k=k+1;
					
					// Classe de cor atual, vazia inicialmente
					ArrayList<Vertice> classeCores = new ArrayList<Vertice>();
					
					// Conjunto F de vértices (F = vértices que ainda podem receber K)
					ArrayList<Vertice> vElegiveis = new ArrayList<Vertice>(naoColoridos);
					
					// Seleciona um vértice
					Vertice i = selecionaVertice(vElegiveis);
					
					// Colore o vértice selecionado
					colorir(i,k,naoColoridos,classeCores,vElegiveis);
					
					while(vElegiveis.size()!=0){
						i = selecionaVertice(vElegiveis);
						colorir(i,k,naoColoridos,classeCores,vElegiveis);
					}
					
					// Adiciona a nova classe de cor à lista de cores
					classesDeCores.add(classeCores);
				}
				// A formiga terminou de colorir o grafo
				System.out.println("A formiga terminou de colorir o grafo, a solução foi: " + classesDeCores);

				// Adiciona esta solução à lista de soluções
				solucoes.add(classesDeCores);

				// Atualiza a Matriz Delta (dij)
				atualizaMatrizDelta(classesDeCores,k);

				// Reinicia a lista de cores
				classesDeCores = new ArrayList<ArrayList<Vertice>>();
			}
			// Atualiza a Matriz de Rastro (Tij), como no artigo
			atualizaTrailMatrix(solucoes,mRastro);
		}

		// DESCOMENTAR PARA VER TODAS AS SOLUÇÕES
		//System.out.println("As soluções foram: " + solucoes);
		System.out.println("\n");
		//System.out.println("A Matriz Delta final (Dij) foi:" + mAtualizacao);
		System.out.println("A Matriz de Rastro final (Tij) foi:" + mRastro);
		// Solução final:
		System.out.println("Solução final:\n" + solucoes.get(solucoes.size()-1));
	}
	
	// Método Externo para atualizar a Matriz de Atualizações (Trail Update Matrix) dij
	public static void atualizaMatrizDelta(ArrayList<ArrayList<Vertice>> clasescolor,int k){
		double k1=k;
		for(int i=0;i<clasescolor.size();i++){
			mAtualizacao.atualizaMatrizAtualizacao(clasescolor.get(i),k1);
		}
	}
	
	// Método para colorir
	public static void colorir(Vertice i,int k,ArrayList<Vertice> X,ArrayList<Vertice> Ck,ArrayList<Vertice> F){
		X.remove(i);
		Ck.add(i);
		// Remove i e seus vizinhos de F
		removeVertices(i,F);
	}
	
	// Adiciona os vértices de um Array a lista recebida
	public static void addVerticesLista(ArrayList<Vertice> lista,Vertice[] arrayvertices){
		for(int i=0;i<arrayvertices.length;i++){
			lista.add(arrayvertices[i]);
		}
	}
	
	// Seleciona um vértice de maneira aleatória uniformemente
	public static Vertice selecionaVertice(ArrayList<Vertice> F){
		int max=F.size()*100;
		int index=(int)((Math.random()*max)%F.size());
		return F.get(index);
	}
	
	// Método para remover de F I e seus vizinhos
	public static void removeVertices(Vertice i,ArrayList<Vertice> F){
		F.remove(i);
		for(int ii=0;ii<i.adjacentes.size();ii++){
			Vertice j=i.adjacentes.get(ii);
			F.remove(j);
		}
	}
	
	/* Métodos para atualização de Tij */
	
	// Recebe uma lista de vértices e busca pelo ID recebido, retorna true se encontrar e false se não
	public static boolean contem(ArrayList<Vertice> vertices,int id){
		for(int i=0;i<vertices.size();i++){
			if(vertices.get(i).id==id){
				return true;
			}
		}
		return false;
	}
	
	// Recebe uma lista de listas de vertices e uma coloração dos vértices
	// Busca 2 vértices em uma solução (coloração) 
	// Se encontrar, retorna double = 1/q(s)=1/coloracion.size()
	// Tij = pTij+Sigma(1/q(s)) para todo s:= é uma solução onde i e j tem = cor
	public static double sigma(ArrayList<ArrayList<Vertice>> coloracao, int v1, int v2){
		// Se nesta solução os vértices c1 e c2 tem a mesma cor, retorna 1/q(s), ou, 1/coloracao.size()
		double conta = 0;
		for (int i = 0; i < coloracao.size(); i++){
			if (contem(coloracao.get(i),v1) && contem(coloracao.get(i),v2) && v1 != v2){
				conta++;
			}
		}
		//1/q(s)
		return conta/coloracao.size();
	}
	
	// Recebe uma lista de listas de vértices/soluções
	// Busca soluções para atualizar Matriz de Rastro (Tij) 
	public static void atualizaTrailMatrix (ArrayList solucoes, MatrizRastro mRastro){
		// Percorre a Matriz de Rastros atualizando as entradas Tij
		for (int k = 0; k < solucoes.size(); k++){
			// Seleciona a solução K
			ArrayList coloracao = (ArrayList)solucoes.get(k);
			for(int i = 0; i < mRastro.rastro.length;i++){
				// Percorre para para atualizar a entrada Tij
				for(int j=0;j<mRastro.rastro.length;j++){
					// Procura por soluções onde i e j tenham a mesma cor para atualizar a entrada Tij
					double factor=sigma(coloracao, i, j);
					//tij=ptij+Sigma(1/q)
					mRastro.rastro[i][j] = (p*mRastro.rastro[i][j]) + factor;
					
				}
			}
		}
	}
}