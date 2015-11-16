package controller;

import java.util.ArrayList;

import model.domain.Estado;
import model.domain.Grupo;

public class Conversor {
	private ArrayList<Grupo> grupos = new ArrayList<Grupo>();
	private Grupo grupoEstados;
	
	public Conversor(Grupo grupo)
	{
		this.grupoEstados = grupo;
	}
	
	public void gerarAFDMinimo()
	{
		adicionarGrupoFinais();
		adicionarGrupoNormais();
		
		for(int i=0; i < grupos.size(); i++)
		{
			System.out.println(grupos.get(i).getNome());
		}
		
		/*do
		{
			
		}while(false);*/
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
