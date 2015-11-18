package controller;

import java.util.ArrayList;

import model.domain.Estado;
import model.domain.Grupo;

public class Conversor {
	private ArrayList<Grupo> gruposR = new ArrayList<Grupo>();
	private ArrayList<Grupo> gruposS = new ArrayList<Grupo>();
	private Grupo grupoEstados, grupoEstadosY;
	
	public Conversor(Grupo grupo)
	{
		this.grupoEstados = grupo;
	}
	
	public void gerarAFDMinimo()
	{

		Grupo.adicionarGrupoFinais(grupoEstados, gruposR);
		Grupo.adicionarGrupoNormais(grupoEstados, gruposR);
		Grupo.imprimirGrupos(gruposR);
		
		int indiceEstado = 0;
		Estado estadoE;
		grupoEstadosY = new Grupo();
		do
		{
			Grupo.copiarGrupos(gruposR, gruposS);
			for (Grupo grupo : gruposS) {
				indiceEstado = 0;
				do 
				{
					System.out.println("IndiceEstado: " + indiceEstado + " TamGrupo: " + grupo.getEstados().size());
					
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
			boolean teste = gruposR.equals(gruposS);
			System.out.println("valor de teste: " + teste);
		}while(!gruposR.equals(gruposS));
		
	}
	
	private Estado proximoEstado(Estado estadoAnterior, Grupo grupo)
	{
		if(grupo.getEstados().contains(estadoAnterior))
		{
			int indiceAnterior = grupo.getEstados().indexOf(estadoAnterior);
			
			if(indiceAnterior+1 < grupo.getEstados().size())
			{
				return grupo.getEstados().get(indiceAnterior+1);
			}
		}
		else if(estadoAnterior == null)
		{
			if(grupo.getEstados().size() >= 1)
				return grupo.getEstados().get(0);
		}
		
		System.out.println("Próximo estado retornando NULL");
		return null;
	}
	
	private boolean compararGrupos(ArrayList<Grupo> gruposR, ArrayList<Grupo> gruposS)
	{
		return true;
	}
	
	
	// Gera o AFD mínimo
	public void gerarAFDMinimoAntigo()
	{
		boolean estaReduzidoMax = false;
		int qtdEstadosAnterior = 0, qtdEstadosAtual = 0;
		
		/* Repete até encontrar o AFD mínimo, ou seja, a quantidade de estados do loop
		 * anterior vai ser igual na próxima
		 */
		int contVoltas = 1;
		do
		{
			System.out.println("-----------------["+contVoltas+"]-----------------");
			qtdEstadosAnterior = grupoEstados.getEstados().size();
			gruposR.clear();
			Grupo.adicionarGrupoFinais(grupoEstados, gruposR);
			Grupo.adicionarGrupoNormais(grupoEstados, gruposR);
			Grupo.imprimirGrupos(gruposR);
			agruparGruposIguais();
			Grupo.imprimirGrupos(gruposR);
			converterGruposParaEstados();
			qtdEstadosAtual =  grupoEstados.getEstados().size();
			
			System.out.println("Qtd. de estados anterior(" + qtdEstadosAnterior + ") x atual(" + qtdEstadosAtual+")");
			contVoltas++;
			estaReduzidoMax = true;
		}while(qtdEstadosAnterior > qtdEstadosAtual);
		
		System.out.println("\n\nAFD Mínimo: ");
		System.out.print("Grupo: ");
		Grupo.imprimirGrupos(gruposR);
		System.out.println("Estados: ");
		Grupo.imprimirGrupo(grupoEstados);
	}
	
	// Converte um conjunto de grupos para um conjunto de estados
	private void converterGruposParaEstados()
	{
		grupoEstados.getEstados().clear();
		
		// Percorro cada grupo
		for(int i=0; i < gruposR.size(); i++)
		{
			Estado primeiroEstado = gruposR.get(i).getEstados().get(0);
			// Percorro todas as transições do primeiro estado
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
		System.out.println(grupoEstados);
		Grupo.imprimirGrupo(grupoEstados);
		
	}
	

	// Faz o agrupamento de estados iguais e diferentes
	private void agruparGruposIguais()
	{
		boolean eDiferente = true;
		// Percorre todos os grupos
		for(int i=0; i < gruposR.size(); i++)
		{
			eDiferente = true;
			// Percorre todos os estados do grupo
			for(int j=0; j < gruposR.get(i).getEstados().size(); j++)
			{
				Estado estadoA = gruposR.get(i).getEstados().get(j);
				
				for(int k=0; k < gruposR.get(i).getEstados().size(); k++)
				{
					Estado estadoB = gruposR.get(i).getEstados().get(k);
					
					// Um estado não pode comparar com ele mesmo
					if(estadoA != estadoB)
					{
						System.out.println("Comparo " + estadoA + " e " + estadoB + 
										   " = " + Estado.estadosSaoIguais(estadoA, estadoB));
						if(Estado.estadosSaoIguais(estadoA, estadoB))
						{
							// Se só tiver os dois estados no grupo, deixa eles
							// Se tiver mais estados no grupo, cria um grupo para os dois
							// Deve verificar se o grupo vai ser inicial ou final
							eDiferente = false;
							
							if(gruposR.get(i).getEstados().size() > 2)
							{
								gruposR.get(i).getEstados().remove(j);
								gruposR.get(i).getEstados().remove(k);
								gruposR.get(i).setNome(Grupo.gerarNomeGrupo(gruposR.get(i)));
								gruposR.add(Grupo.criarNovoGrupo(estadoA, estadoB));
							}
							
						}
					}
				}
				
				if(eDiferente)
				{
					// Remove o estado desse grupo
					// Se tiver só o estado, deixa ele
					// Se tiver mais estados no grupo, cria um grupo para ele
					if(gruposR.get(i).getEstados().size() > 1)
					{
						gruposR.get(i).getEstados().remove(j);
						gruposR.get(i).setNome(Grupo.gerarNomeGrupo(gruposR.get(i)));
						gruposR.add(Grupo.criarNovoGrupo(estadoA));
					}
	
				}
			}
		}
	}
	
	
}
