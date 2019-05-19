

public class Node {
	
	int table[][],depth,utility;
	
	Node(){	
		table=new int[3][3];
		utility=depth=0;
	}
	
	Node(Node n){
		table=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				table[i][j]=n.table[i][j];	
		depth=n.depth;
		utility=n.utility;
		}
	
	Node(int t[][],int depth,int c){
		table=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				table[i][j]=t[i][j];	
		this.depth=depth;
		utility=c;
	}
	
	/**
	 * depth of element
	 * @return depth
	 */
		public int getDepth(){
			return depth;
		}

	/**
	 * get table
	 * @return table
	 */
		public int[][] getTable(){
			return table;
		}
		
	/**
	 * get cost	
	 * @return cost
	 */
		public int getUtility(){
			return utility;
		}
}