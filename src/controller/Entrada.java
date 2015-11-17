package controller;

import java.util.ArrayList;

import model.dao.Arquivo;
import model.domain.Constantes;
import model.domain.Estado;
import model.domain.Grupo;
import model.domain.Transicao;

public class Entrada {
	private ArrayList<Estado> estados = new ArrayList<Estado>();
	private ArrayList<Grupo> grupos = new ArrayList<Grupo>();
	
	// Faz a leitura de um arquivo
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
	
	// Busca o AFD no conteúdo do arquivo
	public void buscarAFD()
	{
		String afdString = lerArquivo("inputs/input.txt");
		afdString = afdString.replace(Constantes.BREAK_LINE1, "").replace(Constantes.BREAK_LINE2, "");
		
		String[] stringSeparada = afdString.split(Constantes.SEMICOLON);
		
		String[] stringEstados = separarColunas(stringSeparada[0]);
		String[] stringAlfabeto = separarColunas(stringSeparada[1]);
		String[] stringTransacoes = separarColunas(stringSeparada[2], Constantes.COMMA);
		String[] stringEstadosIniciais = separarColunas(stringSeparada[3]);
		String[] stringEstadosFinais = separarColunas(stringSeparada[4]);
		
		adicionarEstados(stringEstados);
		adicionarEstadosIniciais(stringEstadosIniciais);
		adicionarEstadosFinais(stringEstadosFinais);
		adicionarTransicoes(stringTransacoes, stringAlfabeto);
		
		
		converter();
	}
	
	// Converte o AFD para AFD mínimo
	private void converter()
	{
		// Gera o grupo com todos os estados
		Grupo grupo = new Grupo();
		for(int i=0; i < estados.size(); i++)
		{
			grupo.adicionarEstado(estados.get(i));
		}
		
		Conversor conversor = new Conversor(grupo);
		conversor.gerarAFDMinimo();
	}
	
	// Adiciona um estado
	private void adicionarEstados(String[] stringEstados)
	{
		for (String estadoString : stringEstados) {
			estados.add(new Estado(estadoString));
			System.out.println("Adicionou o estado " + estadoString);
		}
	}
	
	// Adiciona os estado iniciais
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
	
	// Adiciona os estados finais
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
	
	// Adiciona transições
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
	
	// Busca um estado
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
	
	
	// Separa as colunas pelo espaço
	public String[] separarColunas(String linha)
	{
		String[] stringSeparada = linha.split(Constantes.SPACE);
		return stringSeparada;
	}
	
	// Separa as colunas por algum separador
	public String[] separarColunas(String linha, String separador)
	{
		String[] stringSeparada = linha.split(separador);
		return stringSeparada;
	}
	
	
}
