package br.com.poli.usuarios;

public class Jogador {

	
    private int idade;
	private String nome;

	public Jogador(String nome, int idade) 
	{
		super();
		this.idade = idade;
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}
	public String getNome() {
		return nome;
	}
}
