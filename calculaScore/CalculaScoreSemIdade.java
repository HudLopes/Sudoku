package br.com.poli.calculaScore;

//import br.com.poli.jogoconfig.DificuldadePartida;
import br.com.poli.jogoconfig.Partida;
import br.com.poli.usuarios.Jogador;
//import br.com.poli.usuarios.Pessoa;

public class CalculaScoreSemIdade implements CalculaScore {

	private long score;
	//private Partida partida;
	//private Pessoa pessoa;
	private long tempo;
	
	/* Dificuldade da partida(numero de erros) vezes 100 mais 
	 Tempo de duração menos a quantidade de erros vezes 100 */
	
	@Override
	public void calcula(Partida partida, Jogador jogador) 
	{
		tempo=(partida.getTempoFinal()-partida.getTempoInicial())/1000;
		int tempoInt = (int) tempo;
		
		score = ( (partida.getValorDif()*1000)-(partida.getQuantidadeErros()*10))-tempoInt*2;
		System.out.println("Seu Score foi:" + score);
	}
	
}
