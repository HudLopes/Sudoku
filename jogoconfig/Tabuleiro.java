package br.com.poli.jogoconfig;

import java.util.Random;

import br.com.poli.exceptions.*;

public class Tabuleiro implements ResolvedorSudoku {

	private int[][] gabarito = new int[9][9];
	private int[][] grid = new int[9][9];

	/**
	 * Metodo recebendo os valores das posições e o valor a ser preenchido no tabuleiro
	 * lançando exceções caso sejam maior 9 ou menor que 1, e lançando execeção caso seja
	 * diferente do valor na mesma posição do gabarito.
	 * @return
	 */

	public boolean executaMovimento(int x, int y, int valor) throws  MovimentoIncorretoException, MovimentoInvalidoException
	{
		if (x>8 || x<0 || y<0 || y>8) {
			throw new MovimentoInvalidoException();
		} 
		else if (valor>9 || valor<1) {
			throw new MovimentoInvalidoException();
		} 
		else if (grid[x][y] != 0) {
			throw new MovimentoInvalidoException();
		}
		else if (valor != gabarito[x][y]){
			throw new MovimentoIncorretoException();
		}
		else {
			grid[x][y]=valor; 
			return true;
		}
	}

	/**
	 * Metodo para conferir se o tabuleiro está preenchido, considerando que as lacunas não preenchidas
	 * estão 'preenchidas' com valor 0.
	 * @return
	 */

	public boolean isTabuleiroPreenchido()
	{

		for(int linha=0; linha<grid.length; linha++)
		{
			for(int coluna=0; coluna<grid.length; coluna++)
			{
				if(grid[linha][coluna]==0)
				{
					return false;
				}
				break;
			}
		}
		return true;
	}

	/**
	 * Metodo para resolver o tabuleiro quando o jogador desistir do jogo ou alcançar o numero
	 * maximo de erros.
	 * @return
	 */

	public boolean resolveTabuleiro(int[][] tabuleiro) {

		int x=0;
		int y=0;
		boolean teste = false;

		for(x = 0;x < 9; x ++)
		{
			for(y = 0;y < 9; y++)
			{
				if(tabuleiro[x][y] == 0)
				{
					teste = true;
					break;
				}
			}
			if( teste ) break;
		}
		if(!teste) return true;

		boolean num[] = new boolean[11];
		for(int i = 0; i < 9; i++)
		{
			num[tabuleiro[x][i]] = true;
			num[tabuleiro[i][y]] = true;
		}
		int bx = 3 * (x/3), by = 3 * (y/3);
		for(int i =0;i<3;i++)
			for(int j = 0; j < 3; j++)
				num[tabuleiro[bx+i][by+j]] = true;

		for(int i = 1 ; i <= 9; i++)
		{
			if(!num[i] )
			{
				tabuleiro[x][y] = i;
				if(resolveTabuleiro(tabuleiro))
					return true;
				tabuleiro[x][y] = 0;
			}
		}
		return false; 
	}

	/**
	 * Metodo auxiliar do geraTabuleiro
	 * Metodo recebendo dificuldade e removendo algumas posições aleatorias da grid
	 * substituindo a posição preenchida por 0
	 * @param dificuldade
	 */
	private void gridRemover (DificuldadePartida dificuldade)
	{
		Random random = new Random();

		for(int num=1; num< dificuldade.getGridRemover(); num++)
		{
			int i= random.nextInt(9); 
			int j= random.nextInt(9);

			if(grid[i][j]==0){
				num--;
			} else {
				grid[i][j]=0; 
			}
		}	
	}
 
	/**
	 *  cria um tabuleiro de 81 posição sem repitição
	 */
	private void numerosSemRepetição(){
		
		int k=1,n=1;
		for(int i=0;i<9;i++)
		{
			k=n;
			for(int j=0;j<9;j++)
			{
				if(k<=9){
					gabarito[i][j]=k;
					k++;
				}else{
					k=1;
					gabarito[i][j]=k;
					k++;
				}
			}
			n=k+3;
			if(k==10)
				n=4;
			if(n>9)
				n=(n%9)+1;
		}
	}
	
	/**
	 * embaralha o tabuleiro criando sub grids 3x3 sem repitição(gabarito)
	 */
	private void embaralha3x3(){
		
		int k1,k2,max=2,min=0;
		Random r= new Random();
		for(int i=0;i<3;i++)
		{
			k1=r.nextInt(max-min+1)+min;
			do{
				k2=r.nextInt(max-min+1)+min;
			}
			while(k1==k2);
			max+=3;min+=3;
			int check=1;
			if(check==1)
			{
				int temp;
				for(int j=0;j<9;j++)
				{
					temp=gabarito[k1][j];
					gabarito[k1][j]=gabarito[k2][j];
					gabarito[k2][j]=temp;
				}
			}
			check--;
			if(check==0)
			{
				int temp2;
				for(int j=0;j<9;j++)
				{
					temp2=gabarito[j][k1];
					gabarito[j][k1]=gabarito[j][k2];
					gabarito[j][k2]=temp2;
				}
			}
		}
	}

	public void geraTabuleiro(DificuldadePartida dificuldade)
	{
		numerosSemRepetição(); 

		embaralha3x3(); 

		// Atribuindo valor a grid pelo gabarito
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				grid[i][j] = gabarito[i][j];
			}		
		}

		gridRemover(dificuldade); // transforma x numeros do grid em 0
	}

	/**
	 * Conferindo se a grid está totalmente preenchida, sem nenhum espaço igual 0
	 * @return
	 */
	public boolean fimDeJogo()
	{
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					if (grid[i][j]==0)
						return false;
				}
				System.out.println("");
			}
			return true;
	}

	// Getters
	public int getGabarito(int x, int y) {
		return gabarito[x][y];
	}
	public int getGrid(int x, int y) {
		return grid[x][y];
	}
	public int[][] getGridResolve() {
		return grid;
	}
}
