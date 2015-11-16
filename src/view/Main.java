package view;

import controller.Entrada;
import model.dao.Arquivo;
import model.domain.Estado;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Entrada controller = new Entrada();
		
		Estado estadoA = new Estado("A", true, false);
		Estado estadoB = new Estado("B", false, true);
		estadoA.adicionarTransicao(estadoB, "1");
		estadoA.adicionarTransicao(estadoB, "0");
		estadoB.adicionarTransicao(estadoA, "0");
		estadoB.adicionarTransicao(estadoA, "1");
		
		//System.out.print("0 1");
		//estadoA.printTransicoes();
		//estadoB.printTransicoes();
		
		controller.buscarAFD();
	}

}
