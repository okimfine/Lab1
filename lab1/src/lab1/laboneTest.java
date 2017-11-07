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
		/*��ȷ����*/
		String word1 = "to";
		String word2 = "a";
		String res = "to��a���·��Ϊ:\n" + 
				"to->meet->massive->individualized->customer->requirements->is->a ·������Ϊ  7";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath2() {
		/*�������ʶ�����ͼ��*/
		String word1 = "happy";
		String word2 = "year";
		String res = "��������ȷ�ĵ��ʣ�";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath3() {
		/*һ��������ͼ�У�һ�����ʲ���*/
		String word1 = "to";
		String word2 = "year";
		String res = "��������ȷ�ĵ��ʣ�";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath4() {
		/*����������ͬ*/
		String word1 = "to";
		String word2 = "to";
		String res = "������������ͬ�ĵ��ʣ���л��";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath5() {
		/*���뵥��·�����ɴ�*/
		String word1 = "up";
		String word2 = "week";
		String res = "up �� week ���ɴ";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath6() {
		/*���뵥�ʸ�����0��*/
		String word1 = "";
		String word2 = "";
		String res = "��������ȷ�ĵ��ʣ�";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath7() {
		/*���뵥�ʸ�����1��*/
		String word1 = "up";
		String word2 = "";
		String res = "��������ȷ�ĵ��ʣ�";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath8() {
		/*���뵥�ʸ��������*/
		String word1 = "to a";
		String word2 = "week";
		String res = "��������ȷ�ĵ��ʣ�";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath9() {
		/*���뵥�ʺ��д�д��ĸ*/
		String word1 = "Week";
		String word2 = "up";
		String res = "��������ȷ�ĵ��ʣ�";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
	
	@Test
	public void testCalcShortestPath10() {
		/*���뵥�ʺ��������Ƿ��ַ�*/
		String word1 = "w**k";
		String word2 = "&p";
		String res = "��������ȷ�ĵ��ʣ�";
		assertEquals(res,labonetest.calcShortestPath(text,word1, word2));
	}
}


