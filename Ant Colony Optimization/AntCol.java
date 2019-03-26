import java.util.ArrayList;

public class AntCol{
	
	public static int rodadas = 40;
	public static int formigas = 20;
	
	// Controle de evapora��o
	public static double p = 0.8;

	// Matriz de Rastro (Tij)
	public static MatrizRastro mRastro;
	
	// Matriz de atualiza��o (Dij)
	public static MatrizAtualizacao mAtualizacao;
	
	
	public static void main(String [] args){
		/* Defini��o do grafo a ser usado */

		//Inicializa��o dos v�rtices
		Vertice v0=new Vertice(0);
		Vertice v1=new Vertice(1);
		Vertice v2=new Vertice(2);
		Vertice v3=new Vertice(3);
		Vertice v4=new Vertice(4);
		Vertice v5=new Vertice(5);
		Vertice v6=new Vertice(6);

		// Inicializa��o das adjac�ncias
		v0.addVerticesAdjacentes(new Vertice[]{v1,v3});
		v1.addVerticesAdjacentes(new Vertice[]{v0,v2,v3,v5});
		v2.addVerticesAdjacentes(new Vertice[]{v1,v5});
		v3.addVerticesAdjacentes(new Vertice[]{v0,v1,v4});
		v4.addVerticesAdjacentes(new Vertice[]{v3,v5,v6});
		v5.addVerticesAdjacentes(new Vertice[]{v1,v2,v4,v6});
		v6.addVerticesAdjacentes(new Vertice[]{v4,v5});
		
		// Array de v�rtices para inicializar a matriz
		Vertice[] vetVertices={v0,v1,v2,v3,v4,v5,v6};
		
		// Lista Geral de Vertices V
		ArrayList<Vertice> V = new ArrayList<Vertice>();
		
		//Adiciona os v�rtices � lista V
		addVerticesLista(V,vetVertices);
		/* Fim da defini��o do grafo */
		
		/* Inicializa��o da Matriz de Rastro Tij */
		MatrizRastro mRastro = new MatrizRastro(vetVertices);
		mRastro.preencheRastro();
		
		// Lista para guardar solu��es anteriores (necess�rio para atualizar Tij)
		ArrayList<ArrayList<ArrayList<Vertice>>> solucoes = new ArrayList<ArrayList<ArrayList<Vertice>>>();
		
		for(int ciclos = 1; ciclos <= rodadas; ciclos++){
			/* Inicializa��o da Matriz de Atualiza��o Dij */
			mAtualizacao = new MatrizAtualizacao(vetVertices);
			mAtualizacao.preencheAtualizacao();
			
			//Lista para guardar as classes de cores na solu��o
			ArrayList<ArrayList<Vertice>> classesDeCores = new ArrayList<ArrayList<Vertice>>();
			
			for(int ii = 0; ii < formigas; ii++){
				// Defini��o do conjunto de v�rtices n�o coloridos (X)
				ArrayList<Vertice> naoColoridos = new ArrayList<Vertice>(V);
				
				// Cor
				int k = 0;
				
				while(naoColoridos.size() != 0){
					
					k=k+1;
					
					// Classe de cor atual, vazia inicialmente
					ArrayList<Vertice> classeCores = new ArrayList<Vertice>();
					
					// Conjunto F de v�rtices (F = v�rtices que ainda podem receber K)
					ArrayList<Vertice> vElegiveis = new ArrayList<Vertice>(naoColoridos);
					
					// Seleciona um v�rtice
					Vertice i = selecionaVertice(vElegiveis);
					
					// Colore o v�rtice selecionado
					colorir(i,k,naoColoridos,classeCores,vElegiveis);
					
					while(vElegiveis.size()!=0){
						i = selecionaVertice(vElegiveis);
						colorir(i,k,naoColoridos,classeCores,vElegiveis);
					}
					
					// Adiciona a nova classe de cor � lista de cores
					classesDeCores.add(classeCores);
				}
				// A formiga terminou de colorir o grafo
				System.out.println("A formiga terminou de colorir o grafo, a solu��o foi: " + classesDeCores);

				// Adiciona esta solu��o � lista de solu��es
				solucoes.add(classesDeCores);

				// Atualiza a Matriz Delta (dij)
				atualizaMatrizDelta(classesDeCores,k);

				// Reinicia a lista de cores
				classesDeCores = new ArrayList<ArrayList<Vertice>>();
			}
			// Atualiza a Matriz de Rastro (Tij), como no artigo
			atualizaTrailMatrix(solucoes,mRastro);
		}

		// DESCOMENTAR PARA VER TODAS AS SOLU��ES
		//System.out.println("As solu��es foram: " + solucoes);
		System.out.println("\n");
		//System.out.println("A Matriz Delta final (Dij) foi:" + mAtualizacao);
		System.out.println("A Matriz de Rastro final (Tij) foi:" + mRastro);
		// Solu��o final:
		System.out.println("Solu��o final:\n" + solucoes.get(solucoes.size()-1));
	}
	
	// M�todo Externo para atualizar a Matriz de Atualiza��es (Trail Update Matrix) dij
	public static void atualizaMatrizDelta(ArrayList<ArrayList<Vertice>> clasescolor,int k){
		double k1=k;
		for(int i=0;i<clasescolor.size();i++){
			mAtualizacao.atualizaMatrizAtualizacao(clasescolor.get(i),k1);
		}
	}
	
	// M�todo para colorir
	public static void colorir(Vertice i,int k,ArrayList<Vertice> X,ArrayList<Vertice> Ck,ArrayList<Vertice> F){
		X.remove(i);
		Ck.add(i);
		// Remove i e seus vizinhos de F
		removeVertices(i,F);
	}
	
	// Adiciona os v�rtices de um Array a lista recebida
	public static void addVerticesLista(ArrayList<Vertice> lista,Vertice[] arrayvertices){
		for(int i=0;i<arrayvertices.length;i++){
			lista.add(arrayvertices[i]);
		}
	}
	
	// Seleciona um v�rtice de maneira aleat�ria uniformemente
	public static Vertice selecionaVertice(ArrayList<Vertice> F){
		int max=F.size()*100;
		int index=(int)((Math.random()*max)%F.size());
		return F.get(index);
	}
	
	// M�todo para remover de F I e seus vizinhos
	public static void removeVertices(Vertice i,ArrayList<Vertice> F){
		F.remove(i);
		for(int ii=0;ii<i.adjacentes.size();ii++){
			Vertice j=i.adjacentes.get(ii);
			F.remove(j);
		}
	}
	
	/* M�todos para atualiza��o de Tij */
	
	// Recebe uma lista de v�rtices e busca pelo ID recebido, retorna true se encontrar e false se n�o
	public static boolean contem(ArrayList<Vertice> vertices,int id){
		for(int i=0;i<vertices.size();i++){
			if(vertices.get(i).id==id){
				return true;
			}
		}
		return false;
	}
	
	// Recebe uma lista de listas de vertices e uma colora��o dos v�rtices
	// Busca 2 v�rtices em uma solu��o (colora��o) 
	// Se encontrar, retorna double = 1/q(s)=1/coloracion.size()
	// Tij = pTij+Sigma(1/q(s)) para todo s:= � uma solu��o onde i e j tem = cor
	public static double sigma(ArrayList<ArrayList<Vertice>> coloracao, int v1, int v2){
		// Se nesta solu��o os v�rtices c1 e c2 tem a mesma cor, retorna 1/q(s), ou, 1/coloracao.size()
		double conta = 0;
		for (int i = 0; i < coloracao.size(); i++){
			if (contem(coloracao.get(i),v1) && contem(coloracao.get(i),v2) && v1 != v2){
				conta++;
			}
		}
		//1/q(s)
		return conta/coloracao.size();
	}
	
	// Recebe uma lista de listas de v�rtices/solu��es
	// Busca solu��es para atualizar Matriz de Rastro (Tij) 
	public static void atualizaTrailMatrix (ArrayList solucoes, MatrizRastro mRastro){
		// Percorre a Matriz de Rastros atualizando as entradas Tij
		for (int k = 0; k < solucoes.size(); k++){
			// Seleciona a solu��o K
			ArrayList coloracao = (ArrayList)solucoes.get(k);
			for(int i = 0; i < mRastro.rastro.length;i++){
				// Percorre para para atualizar a entrada Tij
				for(int j=0;j<mRastro.rastro.length;j++){
					// Procura por solu��es onde i e j tenham a mesma cor para atualizar a entrada Tij
					double factor=sigma(coloracao, i, j);
					//tij=ptij+Sigma(1/q)
					mRastro.rastro[i][j] = (p*mRastro.rastro[i][j]) + factor;
					
				}
			}
		}
	}
}