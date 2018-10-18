package br.com.poli.calculaScore;

import br.com.poli.jogoconfig.DificuldadePartida;
import br.com.poli.jogoconfig.Partida;
import br.com.poli.usermenu.JogoSudoku;
import br.com.poli.usuarios.Jogador;

public class CalculaScoreComIdade implements CalculaScore {

	private long score;
	private Partida partida;
	private long tempo;
	private JogoSudoku jogo;

	/* Calcula partida com idade
	   Dificuldade da partida vezes 100 dividido pela idade do jogador
	   Mais o tempo de duração do jogo menos o numero de erros vezes 10 */

	@Override
	public void calcula(Partida partida, Jogador jogador) 
	{
		tempo=(partida.getTempoFinal()-partida.getTempoInicial())/1000;
		int tempoInt = (int) tempo;

		score = ((partida.getValorDif()*300)+(partida.getQuantidadeErros()*5))-tempoInt - jogador.getIdade();
		//System.out.println("Seu Score foi:" + score);
	}
	public long getScore(){
		return score;
	}
}
