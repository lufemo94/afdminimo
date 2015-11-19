package model.domain;

import java.util.ArrayList;

public class Grupo {
	private String nome;
	private ArrayList<Estado> estados = new ArrayList<Estado>();
	private boolean grupoIniciais = false, grupoFinais = false; 
	
	public static ArrayList<Grupo> copiarGrupos(ArrayList<Grupo> gruposOrigem)
	{
		ArrayList<Grupo> grupos = new ArrayList<Grupo>();
		for(int i=0; i < gruposOrigem.size(); i++)
		{
			grupos.add(copiarGrupo(gruposOrigem.get(i)));
		}
		return grupos;
	}
	
	public static Grupo copiarGrupo(Grupo grupoOrigem)
	{
		Grupo grupo = new Grupo();
		grupo.setNome(grupoOrigem.getNome());
		grupo.setGrupoFinais(grupoOrigem.isGrupoFinais());
		grupo.setGrupoIniciais(grupoOrigem.isGrupoIniciais());
		
		for(int i=0; i < grupoOrigem.getEstados().size(); i++)
		{
			grupo.getEstados().add(grupoOrigem.getEstados().get(i));
			
		}
		return grupo;
	}
	
	// Cria um grupo para os estados finais e adiciona ele no conjunto de grupos
	public static void adicionarGrupoFinais(Grupo grupoEstados, ArrayList<Grupo> grupos)
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
		finais.setNome(Grupo.gerarNomeGrupo(finais));
		grupos.add(finais);
	}
	
	// Cria um grupo para os estados normais (inicial ou n�o) e adiciona ele no conjunto de grupos
	public static void adicionarGrupoNormais(Grupo grupoEstados, ArrayList<Grupo> grupos)
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
		normal.setNome(Grupo.gerarNomeGrupo(normal));
		grupos.add(normal);
	}
	
	// Busca um estado no conjunto de grupos e retorna o grupo que ele est� contido
	public static Grupo buscarEstadoNosGrupos(Estado estado, ArrayList<Grupo> grupos)
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
	
	
	// Cria um novo grupo com somente um estado
	public static Grupo criarNovoGrupo(Estado estadoA)
	{
		Grupo grupo = new Grupo();
		grupo.getEstados().add(estadoA);
		grupo.setNome(Grupo.gerarNomeGrupo(grupo));
		grupo.setGrupoIniciais(estadoA.isEstadoInicial());
		grupo.setGrupoFinais(estadoA.isEstadoFinal());
		return grupo;
	}
	
	// Cria um novo grupo com dois estados
	public static Grupo criarNovoGrupo(Estado estadoA, Estado estadoB)
	{
		Grupo grupo = new Grupo();
		grupo.getEstados().add(estadoA);
		grupo.getEstados().add(estadoB);
		grupo.setNome(Grupo.gerarNomeGrupo(grupo));
		// Verifica se � um grupo inicial
		if(estadoA.isEstadoInicial() || estadoB.isEstadoInicial())
			grupo.setGrupoIniciais(true);
		// Verifica se � um grupo final
		grupo.setGrupoFinais(estadoA.isEstadoFinal());
		
		return grupo;
	}
	
	// Gera o nome do grupo de acordo com os valores dos estados
	public static String gerarNomeGrupo(Grupo grupo)
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
	
	// Imprime todos os estados(valores e transi��es) de um determinado grupo
	public static void imprimirGrupo(Grupo grupo)
	{
		for (Estado estado : grupo.getEstados()) {
			Estado.imprimirEstado(estado);
		}
	}
	
	// Imprime o nome dos grupos com o prefixo I(inicial) ou F(final) 
	public static void imprimirGrupos(ArrayList<Grupo> grupos)
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

	
	public String toString()
	{
		return this.nome;
	}
	
	public boolean isGrupoIniciais() {
		return grupoIniciais;
	}

	public void setGrupoIniciais(boolean grupoIniciais) {
		this.grupoIniciais = grupoIniciais;
	}

	public boolean isGrupoFinais() {
		return grupoFinais;
	}

	public void setGrupoFinais(boolean grupoFinais) {
		this.grupoFinais = grupoFinais;
	}

	public void adicionarEstado(Estado estado)
	{
		this.estados.add(estado);
	}
	
	public ArrayList<Estado> getEstados() {
		return estados;
	}

	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
}
