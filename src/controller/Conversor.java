package controller;

import java.util.ArrayList;

import model.domain.Estado;
import model.domain.Grupo;

public class Conversor {
	private ArrayList<Grupo> grupos = new ArrayList<Grupo>();
	private ArrayList<Grupo> gruposTemp = new ArrayList<Grupo>();
	private Grupo grupoEstados;
	
	public Conversor(Grupo grupo)
	{
		this.grupoEstados = grupo;
	}
	
	public void gerarAFDMinimo()
	{

		
		boolean estaReduzidoMax = false;
		do
		{
			adicionarGrupoFinais();
			adicionarGrupoNormais();
			imprimirGrupos(grupos);
			agruparGruposIguais();
			estaReduzidoMax = true;
		}while(!estaReduzidoMax);
	}
	
	private void agruparGruposIguais()
	{
		
		// Percorre todos os grupos
		for(int i=0; i < grupos.size(); i++)
		{
			// Percorre todos os estados do grupo
			for(int j=0; j < grupos.get(i).getEstados().size(); j++)
			{
				
			}
		}
	}
	
	private boolean estadosSaoIguais(Estado estadoA, Estado estadoB)
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
	
	private void imprimirGrupos(ArrayList<Grupo> grupos)
	{
		for(int i=0; i < grupos.size(); i++)
			System.out.print(grupos.get(i));
		System.out.println();
	}
	
	public void adicionarGrupoFinais()
	{
		Grupo finais = new Grupo();
		String nomeGrupo = "{";
		int cont = 0;
		finais.setGrupoFinais(true);
		
		for(int i=0; i < grupoEstados.getEstados().size(); i++)
		{
			Estado estado = grupoEstados.getEstados().get(i);
			
			if(estado.isEstadoFinal())
			{
				if(estado.isEstadoInicial())
				{
					finais.setGrupoIniciais(true);
				}
				
				finais.adicionarEstado(estado);
				
				if(cont == 0)
					nomeGrupo = nomeGrupo.concat(estado.getValor());
				else
					nomeGrupo = nomeGrupo.concat("," + estado.getValor());
				
				cont++;
			}			
		}
		
		nomeGrupo = nomeGrupo.concat("}");
		finais.setNome(nomeGrupo);
		grupos.add(finais);
	}
	
	public void adicionarGrupoNormais()
	{
		Grupo normal = new Grupo();
		String nomeGrupo = "{";
		int cont = 0;
		
		for(int i=0; i < grupoEstados.getEstados().size(); i++)
		{
			Estado estado = grupoEstados.getEstados().get(i);
			
			if(!estado.isEstadoFinal())
			{
				if(estado.isEstadoInicial())
				{
					normal.setGrupoIniciais(true);
				}
				
				normal.adicionarEstado(estado);
				
				if(cont == 0)
					nomeGrupo = nomeGrupo.concat(estado.getValor());
				else
					nomeGrupo = nomeGrupo.concat("," + estado.getValor());
				
				cont++;
			}
		}
		nomeGrupo = nomeGrupo.concat("}");
		normal.setNome(nomeGrupo);
		grupos.add(normal);
	}
	
}
