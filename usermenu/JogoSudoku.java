package br.com.poli.usermenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.com.poli.calculaScore.CalculaScoreComIdade;
import br.com.poli.exceptions.MovimentoIncorretoException;
import br.com.poli.exceptions.MovimentoInvalidoException;
import br.com.poli.jogoconfig.DificuldadePartida;
import br.com.poli.jogoconfig.Partida;
import br.com.poli.jogoconfig.Tabuleiro;
import br.com.poli.usuarios.Jogador;

import javax.swing.JFrame; //imports JFrame library
import javax.swing.Box;
import javax.swing.JButton; //imports JButton library
import java.awt.GridLayout; //imports GridLayout library
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoSudoku extends Thread {
 
	private JTextField[][] grid;
	private Tabuleiro tabu = new Tabuleiro();
	private Jogador jogador;
	private Partida partida;
	private CalculaScoreComIdade calcula;
	private JLabel label;
	private int nuMin=0, nuSeg=0, nuHora=0;
    private int contador=5;

	public JogoSudoku(int width, int length, String nome, int idade, DificuldadePartida dificuldade){ 
		super();	
		jogador= new Jogador( nome, idade);
		partida= new Partida(jogador, dificuldade, tabu);
		partida.iniciaPartida();
		calcula = new CalculaScoreComIdade();
		initialize(width, length);
	}

	public void initialize(int width, int length){

		//frame principal
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout(); // layout principal
		GridLayout conteudo = new GridLayout(9,9,4,4); // layout onde ficar� o tabuleiro
		JPanel painel = new JPanel(layout);
		JPanel painelcentro = new JPanel(conteudo);

		//Painel e bot�es ao norte
		JPanel botoes = new JPanel();
		botoes.setBackground(Color.WHITE);
		JButton desistir = new JButton("Desistir"); 
		botoes.add(desistir);
		JButton ajuda = new JButton("Ajuda");
		botoes.add(ajuda);
		JLabel nomeUsuario = new JLabel();
		botoes.add(nomeUsuario);
		nomeUsuario.setText("   Jogador: "+jogador.getNome()); //exibe o nome do jogador no canto superior direito
		JLabel idadeUsuario = new JLabel();
		botoes.add(idadeUsuario);
		idadeUsuario.setText("   Idade: "+jogador.getIdade()); //exibe a idade no canto superior esquerdo
		
		/**
		 * Bot�o ajuda: Chama o metodo executaMovimento e preenche a primeira posi��o vazia que encontrar(la�o for)
		 * Possui um contador interno, ao usar-lo 5 vezes o bot�o � desabilitado
		 */
		ajuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	

				if(contador==1){ //checando se o ajuda ja foi usada 5 vezes
					JOptionPane.showMessageDialog(null, "Voc� usou sua �ltima ajuda" ,"Informa��o",JOptionPane.INFORMATION_MESSAGE);
					ajuda.setEnabled(false); //desabilita o bot�o ajuda
				} else {
					contador--; // contador incrementando cada vez que o "ajuda" seja usado
					JOptionPane.showMessageDialog(null, "Voc� usou uma ajuda, restam: "+ contador ,"Informa��o",JOptionPane.INFORMATION_MESSAGE);
				}
				first: 
					for(int y=0; y<length; y++) // pecorre 81 posi��es 
					{
						for(int x=0; x<width; x++)
						{
							if(tabu.getGrid(x, y)==0) // procura a primeira posi��o em branco
							{ 
								executaMovimentoInterface(x,y);
								break first; //para a execu��o do primeiro FOR
							}
						}
					}
			} 
		});

		/**
		 * Bot�o Desistir: Chama o metodo resolveTabuleiro da classe tabuleiro, recebendo como parametro a grid
		 * e depois atribui todos aos campos os valores obtidos
		 */
		desistir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabu.resolveTabuleiro(tabu.getGridResolve()); // resolve o grid
				setAllTextField(width, length); // preenche todos os grids 
				setAllTextFieldsDisable(width, length); // desabilita todos os textfields
				desistir.setEnabled(false); // desabilita o bot�o desistir
				ajuda.setEnabled(false); // desabilita o bot�o ajuda	

				partida.isFimDeJogo();
				calcula.calcula(partida,jogador);

				JOptionPane.showMessageDialog(null, "Fim da partida. \nSua pontua��o foi: " + calcula.getScore() + "\nTempo de jogo: "+nuHora+":"+nuMin+":"+nuSeg,"Informa��o",JOptionPane.INFORMATION_MESSAGE);
			}
		});

		painel.add(botoes, BorderLayout.NORTH); // adiciona bot�es ao norte

		//label do cronometro - Painel do sul
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		painel.add(label, BorderLayout.SOUTH); // adiciona label do cronometro ao sul do borderlayout
		painel.setBackground(Color.white);

		painel.add(painelcentro, BorderLayout.CENTER); //adiciona o painel do tabuleiro
		frame.getContentPane().add(painel);

		//adicionar TextField ao GridLayout

		grid=new JTextField[9][9]; //alocando tamanho da grid
		for(int y=0; y<width; y++){  //la�o "for" pecorrendo 81 posi��es
			for(int x=0; x<length; x++){
				grid[x][y]=new JTextField(); // criando 81 bot�es

				painelcentro.add(grid[x][y]); //adicionando bot�es ao painel do centro

				if(tabu.getGrid(x, y)!=0){
					grid[x][y].setText(String.valueOf(tabu.getGrid(x, y))); //textfield recebendo valor da grid    
				} else {
					//caso o valor da grid seja 0, n�o recebe nada, criando um textfield vazio
				}
				grid[x][y].setHorizontalAlignment(SwingConstants.CENTER); // alinhando o texto ao centro
				grid[x][y].setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));

				if (grid[x][y].getText().equals(Integer.toString(tabu.getGabarito(x, y)))){ // checando em rela��o ao gabarito se a posi��o est� preenchida corretamente
					grid[x][y].setEnabled(false); //desabilitando o textfield que ja possuem valores
				}

				/**
				 * Parte de entrada no tabuleiro: Ao digitar e apertar enter, chama o executaMovimento da classe partida.
				 * caso o movimento seja v�lido subtitui o valor dentro da grid da classe tabuleiro e o 
				 * textfield � desabilitado.
				 */

				if(tabu.getGrid(x, y)==0){ //caso o valor na grid seja 0, o textfield recebe um action listener
					grid[x][y].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							first: 
								for(int y=0; y<width; y++){
									for(int x=0; x<length; x++){
										try {
											if(partida.isFimDeJogo()==true){ // testa se existe alguma posi��o vazia ou se ja esgotou a quantidade de erro
												calcula.calcula(partida, jogador); // calcula score do jogador
												JOptionPane.showMessageDialog(null, "Fim de jogo,Sua pontua��o foi: " + calcula.getScore() + "\nTempo de jogo: "+nuHora+":"+nuMin+":"+nuSeg ,"Informa��o",JOptionPane.INFORMATION_MESSAGE);
												setAllTextFieldsDisable(width, length); // desabilita todos os textifields (esgotar quantidade de erros)
												desistir.setEnabled(false);
												ajuda.setEnabled(false);
												break first;
											}
											partida.executaMovimento(x, y, Integer.parseInt(grid[x][y].getText())); //executa movimento
											if(tabu.getGrid(x, y)!=0){ //caso o movimento tenha sido feito, checa se � diferente de 0 e exibe mensagem de movimento correto
												grid[x][y].setEnabled(false);
												grid[x][y].setBackground(Color.white);
												JOptionPane.showMessageDialog(null, "Movimento Correto\nTempo de Jogo: "+nuHora+":"+nuMin+":"+nuSeg ,"Informa��o",JOptionPane.INFORMATION_MESSAGE);
											}
										} catch (NumberFormatException  e1) {

										} catch (MovimentoIncorretoException e1) {
											JOptionPane.showMessageDialog(null, "Movimento Incorreto\nQuantidade de erro restantes: " + partida.getQuantidadeErros()+ "\nTempo de jogo: "+nuHora+":"+nuMin+":"+nuSeg ,"Informa��o",JOptionPane.INFORMATION_MESSAGE);
											break first;
										} catch (MovimentoInvalidoException e1) {

										}

									}
								}
						}
					}
							);
				}
			}

		}

		//frame.pack();
		frame.setLocation(200, 200);
		frame.setSize(500, 550);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Sudoku Projeto LPOO");
	}
	/**
	 * Metodo usando thread responsavel pela impress�o do cronometro
	 */

	public void run() {
		//Cronometro  
		try {
			for (; ;){ //inicio do for infinito      
				if(nuSeg!=59) {//caso n�o seja o ultimo segundo
					nuSeg++; //incrementa segundo                               
				}else{
					if(nuMin!=59){//caso nao seja o ultimo minuto
						nuSeg=0;//atribui 0 ao valor dos segundos
						nuMin++;//incrementa minuto
					}else{//incremento de horas
						nuHora++;
						nuMin=0;//atribui 0 ao minuto
						nuSeg=0;//atribui 0 ao segundo        
					}
				}   
				label.setText("Tempo de Jogo: "+nuHora+":"+nuMin+":"+nuSeg); // imprime o cronometro no label do sul
				sleep(999);
			}//fim do for infinito            
		} catch (Exception ex) {
			//nada
		}                 
	} 

	/**
	 * Usado quando o usuario desistir do jogo ou quando ele atingir o numero maximo de erro
	 * pecorre todos os textfields e os desabilita
	 */
	private void setAllTextFieldsDisable(int width, int length)
	{
		for(int y=0; y<width; y++){
			for(int x=0; x<length; x++){
				grid[x][y].setEnabled(false);
			}
		}
	}

	/**
	 * Preenche todos os textFields com seus respectivos valores na grid
	 */
	private void setAllTextField(int width, int length){
		for(int y=0; y<length; y++){
			for(int x=0; x<width; x++){
				grid[x][y].setText(String.valueOf(tabu.getGrid(x, y)));	 // seta todas as posi��es n�o preenchidas
			}
		}
	}

	private void executaMovimentoInterface(int x, int y){
		try {
			partida.executaMovimento(x, y, tabu.getGabarito(x, y));	// executa o movimento na posi��o achada
		} catch (MovimentoIncorretoException | MovimentoInvalidoException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		grid[x][y].setText(String.valueOf(tabu.getGrid(x, y))); // atribui valor a posi��o
		grid[x][y].setBackground(Color.white); //caso o usuario jogue deixando o campo vermelho e pe�a ajuda, ele retorna ao branco
		grid[x][y].setEnabled(false); // desabilita o textfield
	}
	
}