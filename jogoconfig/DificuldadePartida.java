package br.com.poli.jogoconfig;

public enum DificuldadePartida {

	FACIL(1,20,40), MEDIO(2,15,45), DIFICIL(3,10,50);

	private int valor;
	private int quantidadeMaximaErro;
	private int gridRemover;

	DificuldadePartida(int valor, int quantidadeMaximaErro, int gridRemover)
	{
		this.valor = valor;
		this.quantidadeMaximaErro = quantidadeMaximaErro;
		this.gridRemover = gridRemover;
	}

	// Métodos getters

	public int getValor()
	{
		return this.valor;
	}
	public int getGridRemover()
	{
		return this.gridRemover;
	}
	public int getQuantidadeMaximaErro()
	{
		return this.quantidadeMaximaErro;
	}
}
