package br.com.poli.calculaScore;

import br.com.poli.jogoconfig.Partida;
import br.com.poli.usuarios.Jogador;

public interface CalculaScore {
	
	void calcula(Partida partida, Jogador jogador);
}
