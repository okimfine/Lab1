package lab1;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class labone {
	public static String[][] Bridges = new String[200][3];//�ŽӴʾ���
	public static String[] v = new String[200];//���㼯��
	public static String[][] edges = new String[200][2];//����߼�
	public static int[][] matrix = new int[200][200]; //����ͼ���ڽӾ���
	public static String test; //����Ĳ����ַ���
	public static int[] visited = new int[200];//�����·��������飨�������ã�
	public static int[] path = new int[200];//�����·����¼���飨�������ã� test

/*
 * ���ܣ����ļ�����ת��Ϊ�������ַ���
 * ����loc���ļ�λ��
 * ����name���ļ�����
* */	
  public static String InputFile(String loc,String name){
	  String str =new String();
	  try{
		  File f = new File(loc,name);
		  InputStream is = new FileInputStream(f);
		  Scanner txt = new Scanner(is,"utf-8");
          while(txt.hasNextLine()){
			  String tmp = txt.nextLine();
		      tmp = tmp.replaceAll("[^a-zA-Z]", " ");
		      tmp = tmp.replaceAll("[\\t\\n\\r]", " ");
		      tmp = tmp.toLowerCase();
		      str = str + tmp + " ";//�������з�
		  }
		  str = str.replaceAll(" +", " ");//ɾ������Ŀո�
		  txt.close();
		  is.close();
		  return str;
	  }catch(IOException e){
	      System.out.println("������Ϸ�·����");
	      return str;
	  }
} 

  /*
   * ���ܣ���������
   * ����txt������ú��ı�
   * */  
  public static String[][] EdgesCreate(String txt){
	  String[] ntxt = txt.split(" "); //�ַ���ת��Ϊ�ַ�����
	  int len = ntxt.length;
	  
	  String[][] Edges = new String[len-1][2]; 
	  for(int i= 0; i < len-1 ; i++ ){
		  Edges[i][0] = ntxt[i];
		  Edges[i][1] = ntxt[i+1];
	  }
	  return Edges;
  }
  
  /*
   * ���ܣ���������
   * ����txt������ú���ı�
   * */
   public static String[] VerCreate(String txt){
	   String[] ntxt = txt.split(" "); //�ַ���ת��Ϊ�ַ�����
	   String[] tmpver = new String[ntxt.length];
	   int con = 0;
	   for(int i = 0; i < ntxt.length; i++){
		   String tmp = ntxt[i];
		   if(Arrays.asList(tmpver).contains(tmp) == false){    //ɾ���ظ���
			   tmpver[con] = tmp;
			   con++;
		   }
	   }
	   String[] Vertexs = new String[con];
	   for (int i = 0; i < con; i++)
		   Vertexs[i] = tmpver[i];
	   return Vertexs;
   }
 
   /*
    * ���ܣ�����ߵ�Ȩֵ����ɾ���ظ���
    * ����Edges������õı���
    * */
   public static String[][] EdgeWeight(String[][] Edges){
	   String[] tmpedge = new String[Edges.length];         //���ߵ����������Ϊһ���ַ���
	   for(int i = 0; i < Edges.length; i++){
		   tmpedge[i] = Edges[i][0] + " " + Edges[i][1];
	   }
	   
	   String[] tmpedge1 = new String[tmpedge.length];     //ɾ���ظ����ַ�������ɾ���ظ��ߣ�
	   int count = 0;
	   for(int i = 0; i < tmpedge.length; i++){
		   String tmpedge2 = tmpedge[i];
		   if(Arrays.asList(tmpedge1).contains(tmpedge2) == false){
			   tmpedge1[count] = tmpedge2;
			   count++;
		   }
	   }
	   String[] tmp = new String[count];
	   for (int i = 0; i < count; i++)
		   tmp[i] = tmpedge1[i];
	   
	   int weight[] = CountWeight(tmpedge,tmp);        //����ߵ�Ȩֵ
	   
	   String[][] EdgesWeight = new String[tmp.length][3];  
	   for(int i = 0; i < tmp.length; i++){
		   String[] tran = tmp[i].split(" ");
		   EdgesWeight[i][0] = tran[0];
		   EdgesWeight[i][1] = tran[1];
		   EdgesWeight[i][2] = Integer.toString(weight[i]);
	   }
	   return EdgesWeight;
   }
 
   /*
    * ���ܣ�����ߵ�Ȩֵ
    * ����Edges����һ���ߵ������ڵ��Ϊһ���ַ������ַ������飨�����ظ��ߣ�
    * ����word����һ���ߵ������ڵ��Ϊһ���ַ������ַ������飨�������ظ��ߣ�
    * */
   public static int[] CountWeight(String[] Edges, String[] word){
	   Map<String, Integer> link = new HashMap<String, Integer>();
	   int[] weight = new int[word.length];
	   for(String i : Edges){
		   if(link.containsKey(i)){
			   int count = link.get(i);
			   link.put(i,++count);
		   }
		   else
			   link.put(i, 1);
	   }
	   for(int i = 0; i < word.length; i++){
		   weight[i] = link.get(word[i]);
	   }
	   return weight;
   }

   /*
    * ���ܣ���ͼ���ڽӾ���
    * ����edges������
    * ����vertexs:������
    * */
   public static int[][] Matrix(String[][] edges, String[] vertexs){
	   int[][] matrix = new int[vertexs.length][vertexs.length];
	   int x, y;
	   for(int i = 0; i < vertexs.length; i++){         //��ʼ���ڽӾ���
		   for(int j = 0; j < vertexs.length; j++){
			   matrix[i][j] = Integer.MAX_VALUE;
		   }
	   }
	   
	   for(int i = 0; i < edges.length; i++){       
		   x = Arrays.binarySearch(vertexs, edges[i][0]);      //���ö��ֲ��Ҳ��ҽڵ��Ӧ���
		   y = Arrays.binarySearch(vertexs, edges[i][1]);
		   matrix[x][y] = Integer.parseInt(edges[i][2]);
	   }
	   return matrix;
   }

   /*
    * ���ܣ����ò��Graphvizչʾ����ͼ
    * ���� Edgesweight������Ȩֵ������߼�
    * */
	public static void showDirectedGraph(String[][] Edgesweight){
	      GraphViz gv = new GraphViz();
	      int n = Edgesweight.length;
	      gv.addln(gv.start_graph());
	      for(int i = 0;i < n;i++){
	    	  gv.addln(Edgesweight[i][0]+"->"+Edgesweight[i][1]+" [ label = \" " + Edgesweight[i][2]+"\" ] ;");
	      }
	      gv.addln(gv.end_graph());
	      
	      String type = "png";
	      File out = new File("C:\\test\\out." + type);    // Windows
	      gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	      return;
	}

	   /*
	    * ���ܣ������ŽӴʾ���
	    * ����vertexs��δɾ���ظ��ʵİ��ı�����˳��洢���ʵ��ַ�������
	    * */
   public static void BridgeCreate(String[] vertexs){
	   String[][] temp = new String[vertexs.length-2][3];     //���ɰ����ظ��ŵ��ŽӴʾ���
	   for(int i = 0; i < vertexs.length-2; i++){
		   temp[i][0] = vertexs[i];
		   temp[i][1] = vertexs[i+1];
		   temp[i][2] = vertexs[i+2];
	   }
	   
	   String[] temp1 = new String[temp.length];       //ɾ���ظ���
	   for(int i = 0; i < temp.length; i++){
		   temp1[i] = temp[i][0] + " " + temp[i][1] + " " + temp[i][2];
	   }
	   String[] temp2 = new String[temp.length];    
	   int count = 0;
	   for(int i = 0; i < temp1.length; i++){
		   String temp3 = temp1[i];
		   if(Arrays.asList(temp2).contains(temp3) == false){
			   temp2[count] = temp3;
			   count++;
		   }
	   }
	   String[] temp4 = new String[count];
	   for (int i = 0; i < count; i++)
		   temp4[i] = temp2[i];
	   
	   for(int i = 0; i < temp4.length; i++){          //���ɲ������ظ��ŵ��ŽӴʾ���
		   String[] temp5 = temp4[i].split(" ");
		   Bridges[i][0] = temp5[0];
		   Bridges[i][1] = temp5[1];
		   Bridges[i][2] = temp5[2];
	   }
   }

   /*
    * ���ܣ���ѯ�ŽӴ�
    * ����word1��word2���û��������������ѯ�ĵ���
    * */
   public static String queryBridgeWords(String word1, String word2){
	   String BridgeWords = new String();
	   if(Arrays.asList(v).contains(word1) == false && Arrays.asList(v).contains(word2) == false){         //����ѯ���ʲ���ͼ��
		   System.out.println("No " + "\"" + word1 + "\"" + " and " + "\"" + word2 + "\"" + " in the graph!");
	   }
	   else if(Arrays.asList(v).contains(word1) == false){
		   System.out.println("No " + "\"" + word1 + "\"" + " in the graph!");
	   }
	   else if(Arrays.asList(v).contains(word2) == false){
		   System.out.println("No " + "\"" + word2 + "\"" + " in the graph!");
	   }
	   else{
		   String[] Bridge = BridgeList(word1,word2);
		   if(Bridge.length == 0){        //�������ŽӴ�
			   return BridgeWords;
		   }
		   else{ 
			   for(int i = 0; i < Bridge.length; i++){               //�����ŽӴ�
				   BridgeWords = BridgeWords + Bridge[i] + " ";
			   }
			   return BridgeWords;
		   }
	   }
	   BridgeWords = " ";
	   return BridgeWords;
   }

   /*
    * ���ܣ�������ʾ��Ĳ�ѯ�ŽӴ�
    * ����word1��word2������ѯ����������
    * */
   public static String[] QueryBridge(String word1, String word2){
	   if(Arrays.asList(v).contains(word1) == true && Arrays.asList(v).contains(word2) == true){      //����ѯ�Ĵ���ͼ��
		   String[] Bridge = BridgeList(word1,word2);
		   return Bridge;
	   }
	   String[] Bridge = new String[0];
	   return Bridge;
   }
   
   /*
    * ���ܣ��������м���ŽӴʾ���
    * ����begin��end������ѯ����������
    * */
   public static String[] BridgeList(String begin, String end){
	   int count = 0;
	   String[] words = new String[Bridges.length];
	   for(int i = 0; i < Bridges.length; i++){
		   String before = Bridges[i][0];
		   String after = Bridges[i][2];
		   if(before != null){
			   if(before.equals(begin) && after.equals(end) == true){       //�ŽӴʾ����еĿ�ʼ�ʺͽ����������ѯ�ĵ��ʶ�Ӧ��ͬ
				   words[count] = Bridges[i][1];
				   count++;
			   }
		   }
		   else
			   break;
	   }
	   String[] BridgeWord = new String[count];       //�������ҵ����ŽӴʴ���Ҫ���ص��ַ���������
	   for(int i = 0; i < count; i++){
		   BridgeWord[i] = words[i];
	   }
	   return BridgeWord;
   }
   
   /*
    * ���ܣ������ŽӴ��������ı�
    * ����inputText���û�������ı�
    * */
   public static String generateNewText(String inputText){
	   inputText = inputText.replaceAll("[^a-zA-Z]+", " ");
	   String[] Text = inputText.split("\\s+");
	   String[] temp = new String[100];
	   int count = 0;
	   for(int i = 0; i < Text.length-1; i++){
		   temp[count] = Text[i];
		   count++;
		   String begin = Text[i].toLowerCase();        //���ı��еĴ�����ĸȫ��תΪСд��ĸ�ٲ�ѯ�ŽӴ�
		   String end = Text[i+1].toLowerCase();
		   String[] bridge = QueryBridge(begin,end);
		   if(bridge.length != 0){
			   temp[count] = bridge[(int) (Math.random() * bridge.length)];         //���ѡ��һ���ŽӴʲ����ı���
			   count++;
		   }
	   }
	   temp[count] = Text[Text.length-1];
	   count++;
	   String NewText = new String();
	   for(int i = 0; i < count; i++){
		   NewText = NewText + temp[i] + " ";
	   }
	   return NewText;
   }
   
   /*
    * ���ܣ�Dijkstra�������·��
    * ����point�����·�������
    * */  
   public static void Dijkstra(int point){
	   int i,j,k;
	   int n = matrix.length;
	   path = new int[n];
	   visited = new int[n];
	   int[] weight = new int[n];
	   
	   for(i = 0;i < n; i++)
		   weight[i] = matrix[point][i];
	   
		for (i = 0; i < n; i++) {
			if (weight[i] < Integer.MAX_VALUE) {
				path[i] = point;
			}
		}
		
		for (i = 0; i < n; i++)
			visited[i] = 0;
		
		visited[point] = 1;		
		weight[point] = 0;
		
		for (i = 0; i < n; i++) {
			int min = Integer.MAX_VALUE;
			k = point;
			for (j = 0; j < n; j++) {
				if (visited[j] == 0 && weight[j] < min) {
					min = weight[j];
					k = j;
				}
			}
			visited[k] = 1; //������k����
			for (j = 0; j < n; j++) { //�Զ���kΪ�м�ֵ�����µ�Ȩֵ
				if (visited[j] == 0 && matrix[k][j] != Integer.MAX_VALUE && weight[k] + matrix[k][j] < weight[j]) {
					weight[j] = weight[k] + matrix[k][j];
					path[j] = k;
				}
			}
		}
   }
   
   /*
    * ���ܣ����������ʼ�����·��
    * ����word1&word2����Ҫ��ĵ���
    * */
   public static String calcShortestPath(String word1, String word2){

	   int p1 , p2 , i , j ,k;
	   p1 = 0;
	   p2 = 0;
	   v = VerCreate(test);
	   Arrays.sort(v);
	   p1 = Arrays.binarySearch(v, word1);
	   p2 = Arrays.binarySearch(v, word2);
	   if(p1 < 0 | p2 < 0){
		   System.out.println("��������ȷ�ĵ��ʣ�");
		   return "";
	   }
        Dijkstra(p1);
        String NewText = new String();
		i = p2;		
		String[] str = new String[matrix.length];
		j = 0;
		if (i == p1) {
			System.out.println("������������ͬ�ĵ��ʣ���л��");
		} else if (visited[i] == 0) {
			NewText = v[p1] + " �� " + v[i] + " ���ɴ";
			System.out.println(NewText);
		} else {
			k = i;
			while (k != p1) {
				str[j] = v[k];
				j++;
				k = path[k];
			}
			str[j] = v[k];
			System.out.print(v[p1] + "��" + v[i] + "���·��Ϊ:\n");
			while (j != 0) {
				NewText += str[j] + "->";
				j--;
			}
			NewText += str[0] + "!";
		}
		return NewText;
	   	   
   }

   /*
    * ���ܣ���һ�����ʵ��������ʵ����·��
    * ����word����Ҫ��ĵ���
    * */
	public static void calcShortestPathOne(String word) {
		   int p1, i , j ,k;
		   p1 = 0;
		   v = VerCreate(test);
		   Arrays.sort(v);
		   p1 = Arrays.binarySearch(v, word);
		   if(p1 < 0){
			   System.out.println("��������ȷ�ĵ��ʣ�");
			   return;
		   }
	        Dijkstra(p1);
	        
           int n = matrix.length;
		   for (i = 0; i < n; i++) {
			   String[] str = new String[n];
			   j = 0;
			   if (i == p1) {
				   continue;
			   } else if (visited[i] == 0) {
				   System.out.println(v[p1] + " �� " + v[i] + " ���ɴ");
			   } else {
				   k = i;
				   while (k !=p1) {
					   str[j] = v[k];
					   j++;
					   k = path[k];
				   }
				   str[j] = v[k];
				   System.out.print(v[p1] + "��" + v[i] + "�����·��Ϊ��\n");
				   while (j != 0) {
					   System.out.print(str[j] + "->");
					   j--;
				   }
				   System.out.println(str[0] + " !");
			   }
		}
		   return;
	}  

	// ������ߡ�
	
	/*
	 *���ܣ��������һ���ɴ��
	 *����x:��ʼ��
	 **/
	protected static int random(int x) {
		int len = v.length;
		int[] l = new int[len];
		int num = 0;//�ɴ����
		for (int i = 0; i < len; i++) {
			if (matrix[x][i] < Integer.MAX_VALUE) {
				l[num] = i;
				num++;
			}
		}
		//���������
		if (num > 1)
			return l[(int) (Math.random() * num)];
		else if (num == 1)
			return l[0];
		else
			return -1;
		
	}
	
	/*
	 * ���ܣ��ļ�д��
	 * ����name���ļ�����
	 * ����content��д������
	 * */
	public static void WriteIn(String name, String content) {
		try {
			File file = new File(name);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(content);// ���ļ���д���ַ���
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	/*
	 * ���ܣ���������Ĵ�ͼ��ѡ��һ���ڵ㣬�Դ�Ϊ����س��߽��������������¼�� �������нڵ�ͱߣ�
	 * ֱ�����ֵ�һ���ظ��ı�Ϊֹ�����߽����ĳ���ڵ㲻���ڳ���Ϊֹ
     * */
	public static String randomWalk() {
		int len = v.length;
		int ransp;
		String result = new String();
		ransp = (int)(Math.random()*len);//��������ڵ�
		
		int[][] used = new int[len][len];
		for (int i = 0; i < len; i++)
			for (int j = 0; j < len; j++)
			    used[i][j] = -1;
		
		int a =ransp;
		result += v[a];
        
		while (random(a) != -1) {
			int b = random(a);//���������
			if (used[a][b] == 1){
				result += (" " + v[b]);
				break;
			}				
			else {
				result += (" " + v[b]);
				used[a][b] = 1;
				a = b;
			}
		}
		System.out.println(result);
		return result;
	}	
	
   public static void main(String args[]){
	   
	   System.out.println("��ӭʹ�ã�");
	   Scanner tmp = new Scanner(System.in);
	   //�����ı�
	   while(true){
		   System.out.println("���ȵ����ı���");
		   String loc = new String();
		   String name = new String();
		   System.out.println("�������ļ���ַ��");
		   loc = tmp.nextLine();
		   System.out.println("�������ļ����ƣ�");
		   name = tmp.nextLine();
		   test = InputFile(loc,name);
		   if(test.equals("") != true){
			   System.out.println("�ļ�����ɹ���");	
			   break;
		   }
	   }
	   
	   v = VerCreate(test);//���㼯��	   
	   edges = EdgesCreate(test);//�߼�����	   
	   String[][] edgeweight = EdgeWeight(edges);//����ߵ�Ȩֵ	   
	   Arrays.sort(v);
	   matrix = Matrix(edgeweight,v);//�ڽӾ���
	   
	   while(true){
		   
		   System.out.println("�����ǹ����б�");
		   System.out.println("1.����ͼ��");
		   System.out.println("2.���ŽӴ�");
		   System.out.println("3.�����¾�");
		   System.out.println("4.���·��(��������)");
		   System.out.println("5.���·����һ�����ʣ�");
		   System.out.println("6.�������");
		   System.out.println("0.�˳�����");
		   System.out.println("====================");
		   System.out.println("�����빦����ţ�");
		   
		   int num = tmp.nextInt();	
		   switch(num){
		   case 1:
			   System.out.println("��ӭʹ������ͼ���ܣ�");
			   showDirectedGraph(edgeweight);
			   System.out.println("ͼ�����ɳɹ���");
			   break;
			   
		   case 2:
			   System.out.println("��ӭʹ�ò�ѯ�ŽӴʹ��ܣ�");
			   System.out.println("��������Ҫ��ѯ�ŽӴʵ���������(��������֮���û��з��ָ�����");
			   String begin = new String();
			   String end = new String();
			   tmp.nextLine();
			   begin = tmp.nextLine();
			   end = tmp.nextLine();
			   String[] words = test.split(" ");
			   BridgeCreate(words);
			   String bridge = queryBridgeWords(begin,end);
			   if(bridge.equals("") == true){
				   System.out.println("No bridge words from " + "\"" + begin + "\"" + " to " + "\"" + end + "\"" + "!");
			   }
			   else if(bridge.equals("") == false && bridge.equals(" ") == false){
				   System.out.print("The bridge words from " + "\"" + begin + "\"" + " to " + "\"" + end + "\"" + " are:");
				   String[] bridgeWords = bridge.split(" ");
				   if(bridgeWords.length == 1){
					   System.out.println(bridgeWords[0] + ".");
				   }
				   else if(bridgeWords.length == 2){
					   System.out.println(bridgeWords[0] + " and " + bridgeWords[1] + ".");
				   }
				   else{
					   for(int i = 0; i < bridgeWords.length-1; i++){
						   System.out.print(bridgeWords[i] + ",");
					   }
					   System.out.println("and " + bridgeWords[bridgeWords.length-1] + ".");
				   }
				   
			   } 
			   break;
			   
		   case 3:
			   System.out.println("��ӭʹ�������¾��ӹ��ܣ�");
			   System.out.println("�������ı���");
			   String sentence = new String();
			   tmp.nextLine();
			   sentence = tmp.nextLine();
			   String[] newword = test.split(" ");
			   BridgeCreate(newword);
			   String NewText = generateNewText(sentence);
			   System.out.println("���ɵ����ı�Ϊ��");
			   System.out.println(NewText);
			   break;
			   
		   case 4:
			   System.out.println("��ӭʹ�ò�ѯ���·�����������ʣ����ܣ�");
			   System.out.println("��������Ҫ��ѯ�ĵ��ʣ�");
			   String word1 = new String();
			   String word2 = new String();
			   tmp.nextLine();//�����س�����
			   word1 = tmp.nextLine();
			   word2 = tmp.nextLine();				   
			   String res = new String();
			   res = calcShortestPath(word1,word2);
			   System.out.println(res);
			   break;

		   case 5:
			   System.out.println("��ӭʹ�ò�ѯ���·����һ�����ʣ����ܣ�");
			   System.out.println("��������Ҫ��ѯ�ĵ��ʣ�");
			   tmp.nextLine(); //�����س�����
			   String word = new String();
			   word = tmp.nextLine();
			   calcShortestPathOne(word);
			   break;
			   
		   case 6:
			   System.out.println("��ӭʹ��������߹��ܣ�");
			   System.out.println("��1����������ߣ��������������������");
			   
			   String tp2 = new String();
			   tmp.nextLine();
			   while (tmp.nextInt() == 1) {
					tp2 += randomWalk();
					tp2 += "\t\n";
					WriteIn("random.txt", tp2);
			   }			   
			   break;
			   
		   case 0:
			   System.out.println("��лʹ�ã��ټ���");
			   System.exit(0);
			   break;
			   
		   default:
			   tmp.nextLine(); //�����س�����
			   System.out.println("��������ȷ��ţ�");
			   break;
		      
		   }
		   
	   } 
   //tmp.close();  
   }


}
