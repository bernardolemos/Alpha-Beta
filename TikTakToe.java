import java.util.Date;
import java.util.Scanner;
import java.util.LinkedList;;

public class TikTakToe extends Node{
	
	static int totalPlays=0,seedPlayer=2,searchedNodes=0,totalSearchedNodes=0;
	static int line=0,column=0,diagonal1=0,diagonal2=0,linePosition=0,columnPosition=0;
		
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		int[][] currentTable=new int[3][3];
		int winner=0,player=0;
		System.out.println("Tik Tak Toe\n....................");
		System.out.println("Choose an algorithm\nMode MiniMax: 1\nMode Alpha-Beta: 2");
		System.out.print("Algorithm: ");
		int play=in.nextInt();
		System.out.print("\n....................\nSelect a mode: ");
		if(play==1)
			System.out.println("Mode MiniMax\n....................");
		else
			System.out.println("Alpha-Beta\n....................");
		System.out.print("Want to start?\nYes: 1\nNo: 2\n....................\nComeçar: ");
		int start=in.nextInt();
		if(start==1)
			player=1;
		else
			player=2;
		System.out.println("For every play type a postion 'i', followed by a postion 'j'\n....................");
		System.out.println("User: X\nComputer: O\n");
		Play.printTable(currentTable);
		winner=Play.getWinner(currentTable);
		if(play==1)
			while(winner==0){
				searchedNodes=0;
				double s=new Date().getTime();
				player=Play.playMiniMax(in,currentTable,player);
				double end=new Date().getTime();
				winner=Play.getWinner(currentTable);
				if(player==1){
					totalSearchedNodes+=searchedNodes;
					System.out.println("....................\nNumber of expanded nodes: "+searchedNodes);
				}
				System.out.println("Time of play: "+(end-s)/1000+"s\n....................");
			}
		else
			while(winner==0){
				searchedNodes=0;
				double s=new Date().getTime();
				player=Play.playAlphaBeta(in,currentTable,player);
				double end=new Date().getTime();
				winner=Play.getWinner(currentTable);
				if(player==1){
					totalSearchedNodes+=searchedNodes;
					System.out.println("....................\nNumber of expanded nodes: "+searchedNodes);
				}
				System.out.println("Time of play: "+(end-s)/1000+"s\n....................");
			}
		Play.printResult(currentTable,winner,play);
		System.out.println("Total number of expanded nodes: "+totalSearchedNodes);
	}

	static class Play{
		
			/**
			* play against computer using MinMax
			 * @param in
			 * @param currentTable
			 */
		public static int playMiniMax(Scanner in,int[][] currentTable,int player){
				if(player!=2){
					System.out.print("User: ");
					int i=in.nextInt();
					int j=in.nextInt();
					System.out.println();
					//play is valid?
					if(i>2 || j>2){
						System.out.println("Invalid play! Please, choose another position");
						return -1;
					}
					else if(currentTable[i][j]!=0){
						System.out.println("Invalid play! Please, choose another position");
						return -1;
					}
					else{
						currentTable[i][j]=1;
						printTable(currentTable);
						totalPlays++;
					}
					return 2;
				}
				else{
					System.out.println("Computer: ");
					minimaxSearch(player,currentTable);
					printTable(currentTable);
					totalPlays++;
					return 1;
				}
			}
		
		/**
		 * play against computer using Alpha-Beta
		 * @param in
		 * @param currentTable
		 * @param player
		 * @return
		 */
		public static int playAlphaBeta(Scanner in,int[][] currentTable,int player){
			if(player!=2){
				System.out.print("User: ");
				int i=in.nextInt();
				int j=in.nextInt();
				System.out.println();
				//play is valid?
				if(i>2 || j>2){
					System.out.println("Invalid play! Please, choose another position");
					return -1;
				}
				else if(currentTable[i][j]!=0){
					System.out.println("Invalid play! Please, choose another position");
					return -1;
				}
				else{
					currentTable[i][j]=1;
					printTable(currentTable);
					totalPlays++;
				}
				return 2;
			}
			else{
				System.out.println("Computer: ");
				alphaBetaSearch(player,currentTable);
				printTable(currentTable);
				totalPlays++;
				return 1;
			}
		}
		
		/**
		 * print table
		 * @param currentTable		 
		 */
		public static void printTable(int[][] currentTable){
			System.out.println("  i/j   0   1   2\n     _  _  _  _  _ \n ");
			for (int i = 0; i<3; i++) {
				for (int j = 0; j<3; j++) {
					if(j==0)
						System.out.print(" "+i+" |   ");
					if(currentTable[i][j]==1)
						System.out.print(" X");
					else if(currentTable[i][j]==2)
						System.out.print(" O");
					else
						System.out.print("  ");
					if(j<2)
						System.out.print(" |");
				}
				System.out.println();
				if(i<2)
					System.out.println("       -----------");
			}
			System.out.println();
		}
		
		/**
		 * check if game is over
		 * @param currentTable 	
		 * @return 	winner (0 if game hasn't ended; -1 draw)
		 */
		public static int getWinner(int[][] currentTable){
			column=checkColumn(currentTable);
			if(column!=0)
				return column;
			line=checkLine(currentTable);
			if(line!=0)
				return line;
			diagonal1=checkDiagonal1(currentTable);
			if(diagonal1!=0)
				return diagonal1;
			diagonal2=checkDiagonal2(currentTable);
			if(diagonal2!=0)
				return diagonal2;
			if(isFull(currentTable))
				return -1;
			return 0;
		}

		/**
		 * winning row?
		 * @param currentTable
		 * @return
		 */
		public static int checkLine(int[][] currentTable){
			for(int i=0;i<3;i++)
				if(currentTable[i][0]!=0 && 
						currentTable[i][0]==currentTable[i][1] && currentTable[i][1]==currentTable[i][2]){
					linePosition=i;
					return currentTable[i][0];
				}
			return 0;
		}
				
		/**
		 * winning column?
		 * @param currentTable
		 * @return
		 */
		public static int checkColumn(int[][] currentTable){
			for(int j=0;j<3;j++)
				if(currentTable[0][j]!=0 && 
						currentTable[0][j]==currentTable[1][j] && currentTable[1][j]==currentTable[2][j]){
					columnPosition=j;
					return currentTable[0][j];
				}
				return 0;
		}
		
		/**
		 * main diagonal?	
		 * @param currentTable
		 * @return
		 */
		public static int checkDiagonal1(int[][] currentTable){
			if(currentTable[0][0]==currentTable[1][1] && currentTable[1][1]==currentTable[2][2])
				return currentTable[0][0];
			return 0;
		}
		
		/**
		 * secondary diagonal?
		 * @param currentTable
		 * @return
		 */
		public static int checkDiagonal2(int[][] currentTable){
			if(currentTable[2][0]==currentTable[1][1] && currentTable[1][1]==currentTable[0][2]){
				return currentTable[2][0];
			}
			return 0;
		}
		
		/**
		 * are there any more plays?
		 * @param currentTable
		 * @return
		 */
		public static boolean isFull(int[][] currentTable){
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(currentTable[i][j]==0)
						return false;
			return true;
		}
		
		/*
		 * print winner and winning play (final result)
		 */
		public static void printResult(int[][] currentTable,int winner,int play){
			if(winner==-1)
				System.out.println("Draw!");
			else{
				if(winner==1)
				System.out.print("You win!");
				else
					System.out.print("You loose!");
				if(line!=0)
					System.out.println(" (row "+linePosition+")");
				else if(column!=0)
					System.out.println(" (column "+columnPosition+")");
				else if(diagonal1!=0)
					System.out.println(" (main diagonal)");
				else
					System.out.println(" (secondary diagonal)");
			}
			System.out.println("Total number of plays: "+totalPlays);
		}
		
		/**
		 * pesquisa minimax
		 */
		public static void minimaxSearch(int player,int[][] currentTable){
			Node currentNode=new Node(currentTable,0,getTableUtilily(currentTable, player));
			Node bestNode=new Node(currentTable,0,Integer.MIN_VALUE);
			LinkedList<Node> childNodes=new LinkedList<Node>();
			childNodes=generateChildNodes(currentNode, player);
			//permite guardar a próxima jogada a executar
			while(!childNodes.isEmpty()){
				Node n=new Node(childNodes.removeFirst());
				n.utility=miniSearch(n,1);
				bestNode=getMax(bestNode,n);
			}
			currentTable=setTable(currentTable, bestNode.getTable());
		}
		
		/**
		 * define uma tabela igual a outra
		 * @param table1	tabela a definir
		 * @param table2	table de base
		 * @return
		 */
		public static int[][] setTable(int[][] table1, int[][] table2){
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					table1[i][j]=table2[i][j];
			return table1;
		}
			
		/**
		 * função utilidade
		 * @param table		
		 * @return
		 */
		public static int getTableUtilily(int[][] table, int player){
			return getLinesUtility(table,player)+getColumnsUtility(table,player)+getDiagonalsUtility(table,player);
		}
		
		/**
		 * colunas disponiveis
		 * @param table
		 * @param player
		 * @return
		 */
		public static int getColumnsUtility(int[][] table,int player){
			int utility1=0,utility2=0;
			boolean dirtyBit=false;
			//player 1
			for(int j=0;j<3;j++){
				dirtyBit=false;
				for(int i=0;i<3;i++)
					if(table[i][j]==2)//posição ocupada pelo adversario
						dirtyBit=true;
				if(!dirtyBit)
					utility1++;
			}
			//player 2
			for(int j=0;j<3;j++){
				dirtyBit=false;
				for(int i=0;i<3;i++)
					if(table[i][j]==1)//posição ocupada pelo adversario
						dirtyBit=true;
				if(!dirtyBit)
					utility2++;
			}
			if(seedPlayer==2)
				return utility2-utility1;
			return utility1-utility2;
		}
		
		/**
		 * linhas disponiveis
		 * @param table
		 * @param player
		 * @return
		 */
		public static int getLinesUtility(int[][] table,int player){
			int utility1=0,utility2=0;
			boolean dirtyBit=false;
			//player 1
			for(int i=0;i<3;i++){
				dirtyBit=false;
				for(int j=0;j<3;j++)
					if(table[i][j]==2)//posição ocupada pelo adversario
						dirtyBit=true;
				if(!dirtyBit)
					utility1++;
			}
			//player 2
			for(int i=0;i<3;i++){
				dirtyBit=false;
				for(int j=0;j<3;j++)
					if(table[i][j]==1)//posição ocupada pelo adversario
						dirtyBit=true;
				if(!dirtyBit)
					utility2++;
			}
			if(seedPlayer==2)
				return utility2-utility1;
			return utility1-utility2;
		}
 		
		/**
		 * diagonais disponiveis
		 * @param table
		 * @param player
		 * @return
		 */
		public static int getDiagonalsUtility(int[][] table,int player){
			int p1=0,p2=0;
			//diagonal principal
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(i==j){
						if(table[i][j]==2)
							p2=1;
						else if(table[i][j]==1)
							p1=1;
					}
			if(table[0][2]==1){
				if(p1>0)		
					p1=2;
				else
					p1=1;
			}
			if(table[0][2]==2){
				if(p2>0)
					p2=2;
				else
					p2=1;
			}
			if(table[2][0]==1){
				if(p1>0)
					p1=2;
				else
					p1=1;
			}
			if(table[2][0]==2){
				if(p2>0)
					p2=2;
				else
					p2=1;
			}
			if(seedPlayer==1)
				return p1-p2;
			return p2-p1;
				
		}

		/**
		 * gera nós filhos
		 * @param currentNode
		 * @param player
		 * @return
		 */
		public static LinkedList<Node> generateChildNodes(Node currentNode,int player){
			LinkedList<Node> childList=new LinkedList<Node>();
			int[][] currentTable=new int[3][3];
			currentTable=setTable(currentTable,currentNode.getTable());
			int[][] auxTable=new int[3][3];
			int depth=currentNode.getDepth();
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(currentTable[i][j]==0){
						auxTable=setTable(auxTable,currentTable);
						auxTable[i][j]=player;
						Node childNode=new Node(auxTable,depth+1,getTableUtilily(auxTable, player));
						childList.addLast(childNode);
						searchedNodes++;
					}
			return	childList;
		}
	
		/**
		 * máximo entre dois nos comparando a utilidade
		 * @param n1
		 * @param n2
		 * @return
		 */
		public static Node getMax(Node n1,Node n2){
			if(n1.getUtility()>=n2.getUtility())
				return n1;
			return n2;
		}
		
		/**
		 * mínimo entre dois nos comparando a utilidade
		 * @param n1
		 * @param n2
		 * @return
		 */
		public static Node getMin(Node n1,Node n2){
			if(n1.getUtility()<=n2.getUtility())
				return n1;
			return n2;
		}
		
		/**
		 * pesquisa valor maximo
		 * @param currentNode
		 * @param player
		 * @return
		 */
		public static int maxSearch(Node currentNode,int player){
			int[][] currentTable=new int[3][3];
			int max=Integer.MIN_VALUE;
			currentTable=setTable(currentTable,currentNode.getTable());
			if(getWinner(currentTable)==0){		
				LinkedList<Node> childNodes=new LinkedList<Node>();
				childNodes=generateChildNodes(currentNode,player);
				if(player==1)
					player=2;
				else
					player=1;
				while(!childNodes.isEmpty()){
					Node n=new Node(childNodes.removeFirst());
					max=Math.max(max,miniSearch(n,player));
				}
			}
			else{
				//verifica se é jogada perdedora
				if(getWinner(currentTable)!=seedPlayer && getWinner(currentTable)>-1)
					return Integer.MIN_VALUE;
				//verifica se é jogada vencedora	
				else if(getWinner(currentTable)==seedPlayer)
					return Integer.MAX_VALUE;
				return currentNode.getUtility();
			}
			return max;
		}
	
		/**
		 * pesquisa valor minimo 
		 * @param currentNode
		 * @param player
		 * @return
		 */
		public static int miniSearch(Node currentNode,int player){
			int[][] currentTable=new int[3][3];
			int min=Integer.MAX_VALUE;
			currentTable=setTable(currentTable,currentNode.getTable());
			if(getWinner(currentTable)==0){
				LinkedList<Node> childNodes=new LinkedList<Node>();
				childNodes=generateChildNodes(currentNode,player);
				if(player==1)
					player=2;
				else
					player=1;
				while(!childNodes.isEmpty()){
					Node n=new Node(childNodes.removeFirst());
					min=Math.min(min,maxSearch(n,player));
				}
			}
			else{
				//verifica se é jogada perdedora
				if(getWinner(currentTable)!=seedPlayer && getWinner(currentTable)>-1)
					return Integer.MIN_VALUE;
				//verifica se é jogada vencedora
				else if(getWinner(currentTable)==seedPlayer)
					return Integer.MAX_VALUE;
				return currentNode.getUtility();
			}
			return min;
		}
			
		/**
		 * pesquisa usando o alpha-beta
		 * @param player
		 * @param currentTable
		 */
		public static void alphaBetaSearch(int player,int[][] currentTable){
			int alpha=Integer.MIN_VALUE,beta=Integer.MAX_VALUE;
			Node currentNode=new Node(currentTable,0,getTableUtilily(currentTable, player));
			Node bestNode=new Node(currentTable,0,Integer.MIN_VALUE);
			LinkedList<Node> childNodes=new LinkedList<Node>();
			childNodes=generateChildNodes(currentNode, player);
			//permite guardar a próxima jogada a executar
			while(!childNodes.isEmpty() ){
				Node n=new Node(childNodes.removeFirst());
				n.utility=minimizingSearch(n,alpha,beta,1);
				bestNode=getMax(bestNode,n);
			}
			currentTable=setTable(currentTable, bestNode.getTable());
		}
		
		/**
		 * jogada maximizante (alpha)
		 * @param currentNode
		 * @param alpha
		 * @param beta
		 * @param player
		 * @return
		 */
 		public static int maximizingSearch(Node currentNode,int alpha,int beta,int player){
			int[][] currentTable=new int[3][3];
			int max=Integer.MIN_VALUE;
			currentTable=setTable(currentTable,currentNode.getTable());
			if(getWinner(currentTable)==0 || beta<=alpha){		
				LinkedList<Node> childNodes=new LinkedList<Node>();
				childNodes=generateChildNodes(currentNode,player);
				if(player==1)
					player=2;
				else
					player=1;
				while(!childNodes.isEmpty()){
					Node n=new Node(childNodes.removeFirst());
					max=Math.max(max,minimizingSearch(n,alpha,beta,player));
					alpha=Math.max(max,alpha);
					if(beta<=alpha)
						break;
				}
			}
			else{
				//verifica se é jogada perdedora
				if(getWinner(currentTable)!=seedPlayer && getWinner(currentTable)>-1)
					return Integer.MIN_VALUE;
				//verifica se é jogada vencedora	
				else if(getWinner(currentTable)==seedPlayer)
					return Integer.MAX_VALUE;
				return currentNode.getUtility();
			}
			return max;
		}
	
 		/**
 		 * jogada minimizante (beta)
 		 * @param currentNode
 		 * @param alpha
 		 * @param beta
 		 * @param player
 		 * @return
 		 */
		public static int minimizingSearch(Node currentNode,int alpha,int beta,int player){
			int[][] currentTable=new int[3][3];
			int min=Integer.MAX_VALUE;
			currentTable=setTable(currentTable,currentNode.getTable());
			if(getWinner(currentTable)==0 || beta<=alpha){
				LinkedList<Node> childNodes=new LinkedList<Node>();
				childNodes=generateChildNodes(currentNode,player);
				if(player==1)
					player=2;
				else
					player=1;
				while(!childNodes.isEmpty()){
					Node n=new Node(childNodes.removeFirst());
					min=Math.min(min,maximizingSearch(n,alpha,beta,player));
					beta=Math.min(min,beta);
					if(beta<=alpha)
						break;
				}
			}
			else{
				//verifica se é jogada perdedora
				if(getWinner(currentTable)!=seedPlayer && getWinner(currentTable)>-1)
					return Integer.MIN_VALUE;
				//verifica se é jogada vencedora
				else if(getWinner(currentTable)==seedPlayer)
					return Integer.MAX_VALUE;
				return currentNode.getUtility();
			}
			return min;
		}
	}
}

	
	

