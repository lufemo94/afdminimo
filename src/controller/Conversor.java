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
	
	public void gerarAFDMinimo()
	{
		boolean estaReduzidoMax = false;
		int qtdEstadosAnterior = 0, qtdEstadosAtual = 0;
		
		do
		{
			qtdEstadosAnterior = grupoEstados.getEstados().size();
			adicionarGrupoFinais();
			adicionarGrupoNormais();
			imprimirGrupos(grupos);
			agruparGruposIguais();
			imprimirGrupos(grupos);
			converterGruposParaEstados();
			qtdEstadosAtual =  grupoEstados.getEstados().size();
			
			System.out.println(qtdEstadosAnterior + " x " + qtdEstadosAtual);
			
			estaReduzidoMax = true;
		}while(qtdEstadosAnterior > qtdEstadosAtual);
	}
	
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
				Grupo grupoApontado = buscarEstadoNosGrupos(estadoApontado);
				
				if(grupoApontado.getEstados().size() >= 2)
				{
					Estado primeiroEstadoDoGrupoApontado = grupoApontado.getEstados().get(0);
					primeiroEstado.getTransicoes().get(j).setEstado(primeiroEstadoDoGrupoApontado);
				}	
			}
			
			grupoEstados.getEstados().add(primeiroEstado);
			
		}
		grupoEstados.setNome(gerarNomeGrupo(grupoEstados));
		System.out.println(grupoEstados);
		
		for (Estado estado : grupoEstados.getEstados()) {
			imprimirEstado(estado);
		}
		
	}
	
	private void imprimirEstado(Estado estado)
	{
		System.out.print("Estado '" + estado + "': ");
		for(int i=0; i < estado.getTransicoes().size(); i++)
		{
			System.out.print(estado.getTransicoes().get(i).getValorTransicao() + "->'"+
							   estado.getTransicoes().get(i).getEstado()+"' ");
		}
		System.out.println();
	}
	
	private Grupo buscarEstadoNosGrupos(Estado estado)
	{
		
		for(int i=0; i < grupos.size(); i++)
		{
			if(grupos.get(i).getEstados().contains(estado))
			{
				return grupos.get(i);
			}
		}
		
		return null;
	}
	
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
										   " = " + estadosSaoIguais(estadoA, estadoB));
						if(estadosSaoIguais(estadoA, estadoB))
						{
							// Se só tiver os dois estados no grupo, deixa eles
							// Se tiver mais estados no grupo, cria um grupo para os dois
							// Deve verificar se o grupo vai ser inicial ou final
							eDiferente = false;
							
							if(grupos.get(i).getEstados().size() > 2)
							{
								grupos.get(i).getEstados().remove(j);
								grupos.get(i).getEstados().remove(k);
								grupos.get(i).setNome(gerarNomeGrupo(grupos.get(i)));
								grupos.add(criarNovoGrupo(estadoA, estadoB));
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
						grupos.get(i).setNome(gerarNomeGrupo(grupos.get(i)));
						grupos.add(criarNovoGrupo(estadoA));
					}
	
				}
			}
		}
	}
	
	private Grupo criarNovoGrupo(Estado estadoA)
	{
		Grupo grupo = new Grupo();
		grupo.getEstados().add(estadoA);
		grupo.setNome(gerarNomeGrupo(grupo));
		grupo.setGrupoIniciais(estadoA.isEstadoInicial());
		grupo.setGrupoFinais(estadoA.isEstadoFinal());
		return grupo;
	}
	
	private Grupo criarNovoGrupo(Estado estadoA, Estado estadoB)
	{
		Grupo grupo = new Grupo();
		grupo.getEstados().add(estadoA);
		grupo.getEstados().add(estadoB);
		grupo.setNome(gerarNomeGrupo(grupo));
		// Verifica se é um grupo inicial
		if(estadoA.isEstadoInicial() || estadoB.isEstadoInicial())
			grupo.setGrupoIniciais(true);
		// Verifica se é um grupo final
		grupo.setGrupoFinais(estadoA.isEstadoFinal());
		
		return grupo;
	}
	
	private String gerarNomeGrupo(Grupo grupo)
	{
		String nomeGrupo = "{";
		int cont = 0;
		for(int i=0; i < grupo.getEstados().size(); i++)
		{
			Estado estado = grupo.getEstados().get(i);
			if(cont == 0)
				nomeGrupo = nomeGrupo.concat(estado.getValor());
			else
				nomeGrupo = nomeGrupo.concat("," + estado.getValor());
			
			cont++;			
		}
			
		nomeGrupo = nomeGrupo.concat("}");
		return nomeGrupo;
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
		{
			if(grupos.get(i).isGrupoIniciais())
				System.out.print("I");
			if(grupos.get(i).isGrupoFinais())
				System.out.print("F");
			System.out.print(grupos.get(i));
		}
		System.out.println();
	}
	
	public void adicionarGrupoFinais()
	{
		Grupo finais = new Grupo();
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
			}			
		}
		finais.setNome(gerarNomeGrupo(finais));
		grupos.add(finais);
	}
	
	public void adicionarGrupoNormais()
	{
		Grupo normal = new Grupo();
		
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
			}
		}
		normal.setNome(gerarNomeGrupo(normal));
		grupos.add(normal);
	}
	
}
