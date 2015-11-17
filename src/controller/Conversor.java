package controller;

import java.util.ArrayList;

import model.domain.Estado;
import model.domain.Grupo;

public class Conversor {
	private ArrayList<Grupo> grupos = new ArrayList<Grupo>();
	private Grupo grupoEstados, grupoEstadosTemp;
	
	public Conversor(Grupo grupo)
	{
		this.grupoEstados = grupo;
	}
	
	// Gera o AFD mínimo
	public void gerarAFDMinimo()
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
			grupos.clear();
			Grupo.adicionarGrupoFinais(grupoEstados, grupos);
			Grupo.adicionarGrupoNormais(grupoEstados, grupos);
			Grupo.imprimirGrupos(grupos);
			agruparGruposIguais();
			Grupo.imprimirGrupos(grupos);
			converterGruposParaEstados();
			qtdEstadosAtual =  grupoEstados.getEstados().size();
			
			System.out.println("Qtd. de estados anterior(" + qtdEstadosAnterior + ") x atual(" + qtdEstadosAtual+")");
			contVoltas++;
			estaReduzidoMax = true;
		}while(qtdEstadosAnterior > qtdEstadosAtual);
		
		System.out.println("\n\nAFD Mínimo: ");
		System.out.print("Grupo: ");
		Grupo.imprimirGrupos(grupos);
		System.out.println("Estados: ");
		Grupo.imprimirGrupo(grupoEstados);
	}
	
	// Converte um conjunto de grupos para um conjunto de estados
	private void converterGruposParaEstados()
	{
		grupoEstados.getEstados().clear();
		
		// Percorro cada grupo
		for(int i=0; i < grupos.size(); i++)
		{
			Estado primeiroEstado = grupos.get(i).getEstados().get(0);
			// Percorro todas as transições do primeiro estado
			for(int j=0; j < primeiroEstado.getTransicoes().size(); j++)
			{
				Estado estadoApontado = primeiroEstado.getTransicoes().get(j).getEstado();
				Grupo grupoApontado = Grupo.buscarEstadoNosGrupos(estadoApontado, grupos);
				
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
		for(int i=0; i < grupos.size(); i++)
		{
			eDiferente = true;
			// Percorre todos os estados do grupo
			for(int j=0; j < grupos.get(i).getEstados().size(); j++)
			{
				Estado estadoA = grupos.get(i).getEstados().get(j);
				
				for(int k=0; k < grupos.get(i).getEstados().size(); k++)
				{
					Estado estadoB = grupos.get(i).getEstados().get(k);
					
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
							
							if(grupos.get(i).getEstados().size() > 2)
							{
								grupos.get(i).getEstados().remove(j);
								grupos.get(i).getEstados().remove(k);
								grupos.get(i).setNome(Grupo.gerarNomeGrupo(grupos.get(i)));
								grupos.add(Grupo.criarNovoGrupo(estadoA, estadoB));
							}
							
						}
					}
				}
				
				if(eDiferente)
				{
					// Remove o estado desse grupo
					// Se tiver só o estado, deixa ele
					// Se tiver mais estados no grupo, cria um grupo para ele
					if(grupos.get(i).getEstados().size() > 1)
					{
						grupos.get(i).getEstados().remove(j);
						grupos.get(i).setNome(Grupo.gerarNomeGrupo(grupos.get(i)));
						grupos.add(Grupo.criarNovoGrupo(estadoA));
					}
	
				}
			}
		}
	}
	
	
}
