package model.domain;

import java.util.ArrayList;

public class Grupo {
	private String nome;
	private ArrayList<Estado> estados = new ArrayList<Estado>();
	private boolean grupoIniciais = false, grupoFinais = false; 
	
	
	public String toString()
	{
		return this.nome;
	}
	
	public boolean isGrupoIniciais() {
		return grupoIniciais;
	}

	public void setGrupoIniciais(boolean grupoIniciais) {
		this.grupoIniciais = grupoIniciais;
	}

	public boolean isGrupoFinais() {
		return grupoFinais;
	}

	public void setGrupoFinais(boolean grupoFinais) {
		this.grupoFinais = grupoFinais;
	}

	public void adicionarEstado(Estado estado)
	{
		this.estados.add(estado);
	}
	
	public ArrayList<Estado> getEstados() {
		return estados;
	}

	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
}
