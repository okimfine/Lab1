package lab1;

import static org.junit.Assert.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class laboneTest {

	private static  labone labonetest = new labone();
	private static  String text = "to meet massive individualized customer requirements is a key issue in the big data and big service ecosystem week good day day up";
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalcShortestPath1() {
		/*正确输入*/
		String word1 = "to";
		String word2 = "a";
		String res = "to和a最短路径为:\n" + 
				"to->meet->massive->individualized->customer->requirements->is->a 路径长度为  7";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath2() {
		/*两个单词都不在图中*/
		String word1 = "happy";
		String word2 = "year";
		String res = "请输入正确的单词！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath3() {
		/*一个单词在图中，一个单词不在*/
		String word1 = "to";
		String word2 = "year";
		String res = "请输入正确的单词！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath4() {
		/*两个单词相同*/
		String word1 = "to";
		String word2 = "to";
		String res = "请输入两个不同的单词，感谢！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath5() {
		/*输入单词路径不可达*/
		String word1 = "up";
		String word2 = "week";
		String res = "up 和 week 不可达！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath6() {
		/*输入单词个数：0个*/
		String word1 = "";
		String word2 = "";
		String res = "请输入正确的单词！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath7() {
		/*输入单词个数：1个*/
		String word1 = "up";
		String word2 = "";
		String res = "请输入正确的单词！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath8() {
		/*输入单词个数：多个*/
		String word1 = "to a";
		String word2 = "week";
		String res = "请输入正确的单词！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath9() {
		/*输入单词含有大写字母*/
		String word1 = "Week";
		String word2 = "up";
		String res = "请输入正确的单词！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath10() {
		/*输入单词含有其他非法字符*/
		String word1 = "w**k";
		String word2 = "&p";
		String res = "请输入正确的单词！";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
}


