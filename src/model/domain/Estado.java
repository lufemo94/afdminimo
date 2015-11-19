package model.domain;

import java.util.ArrayList;

public class Estado {
	private String valor;
	private boolean estadoInicial, estadoFinal;
	private ArrayList<Transicao> transicoes = new ArrayList<Transicao>();
	
	public Estado(String valor, boolean estadoInicial, boolean estadoFinal) {
		this.valor = valor;
		this.estadoInicial = estadoInicial;
		this.estadoFinal = estadoFinal;
	}
	
	
	public static Estado copiarEstado(Estado estado)
	{
		Estado novoEstado = new Estado(estado.getValor());
		
		return novoEstado;
	}
	
	
	// Imprime o valor e as transições do estado
	public static void imprimirEstado(Estado estado)
	{
		System.out.print("Estado '" + estado + "' ");
		if(estado.isEstadoFinal())
			System.out.print("(F)");
		if(estado.isEstadoInicial())
			System.out.print("(I)");
		System.out.print(": ");
		
		for(int i=0; i < estado.getTransicoes().size(); i++)
		{
			System.out.print(estado.getTransicoes().get(i).getValorTransicao() + "->'"+
							   estado.getTransicoes().get(i).getEstado()+"' ");
		}
		System.out.println();
	}
	
	// Verifica se os dois estados são iguais (possuem as mesmas transições)
	public static boolean estadosSaoIguais(Estado estadoA, Estado estadoB)
	{
		/* Percorre todas as transições (considerando que todos os estados tem a mesma
		 * quantidade de transições e usam todo o alfabeto)
		*/
		boolean saoIguais = true;
		for(int i=0; i < estadoA.getTransicoes().size(); i++)
		{
			String ligacaoDeA = estadoA.getTransicoes().get(i).getEstado().getValor();
			String ligacaoDeB = estadoB.getTransicoes().get(i).getEstado().getValor();
			if(!ligacaoDeA.equals(ligacaoDeB))
				saoIguais = false;
		}
		return saoIguais;
	}
	
	public Estado(String valor) {
		this.valor = valor;
	}
	
	public void adicionarTransicao(Estado estado, String valorTransicao)
	{
		transicoes.add(new Transicao(estado, valorTransicao));
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(boolean estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public boolean isEstadoFinal() {
		return estadoFinal;
	}

	public void setEstadoFinal(boolean estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

	public ArrayList<Transicao> getTransicoes() {
		return transicoes;
	}

	public void setTransicoes(ArrayList<Transicao> transicoes) {
		this.transicoes = transicoes;
	}
	
	public void printTransicoes()
	{
		System.out.println();
		for (Transicao transicao : transicoes) {
			System.out.print(transicao.getEstado().valor + " ");
		}

	}
	
	public String toString()
	{
		return this.valor;
	}
}
