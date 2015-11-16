package controller;

import java.util.ArrayList;

import model.dao.Arquivo;
import model.domain.Estado;
import model.domain.Grupo;
import model.domain.Transicao;

public class Entrada {
	private ArrayList<Estado> estados = new ArrayList<Estado>();
	private ArrayList<Grupo> grupos = new ArrayList<Grupo>();
	
	private String lerArquivo(String arquivo)
	{
		String conteudo = null;
		try {
			conteudo = Arquivo.lerArquivo(arquivo);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return conteudo;
	}
	
	public void buscarAFD()
	{
		String afdString = lerArquivo("inputs/input.txt");
		afdString = afdString.replace("\n", "").replace("\r", "");
		
		String[] stringSeparada = afdString.split(";");
		
		String[] stringEstados = separarColunas(stringSeparada[0]);
		String[] stringAlfabeto = separarColunas(stringSeparada[1]);
		String[] stringTransacoes = separarColunas(stringSeparada[2], ",");
		String[] stringEstadosIniciais = separarColunas(stringSeparada[3]);
		String[] stringEstadosFinais = separarColunas(stringSeparada[4]);
		
		adicionarEstados(stringEstados);
		adicionarEstadosIniciais(stringEstadosIniciais);
		adicionarEstadosFinais(stringEstadosFinais);
		adicionarTransicoes(stringTransacoes, stringAlfabeto);
		
		
		converter();
	}
	
	private void converter()
	{
		// Gera o grupo com todos os estados
		Grupo grupo = new Grupo();
		for(int i=0; i < estados.size(); i++)
		{
			/*System.out.println("Estado: " + estados.get(i).getValor() +
							   ": 0->"+estados.get(i).getTransicoes().get(0).getEstado().getValor()+
							   " 1->"+estados.get(i).getTransicoes().get(1).getEstado().getValor());
			*/
			
			grupo.adicionarEstado(estados.get(i));
		}
		
		Conversor conversor = new Conversor(grupo);
		conversor.gerarAFDMinimo();
	}
	
	private void adicionarEstados(String[] stringEstados)
	{
		for (String estadoString : stringEstados) {
			estados.add(new Estado(estadoString));
			System.out.println("Adicionou o estado " + estadoString);
		}
	}
	
	private void adicionarEstadosIniciais(String[] stringEstadosIniciais)
	{	
		// Verifico todos os estados
		for(int i=0; i < estados.size(); i++)
		{
			// Verifico todos os estados inicias
			for(int j=0; j < stringEstadosIniciais.length; j++)
			{
				if(estados.get(i).getValor().equals(stringEstadosIniciais[j]))
				{
					estados.get(i).setEstadoInicial(true);
				}
			}
		}	
	}
	
	private void adicionarTransicoes(String[] stringLinhaTransacoes, String[] stringAlfabeto)
	{
		
		for(int i=0; i < estados.size(); i++)
		{
			System.out.println("Estado "+i+": "+ estados.get(i).getValor());
		}
		
		// Percorro as linhas das transicoes
		for(int i=0; i < stringLinhaTransacoes.length; i++)
		{
			// Separo as colunas
			String[] stringColTransacoes = stringLinhaTransacoes[i].split(" ");
			
			// Percorro as colunas
			for(int j=0; j < stringColTransacoes.length; j++)
			{	
				int indice = buscarEstado(stringColTransacoes[j]);
				Estado aponta = estados.get(indice);
				estados.get(i).adicionarTransicao(aponta, stringAlfabeto[j]);
			}
			
		}
		
	}
	
	private int buscarEstado(String valor)
	{
		for(int i=0; i < estados.size(); i++)
		{
			if(estados.get(i).getValor().equals(valor)){
				return i;
			}
		}
		return 0;
	}
	
	private void adicionarEstadosFinais(String[] stringEstadosFinais)
	{
		// Verifico todos os estados
		for(int i=0; i < estados.size(); i++)
		{
			// Verifico todos os estados finais
			for(int j=0; j < stringEstadosFinais.length; j++)
			{
				if(estados.get(i).getValor().equals(stringEstadosFinais[j]))
				{
					estados.get(i).setEstadoFinal(true);
				}
			}
		}
	}
	
	public String[] separarColunas(String linha)
	{
		String[] stringSeparada = linha.split(" ");
		return stringSeparada;
	}
	
	public String[] separarColunas(String linha, String separador)
	{
		String[] stringSeparada = linha.split(separador);
		return stringSeparada;
	}
	
	
}
