package controller;

import java.util.ArrayList;

import model.domain.Estado;
import model.domain.Grupo;

public class Conversor {
	private ArrayList<Grupo> gruposR = new ArrayList<Grupo>();
	private ArrayList<Grupo> gruposS = new ArrayList<Grupo>();
	private ArrayList<Grupo> gruposTemp = new ArrayList<Grupo>();
	private Grupo grupoEstados, grupoEstadosY;
	
	public Conversor(Grupo grupo)
	{
		this.grupoEstados = grupo;
	}
	
	public void gerarAFDMinimo()
	{
		int indiceEstado = 0;
		Estado estadoE;
		grupoEstadosY = new Grupo();
		
		do
		{
			System.out.println("Inicio repetição");
			Grupo.imprimirGrupos(gruposS);
			gruposR.clear();
			Grupo.adicionarGrupoFinais(grupoEstados, gruposR);
			Grupo.adicionarGrupoNormais(grupoEstados, gruposR);
			gruposS = Grupo.copiarGrupos(gruposR);
			gruposR.clear();
			for(int i=0; i < gruposS.size(); i++){
				Grupo grupo = Grupo.copiarGrupo(gruposS.get(i));
				indiceEstado = 0;
				do 
				{
					//System.out.println("IndiceEstado: " + indiceEstado + " TamGrupo: " + grupo.getEstados().size());
					
					estadoE = grupo.getEstados().get(0);
					grupoEstadosY = new Grupo();
					grupoEstadosY.adicionarEstado(estadoE);
					grupoEstadosY.setNome(Grupo.gerarNomeGrupo(grupoEstadosY));
					for (Estado estadoD : grupo.getEstados()) {
						if(estadoD != estadoE)
						{
							if(Estado.estadosSaoIguais(estadoE, estadoD))
							{
								grupoEstadosY.adicionarEstado(estadoD);
								grupoEstadosY.setNome(Grupo.gerarNomeGrupo(grupoEstadosY));
							}
						}
					}
					
					for (Estado estadoARemover : grupoEstadosY.getEstados()) {
						grupo.getEstados().remove(estadoARemover);
					}

					grupo.setNome(Grupo.gerarNomeGrupo(grupo));
					if(grupo.getEstados().isEmpty())
						gruposR.remove(grupo);
					gruposR.add(grupoEstadosY);
					Grupo.imprimirGrupos(gruposR);
					indiceEstado++;
				} while (grupo.getEstados().size() != 0);
			}
			
			converterGruposParaEstados();
			/*System.out.println("AFD convertido para estados: ");
			Grupo.imprimirGrupo(grupoEstados);*/
			
			//System.out.println("Houve diferença? " + houverDiferenca(gruposS, grupoEstados));
			
		}while(houverDiferenca(gruposS, grupoEstados));
		
		System.out.println("AFD Minimizado: ");
		Grupo.imprimirGrupos(gruposR);
		Grupo.imprimirGrupo(grupoEstados);
	}
	
	
	private boolean houverDiferenca(ArrayList<Grupo> gruposAnterior, Grupo grupoEstadosAtual)
	{
		boolean eDiferente = true;
		// Crio um novo conjunto de grupo com os novos estados (grupos convertidos para estados)
		ArrayList<Grupo> gruposTemp = new ArrayList<Grupo>();
		Grupo.adicionarGrupoFinais(grupoEstadosAtual, gruposTemp);
		Grupo.adicionarGrupoNormais(grupoEstadosAtual, gruposTemp);
		
		// Compara os dois conjuntos de grupo
		if(gruposTemp.size() == gruposAnterior.size())
		{
			//System.out.println("Tem a mesma quantida de grupos");
			int contGruposIguais = 0;
			for(int i=0; i < gruposAnterior.size(); i++)
			{
				if(gruposTemp.get(i).getEstados().size() == gruposAnterior.get(i).getEstados().size())
				{
					//System.out.println("Tem a mesma quantidade de estados no groupo " + i);
					contGruposIguais++;
				}
			}
			
			if(contGruposIguais == gruposTemp.size())
				eDiferente = false;
		}
		
		/*System.out.println("GruposS: ");
		Grupo.imprimirGrupos(gruposAnterior);
		
		System.out.println("Grupos Temp: ");
		Grupo.imprimirGrupos(gruposTemp);*/
		
		return eDiferente;
	}
	
	
	// Converte um conjunto de grupos para um conjunto de estados
	private void converterGruposParaEstados()
	{
		grupoEstados.getEstados().clear();
		
		// Percorro cada grupo
		for(int i=0; i < gruposR.size(); i++)
		{
			Estado primeiroEstado = gruposR.get(i).getEstados().get(0);
			// Percorro todas as transi��es do primeiro estado
			for(int j=0; j < primeiroEstado.getTransicoes().size(); j++)
			{
				Estado estadoApontado = primeiroEstado.getTransicoes().get(j).getEstado();
				Grupo grupoApontado = Grupo.buscarEstadoNosGrupos(estadoApontado, gruposR);
				
				if(grupoApontado.getEstados().size() >= 2)
				{
					Estado primeiroEstadoDoGrupoApontado = grupoApontado.getEstados().get(0);
					primeiroEstado.getTransicoes().get(j).setEstado(primeiroEstadoDoGrupoApontado);
				}	
			}
			
			grupoEstados.getEstados().add(primeiroEstado);
			
		}
		grupoEstados.setNome(Grupo.gerarNomeGrupo(grupoEstados));
	}	
	
}
