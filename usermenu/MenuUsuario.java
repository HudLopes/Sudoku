package br.com.poli.usermenu;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import br.com.poli.jogoconfig.DificuldadePartida;
import javax.swing.ImageIcon;

//Criado com o WindowBuilder
public class MenuUsuario {

	private JFrame frame;
	private JTextField nome;
	private JTextField idade;
	private JLabel lblDificuldade;
	private final ButtonGroup buttonGroup = new ButtonGroup();
    private int idadeUsuario;
    private String nomeUsuario;
    
    DificuldadePartida dificuldade1 = DificuldadePartida.FACIL;
	DificuldadePartida dificuldade2 = DificuldadePartida.MEDIO;
	DificuldadePartida dificuldade3 = DificuldadePartida.DIFICIL;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuUsuario window = new MenuUsuario();
					window.frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuUsuario() {
		initialize();
	}

	/**
	 * Valida numero do campo de texto da Idade, conferindo se há algo diferente de número 
	 * @param Numero
	 * @return
	 */
	public boolean validaNumero(JTextField Numero) {
		int valor;
		if (Numero.getText().length() != 0){
			try {
				valor = Integer.parseInt(Numero.getText());
			}catch(NumberFormatException ex){
				return false;
			}
		}
		return true;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Sudoku Projeto LPOO");
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 550);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnIniciarPartida = new JButton("Iniciar Partida");
		
		lblDificuldade = new JLabel("Dificuldade");
		lblDificuldade.setBounds(77, 383, 72, 14);
		frame.getContentPane().add(lblDificuldade);
		
		JRadioButton rdbtnFacil = new JRadioButton("Facil");
		buttonGroup.add(rdbtnFacil);
		rdbtnFacil.setBounds(153, 379, 54, 23);
		frame.getContentPane().add(rdbtnFacil);
		
		JRadioButton rdbtnMedio = new JRadioButton("Medio");
		buttonGroup.add(rdbtnMedio);
		rdbtnMedio.setBounds(211, 379, 60, 23);
		frame.getContentPane().add(rdbtnMedio);
		
		JRadioButton rdbtnDificil = new JRadioButton("Dificil");
		buttonGroup.add(rdbtnDificil);
		rdbtnDificil.setBounds(275, 379, 60, 23);
		frame.getContentPane().add(rdbtnDificil);
		
		JLabel lbldigiteUmaIdade = new JLabel("* Digite uma idade valida");
		lbldigiteUmaIdade.setForeground(Color.BLACK);
		lbldigiteUmaIdade.setBounds(242, 350, 167, 14);
		frame.getContentPane().add(lbldigiteUmaIdade);
		lbldigiteUmaIdade.setVisible(false);
		
		JLabel lblDigiteUm = new JLabel("* Digite um nome");
		lblDigiteUm.setForeground(Color.BLACK);
		lblDigiteUm.setBounds(242, 318, 167, 14);
		frame.getContentPane().add(lblDigiteUm);
		lblDigiteUm.setVisible(false);
		
		JLabel lblSelecioneUma = new JLabel("* Selecione uma dificuldade");
		lblSelecioneUma.setForeground(Color.BLACK);
		lblSelecioneUma.setBounds(143, 407, 165, 14);
		frame.getContentPane().add(lblSelecioneUma);
		lblSelecioneUma.setVisible(false);
		
		JLabel lblJogador = new JLabel("Jogador");
		lblJogador.setBounds(77, 318, 54, 14);
		frame.getContentPane().add(lblJogador);
		
		JLabel lblIdade = new JLabel("Idade");
		lblIdade.setBounds(77, 350, 46, 14);
		frame.getContentPane().add(lblIdade);
		
		nome = new JTextField();
		nome.setBounds(133, 315, 86, 20);
	    frame.getContentPane().add(nome);	
		nome.setColumns(10);
		
		idade = new JTextField();
		idade.setBounds(133, 347, 86, 20);
		frame.getContentPane().add(idade);
		idade.setColumns(10);
	
		btnIniciarPartida.addActionListener(new ActionListener() { //ação do botão iniciar partida
			public void actionPerformed(ActionEvent arg0) {

				//Regras dos campos de texto
				if(nome.getText().isEmpty())
				{ // Campo de texto do nome vazio
					JOptionPane.showMessageDialog(null, "Preencha seu nome" ,"Informação",JOptionPane.INFORMATION_MESSAGE);
					nome.grabFocus();
				} 
				else if(idade.getText().isEmpty())
				{ // Campo de texto da idade vazia
					JOptionPane.showMessageDialog(null, "Preencha sua idade" ,"Informação",JOptionPane.INFORMATION_MESSAGE);
					idade.grabFocus();
				} else if(validaNumero(idade)==false)
				{ // Campo de texto da idade sem ser numeros
					JOptionPane.showMessageDialog(null, "O campo idade só aceita números" ,"Informação",JOptionPane.INFORMATION_MESSAGE);
					idade.grabFocus();
				} else if(Integer.parseInt(idade.getText())<1 || Integer.parseInt(idade.getText())>100 )
				{ // campo de texto da idade acima 100 ou menor que 1
					JOptionPane.showMessageDialog(null, "Preencha uma idade valida (1 à 100)" ,"Informação",JOptionPane.INFORMATION_MESSAGE);
					idade.grabFocus();
				}
				else if(!rdbtnFacil.isSelected() && !rdbtnMedio.isSelected() && !rdbtnDificil.isSelected())
				{ // nenhuma dificuldade selecionada
					JOptionPane.showMessageDialog(null, "Selecione uma dificuldade" ,"Informação",JOptionPane.INFORMATION_MESSAGE);
				} 
				else 
				{
					idadeUsuario=Integer.parseInt(idade.getText());
					nomeUsuario = nome.getText();
					if(rdbtnFacil.isSelected()){ //inicia jogo com a dificuldade facil
						JogoSudoku novoJogo = new JogoSudoku(9,9, nomeUsuario, idadeUsuario, dificuldade1);
						novoJogo.start();
					} else if (rdbtnMedio.isSelected()) { //inicia jogo com a dificuldade medio
						JogoSudoku novoJogo = new JogoSudoku(9,9, nomeUsuario, idadeUsuario, dificuldade2);
						novoJogo.start();
					} else { //inicia jogo com a dificuldade dificil
						JogoSudoku novoJogo = new JogoSudoku(9,9, nomeUsuario, idadeUsuario, dificuldade3);
						novoJogo.start();
					}
					frame.dispose(); //fecha o frame atual
				} 
			}
		});
		btnIniciarPartida.setBounds(173, 432, 140, 23);
		frame.getContentPane().add(btnIniciarPartida);
		
		//Label da logo central
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Hudson\\Pictures\\logo2.jpg"));
		lblNewLabel.setBounds(28, 41, 421, 245);
		frame.getContentPane().add(lblNewLabel);
	}
	
}
