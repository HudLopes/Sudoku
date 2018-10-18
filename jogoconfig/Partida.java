package br.com.poli.jogoconfig;

import br.com.poli.exceptions.MovimentoIncorretoException;
import br.com.poli.exceptions.MovimentoInvalidoException;
import br.com.poli.usuarios.Jogador;

public class Partida {

	private Jogador jogador;
	private Tabuleiro tabuleiro;
	private int quantidadeErros;
	private DificuldadePartida dificuldade;

	// Variaveis para Captura do tempo, que será usado para calculo do Score do jogador.

	private long tempoInicial;
	private long tempoFinal;

	public Partida(Jogador jogador,DificuldadePartida dificuldade, Tabuleiro tabuleiro)
	{
		super();
		this.jogador = jogador;
		this.dificuldade = dificuldade;
		this.tabuleiro = tabuleiro;
	}

	// Método recebendo as posições de array e valor para executar movimento.
	// Metodo lançando MovimentoInvalido, tratando MovimentoIncorreto e lançando MovimentoIncorreto novamente.


	public void executaMovimento(int x, int y, int valor) throws MovimentoIncorretoException, MovimentoInvalidoException
	{
		try 
		{
			if (tabuleiro.executaMovimento(x, y, valor) == true)
			{
				tabuleiro.isTabuleiroPreenchido();
			} 
		} 
		catch (MovimentoIncorretoException e) 
		{
			quantidadeErros--;
			throw new MovimentoIncorretoException();
		}	
	}

	// Metodo isFimDeJogo captura o tempo final e subtrai o tempo inicial, para ser usado no calculo de score

	public boolean isFimDeJogo()
	{
		if (quantidadeErros == 0)
		{
			tempoFinal = System.currentTimeMillis();
			return true; 
		} else if (tabuleiro.fimDeJogo()==true)
		{
			tempoFinal = System.currentTimeMillis();
			return true;
		}
		else
		{
			return false;
		}
	}

	// Metodo iniciaPartida captura o tempo inicial para ser subtraido pelo tempo final do jogo
	// no metodo isFimDeJogo
	// Date substituido por currentTimeMillis

	public void iniciaPartida()// throws MovimentoIncorretoException, MovimentoInvalidoException
	{
		tempoInicial = System.currentTimeMillis();
		quantidadeErros=dificuldade.getQuantidadeMaximaErro();
		tabuleiro.geraTabuleiro(dificuldade);
	}

	// Metodos Getters

	public long getTempoInicial() 
	{
		return tempoInicial;
	}
	public int getValorDif(){
		return dificuldade.getValor();
	}

	public long getTempoFinal() 
	{
		return tempoFinal;
	}

	public int getQuantidadeErros() 
	{
		return quantidadeErros;
	}
}
