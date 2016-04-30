package com.huang.utils;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * 工具类
 * @author huanggh
 * @createDate 2016-04-28 17:14
 */
public class MyUtils {
	
	private static final Logger logger = Logger.getLogger(MyUtils.class);

	public static void main(String[] args) {
		
		doFetchKingdomZQTest();
	}
	
	/**
	 * 执行试题抓取,并存储到MySQL数据库中
	 */
	private static void doFetchKingdomZQTest(){
		/**
		 *MySQL查询
		 SELECT
			t.question_title,
			t.question_waiting_answer,
			t.question_true_answer,
			t.business_name,
			t.question_id
		FROM
			kingdom_testing_answer t
		where t.question_title like '%sdsf%'
		 */
		
		long beginTime = System.currentTimeMillis();
		List<KingdomResult> kingdomResults = new ArrayList<KingdomResult>();
		
		new MyUtils().fetchKingdomZQTest(kingdomResults);
		
		long endHtmlToListData = System.currentTimeMillis();
		
		Connection conn = null;
		Statement stat = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/learning";
			String user = "root";
			String password = "*****";
			
			conn = (Connection) DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			stat = (Statement) conn.createStatement();
			
			for(KingdomResult kr: kingdomResults) {
				StringBuilder sb = new StringBuilder();
				
				sb.append("INSERT INTO kingdom_testing_answer(business_name,question_id,question_title,question_waiting_answer,question_true_answer) VALUES");
				sb.append("('").append(kr.businessName).append("',");
				sb.append(kr.questionId).append(",");
				sb.append("'").append(kr.questionTitle).append("',");
				sb.append("'").append(kr.questionWaitingAnswer).append("',");
				sb.append("'").append(kr.queetionTrueAnswer).append("');");
				
				stat.addBatch(sb.toString());
			}
			
			stat.executeBatch();
			conn.commit();
					
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		long endTime = System.currentTimeMillis();
		
		logger.info("Html数据转成List数据耗时:"+ (endHtmlToListData-beginTime));
		logger.info("List数据存储到数据库耗时:"+ (endTime-endHtmlToListData));
		logger.info("总耗时:"+ (endTime-beginTime));
	}
	
	/*
	 * 解析金证-证券测试试题(HTML文件)
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private void fetchKingdomZQTest(List<KingdomResult> kingdomResults){
		String filePath = "E:/javaDir/kingdomZQTest";//试题Html文件
		Gson gson = new Gson();
		File file = new File(filePath);
		File[] files = file.listFiles();
		
		if(files != null) {
			for(File f: files) {//遍历文件
				try {
					List<Map<String,String>> listRs = null;
					Document document = Jsoup.parse(f, "UTF-8");
					
					//标题
					Elements elements = document.getElementsByTag("title");
					Element e = elements.get(0);
					
					String businessName = null;
					
					if(e != null) {
						Node node = e.childNode(0);
						businessName = node.outerHtml();//标题
					}
					
					//遍历题目
					Elements e1s = document.getElementsByClass("questioncontent");
					
					for(Element e1: e1s) {
						
						String questionId = e1.getElementsByTag("label").get(0).text();
						String questionName1 = e1.getElementsByTag("h4").get(0).text();
						
						String questionValue = "";
						Elements eLi = e1.getElementsByTag("li");
						Elements eTextArea = e1.getElementsByTag("textarea");
						
						if(eLi != null) {
							for(Element eLi1: eLi) {
								questionValue += eLi1.text() + "  ";
							}
						}
						
						if(eTextArea != null) {
							for(Element eTextArea1: eTextArea) {
								questionValue = "textArea";
							}
						}
						
						KingdomResult kr = new KingdomResult();
						
						kr.businessName = businessName;
						kr.questionId = Integer.parseInt(questionId.replace(".", ""));
						kr.questionTitle = questionName1;
						kr.questionWaitingAnswer = questionValue;
						
						kingdomResults.add(kr);
						
					}
					
					//获取javascript中答案
					Elements eRs = document.getElementsByTag("script");
					if(eRs != null) {
						for(Element eRs1: eRs) {
							String scriptType = eRs1.attr("type");
							
							if("text/javascript".equals(scriptType)) {
								String jsRs = eRs1.toString();
								
								String temp = jsRs.substring(jsRs.indexOf("window.eval('") + "window.eval('".length() , jsRs.indexOf("');"));
								
								listRs = (List<Map<String,String>>) gson.fromJson(temp, List.class);
							}
						} 
					}
					
					if(listRs != null) {
						for(Map<String,String> listRs1: listRs) {
							String result = "";
							Set<Entry<String,String>> lll = listRs1.entrySet();
							
							Iterator iterator = lll.iterator();
							
							Integer id1 = null;
							String an1 = null;
							
							while(iterator.hasNext()) {
								Entry ee = (Entry) iterator.next();
								result += ee.getKey() + ":" + ee.getValue() +" , ";
								
								if("id".equals(ee.getKey())) {
									id1 = ((Double)ee.getValue()).intValue();
								}
								
								if("answer".equals(ee.getKey())) {
									an1 = (String) ee.getValue();
								}
							}
							
							//添加答案
							for(KingdomResult kr1: kingdomResults) {
								if(kr1.businessName.equals(businessName) 
										&& kr1.questionId == (Integer)id1+1) {
									kr1.queetionTrueAnswer = an1;
									break;
								}
							}
							
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 金证答案封装类
	 */
	private class KingdomResult {
		
		private String businessName;//所属业务模块
		
		private Integer questionId;//问题ID
		
		private String questionTitle;//问题内容
		
		private String questionWaitingAnswer;//可选项
		
		private String queetionTrueAnswer;//答案
	}
}
