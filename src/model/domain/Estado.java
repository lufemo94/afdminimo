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
