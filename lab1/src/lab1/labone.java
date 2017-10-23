package lab1;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class labone {
	public static String[][] Bridges = new String[200][3];//桥接词矩阵
	public static String[] v = new String[200];//顶点集合
	public static String[][] edges = new String[200][2];//有向边集
	public static int[][] matrix = new int[200][200]; //有向图的邻接矩阵
	public static String test; //输入的测试字符串
	public static int[] visited = new int[200];//求最短路径标记数组（函数复用）
	public static int[] path = new int[200];//求最短路径记录数组（函数复用） test

/*
 * 功能：将文件内容转化为待处理字符串
 * 参数loc：文件位置
 * 参数name：文件名称
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
		      str = str + tmp + " ";//消除换行符
		  }
		  str = str.replaceAll(" +", " ");//删除多余的空格
		  txt.close();
		  is.close();
		  return str;
	  }catch(IOException e){
	      System.out.println("请输入合法路径！");
	      return str;
	  }
} 

  /*
   * 功能：创建边组
   * 参数txt：处理好后文本
   * */  
  public static String[][] EdgesCreate(String txt){
	  String[] ntxt = txt.split(" "); //字符串转换为字符串组
	  int len = ntxt.length;
	  
	  String[][] Edges = new String[len-1][2]; 
	  for(int i= 0; i < len-1 ; i++ ){
		  Edges[i][0] = ntxt[i];
		  Edges[i][1] = ntxt[i+1];
	  }
	  return Edges;
  }
  
  /*
   * 功能：创建点组
   * 参数txt：处理好后的文本
   * */
   public static String[] VerCreate(String txt){
	   String[] ntxt = txt.split(" "); //字符串转换为字符串组
	   String[] tmpver = new String[ntxt.length];
	   int con = 0;
	   for(int i = 0; i < ntxt.length; i++){
		   String tmp = ntxt[i];
		   if(Arrays.asList(tmpver).contains(tmp) == false){    //删除重复点
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
    * 功能：计算边的权值，并删除重复边
    * 参数Edges：处理好的边组
    * */
   public static String[][] EdgeWeight(String[][] Edges){
	   String[] tmpedge = new String[Edges.length];         //将边的两个顶点和为一个字符串
	   for(int i = 0; i < Edges.length; i++){
		   tmpedge[i] = Edges[i][0] + " " + Edges[i][1];
	   }
	   
	   String[] tmpedge1 = new String[tmpedge.length];     //删除重复的字符串（即删除重复边）
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
	   
	   int weight[] = CountWeight(tmpedge,tmp);        //计算边的权值
	   
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
    * 功能：计算边的权值
    * 参数Edges：将一条边的两个节点合为一个字符串的字符串数组（包含重复边）
    * 参数word：将一条边的两个节点合为一个字符串的字符串数组（不包含重复边）
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
    * 功能：求图的邻接矩阵
    * 参数edges：边组
    * 参数vertexs:顶点组
    * */
   public static int[][] Matrix(String[][] edges, String[] vertexs){
	   int[][] matrix = new int[vertexs.length][vertexs.length];
	   int x, y;
	   for(int i = 0; i < vertexs.length; i++){         //初始化邻接矩阵
		   for(int j = 0; j < vertexs.length; j++){
			   matrix[i][j] = Integer.MAX_VALUE;
		   }
	   }
	   
	   for(int i = 0; i < edges.length; i++){       
		   x = Arrays.binarySearch(vertexs, edges[i][0]);      //利用二分查找查找节点对应序号
		   y = Arrays.binarySearch(vertexs, edges[i][1]);
		   matrix[x][y] = Integer.parseInt(edges[i][2]);
	   }
	   return matrix;
   }

   /*
    * 功能：利用插件Graphviz展示有向图
    * 参数 Edgesweight：含有权值的有向边集
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
	    * 功能：生成桥接词矩阵
	    * 参数vertexs：未删除重复词的按文本内容顺序存储单词的字符串数组
	    * */
   public static void BridgeCreate(String[] vertexs){
	   String[][] temp = new String[vertexs.length-2][3];     //生成包括重复桥的桥接词矩阵
	   for(int i = 0; i < vertexs.length-2; i++){
		   temp[i][0] = vertexs[i];
		   temp[i][1] = vertexs[i+1];
		   temp[i][2] = vertexs[i+2];
	   }
	   
	   String[] temp1 = new String[temp.length];       //删除重复桥
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
	   
	   for(int i = 0; i < temp4.length; i++){          //生成不包括重复桥的桥接词矩阵
		   String[] temp5 = temp4[i].split(" ");
		   Bridges[i][0] = temp5[0];
		   Bridges[i][1] = temp5[1];
		   Bridges[i][2] = temp5[2];
	   }
   }

   /*
    * 功能：查询桥接词
    * 参数word1、word2：用户输入的两个待查询的单词
    * */
   public static String queryBridgeWords(String word1, String word2){
	   String BridgeWords = new String();
	   if(Arrays.asList(v).contains(word1) == false && Arrays.asList(v).contains(word2) == false){         //待查询单词不在图中
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
		   if(Bridge.length == 0){        //不存在桥接词
			   return BridgeWords;
		   }
		   else{ 
			   for(int i = 0; i < Bridge.length; i++){               //存在桥接词
				   BridgeWords = BridgeWords + Bridge[i] + " ";
			   }
			   return BridgeWords;
		   }
	   }
	   BridgeWords = " ";
	   return BridgeWords;
   }

   /*
    * 功能：不带提示语的查询桥接词
    * 参数word1、word2：待查询的两个单词
    * */
   public static String[] QueryBridge(String word1, String word2){
	   if(Arrays.asList(v).contains(word1) == true && Arrays.asList(v).contains(word2) == true){      //待查询的词在图中
		   String[] Bridge = BridgeList(word1,word2);
		   return Bridge;
	   }
	   String[] Bridge = new String[0];
	   return Bridge;
   }
   
   /*
    * 功能：求两词中间的桥接词矩阵
    * 参数begin、end：待查询的两个单词
    * */
   public static String[] BridgeList(String begin, String end){
	   int count = 0;
	   String[] words = new String[Bridges.length];
	   for(int i = 0; i < Bridges.length; i++){
		   String before = Bridges[i][0];
		   String after = Bridges[i][2];
		   if(before != null){
			   if(before.equals(begin) && after.equals(end) == true){       //桥接词矩阵中的开始词和结束词与待查询的单词对应相同
				   words[count] = Bridges[i][1];
				   count++;
			   }
		   }
		   else
			   break;
	   }
	   String[] BridgeWord = new String[count];       //将所有找到的桥接词存入要返回的字符串数组中
	   for(int i = 0; i < count; i++){
		   BridgeWord[i] = words[i];
	   }
	   return BridgeWord;
   }
   
   /*
    * 功能：根据桥接词生成新文本
    * 参数inputText：用户输入的文本
    * */
   public static String generateNewText(String inputText){
	   inputText = inputText.replaceAll("[^a-zA-Z]+", " ");
	   String[] Text = inputText.split("\\s+");
	   String[] temp = new String[100];
	   int count = 0;
	   for(int i = 0; i < Text.length-1; i++){
		   temp[count] = Text[i];
		   count++;
		   String begin = Text[i].toLowerCase();        //将文本中的词中字母全部转为小写字母再查询桥接词
		   String end = Text[i+1].toLowerCase();
		   String[] bridge = QueryBridge(begin,end);
		   if(bridge.length != 0){
			   temp[count] = bridge[(int) (Math.random() * bridge.length)];         //随机选择一个桥接词插入文本中
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
    * 功能：Dijkstra法求最短路径
    * 参数point：最短路径的起点
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
			visited[k] = 1; //将顶点k加入
			for (j = 0; j < n; j++) { //以顶点k为中间值计算新的权值
				if (visited[j] == 0 && matrix[k][j] != Integer.MAX_VALUE && weight[k] + matrix[k][j] < weight[j]) {
					weight[j] = weight[k] + matrix[k][j];
					path[j] = k;
				}
			}
		}
   }
   
   /*
    * 功能：求两个单词间的最短路径
    * 参数word1&word2：所要求的单词
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
		   System.out.println("请输入正确的单词！");
		   return "";
	   }
        Dijkstra(p1);
        String NewText = new String();
		i = p2;		
		String[] str = new String[matrix.length];
		j = 0;
		if (i == p1) {
			System.out.println("请输入两个不同的单词，感谢！");
		} else if (visited[i] == 0) {
			NewText = v[p1] + " 和 " + v[i] + " 不可达！";
			System.out.println(NewText);
		} else {
			k = i;
			while (k != p1) {
				str[j] = v[k];
				j++;
				k = path[k];
			}
			str[j] = v[k];
			System.out.print(v[p1] + "和" + v[i] + "最短路径为:\n");
			while (j != 0) {
				NewText += str[j] + "->";
				j--;
			}
			NewText += str[0] + "!";
		}
		return NewText;
	   	   
   }

   /*
    * 功能：求一个单词到其他单词的最短路径
    * 参数word：所要求的单词
    * */
	public static void calcShortestPathOne(String word) {
		   int p1, i , j ,k;
		   p1 = 0;
		   v = VerCreate(test);
		   Arrays.sort(v);
		   p1 = Arrays.binarySearch(v, word);
		   if(p1 < 0){
			   System.out.println("请输入正确的单词！");
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
				   System.out.println(v[p1] + " 和 " + v[i] + " 不可达！");
			   } else {
				   k = i;
				   while (k !=p1) {
					   str[j] = v[k];
					   j++;
					   k = path[k];
				   }
				   str[j] = v[k];
				   System.out.print(v[p1] + "和" + v[i] + "的最短路径为：\n");
				   while (j != 0) {
					   System.out.print(str[j] + "->");
					   j--;
				   }
				   System.out.println(str[0] + " !");
			   }
		}
		   return;
	}  

	// 随机游走。
	
	/*
	 *功能：随机产生一条可达边
	 *参数x:起始点
	 **/
	protected static int random(int x) {
		int len = v.length;
		int[] l = new int[len];
		int num = 0;//可达边数
		for (int i = 0; i < len; i++) {
			if (matrix[x][i] < Integer.MAX_VALUE) {
				l[num] = i;
				num++;
			}
		}
		//生成随机数
		if (num > 1)
			return l[(int) (Math.random() * num)];
		else if (num == 1)
			return l[0];
		else
			return -1;
		
	}
	
	/*
	 * 功能：文件写入
	 * 参数name：文件名称
	 * 参数content：写入内容
	 * */
	public static void WriteIn(String name, String content) {
		try {
			File file = new File(name);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(content);// 往文件里写入字符串
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	/*
	 * 功能：程序随机的从图中选择一个节点，以此为起点沿出边进行随机遍历，记录经 过的所有节点和边，
	 * 直到出现第一条重复的边为止，或者进入的某个节点不存在出边为止
     * */
	public static String randomWalk() {
		int len = v.length;
		int ransp;
		String result = new String();
		ransp = (int)(Math.random()*len);//产生随机节点
		
		int[][] used = new int[len][len];
		for (int i = 0; i < len; i++)
			for (int j = 0; j < len; j++)
			    used[i][j] = -1;
		
		int a =ransp;
		result += v[a];
        
		while (random(a) != -1) {
			int b = random(a);//产生随机边
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
	   
	   System.out.println("欢迎使用！");
	   Scanner tmp = new Scanner(System.in);
	   //导入文本
	   while(true){
		   System.out.println("请先导入文本！");
		   String loc = new String();
		   String name = new String();
		   System.out.println("请输入文件地址：");
		   loc = tmp.nextLine();
		   System.out.println("请输入文件名称：");
		   name = tmp.nextLine();
		   test = InputFile(loc,name);
		   if(test.equals("") != true){
			   System.out.println("文件导入成功！");	
			   break;
		   }
	   }
	   
	   v = VerCreate(test);//顶点集合	   
	   edges = EdgesCreate(test);//边集集合	   
	   String[][] edgeweight = EdgeWeight(edges);//计算边的权值	   
	   Arrays.sort(v);
	   matrix = Matrix(edgeweight,v);//邻接矩阵
	   
	   while(true){
		   
		   System.out.println("以下是功能列表：");
		   System.out.println("1.生成图像");
		   System.out.println("2.查桥接词");
		   System.out.println("3.生成新句");
		   System.out.println("4.最短路径(两个单词)");
		   System.out.println("5.最短路径（一个单词）");
		   System.out.println("6.随机游走");
		   System.out.println("0.退出程序");
		   System.out.println("====================");
		   System.out.println("请输入功能序号：");
		   
		   int num = tmp.nextInt();	
		   switch(num){
		   case 1:
			   System.out.println("欢迎使用生成图像功能！");
			   showDirectedGraph(edgeweight);
			   System.out.println("图像生成成功！");
			   break;
			   
		   case 2:
			   System.out.println("欢迎使用查询桥接词功能！");
			   System.out.println("请输入需要查询桥接词的两个单词(两个单词之间用换行符分隔）：");
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
			   System.out.println("欢迎使用生成新句子功能！");
			   System.out.println("请输入文本：");
			   String sentence = new String();
			   tmp.nextLine();
			   sentence = tmp.nextLine();
			   String[] newword = test.split(" ");
			   BridgeCreate(newword);
			   String NewText = generateNewText(sentence);
			   System.out.println("生成的新文本为：");
			   System.out.println(NewText);
			   break;
			   
		   case 4:
			   System.out.println("欢迎使用查询最短路径（两个单词）功能！");
			   System.out.println("请输入需要查询的单词：");
			   String word1 = new String();
			   String word2 = new String();
			   tmp.nextLine();//消除回车换行
			   word1 = tmp.nextLine();
			   word2 = tmp.nextLine();				   
			   String res = new String();
			   res = calcShortestPath(word1,word2);
			   System.out.println(res);
			   break;

		   case 5:
			   System.out.println("欢迎使用查询最短路径（一个单词）功能！");
			   System.out.println("请输入需要查询的单词：");
			   tmp.nextLine(); //消除回车换行
			   String word = new String();
			   word = tmp.nextLine();
			   calcShortestPathOne(word);
			   break;
			   
		   case 6:
			   System.out.println("欢迎使用随机游走功能！");
			   System.out.println("请1继续随机游走！输入其他结束随机游走");
			   
			   String tp2 = new String();
			   tmp.nextLine();
			   while (tmp.nextInt() == 1) {
					tp2 += randomWalk();
					tp2 += "\t\n";
					WriteIn("random.txt", tp2);
			   }			   
			   break;
			   
		   case 0:
			   System.out.println("感谢使用，再见！");
			   System.exit(0);
			   break;
			   
		   default:
			   tmp.nextLine(); //消除回车换行
			   System.out.println("请输入正确序号！");
			   break;
		      
		   }
		   
	   } 
   //tmp.close();  
   }


}
