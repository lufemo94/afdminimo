package model.domain;

public class Transicao {
	private Estado estado;
	private String valorTransicao;
	
	public Transicao(Estado estado, String valorTransicao)
	{
		this.estado = estado;
		this.valorTransicao = valorTransicao;
	}
	


	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getValorTransicao() {
		return valorTransicao;
	}

	public void setValorTransicao(String valorTransicao) {
		this.valorTransicao = valorTransicao;
	}
	
	
	
}
