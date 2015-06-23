package com.wtyt.yzone.lucene;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneUtil {
	
	/**索引的存放位置*/
	public final static String INDEX_STORE_PATH = "F:\\lucene\\index";  
	
	/**
	 * 获取索引路径
	 * @param fliePath
	 * @return
	 * @throws IOException 
	 */
	public static Directory getDirectory(LuceneQueryBean luceneQueryBean) throws IOException{
		return FSDirectory.open(new File(luceneQueryBean.getIndexPath()));		
	}
	
	
    /**
     * 查询出索引的值
     * @param fieldName
     * @param content
     * @param storeType
     * @throws IOException 
     * @throws ParseException 
     */
    public static List<Document> queryParser(String fieldName,String keyWord,Store storeType,LuceneQueryBean luceneQueryBean) throws IOException, ParseException{
    	Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);  
    	QueryParser parser = new QueryParser(Version.LUCENE_46,fieldName,analyzer);  
    	Query query = parser.parse(keyWord);
    	Directory dire=getDirectory(luceneQueryBean);
	    IndexReader ir=DirectoryReader.open(dire);  
	    IndexSearcher is=new IndexSearcher(ir);  	    
	    TopDocs td=is.search(query, luceneQueryBean.getQueryCount());  
	    System.out.println("共为您查找到"+td.totalHits+"条结果");  
	    ScoreDoc[] sds =td.scoreDocs;
	    List<Document> listDoc = new ArrayList<Document>();
	    for(int i=0;i<sds.length;i++){
	    	int docId = sds[i].doc;
	    	Document doc =is.doc(docId);
	    	listDoc.add(doc);
	    }
    	return listDoc;
    }
    
    
    
    /**
     * 查询出索引的值(多字段索引排序)
     * @param fieldName
     * @param content
     * @param storeType
     * @throws IOException 
     * @throws ParseException 
     */
    public static List<Document> queryMutiParserSort(String fieldName[],String keyWord[],Occur[] occ,Sort sort,LuceneQueryBean luceneQueryBean) throws IOException, ParseException{
    	Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);      	
    	Query query = MultiFieldQueryParser.parse(Version.LUCENE_46,keyWord,fieldName,occ,analyzer);
    	Directory dire= getDirectory(luceneQueryBean);
	    IndexReader ir=DirectoryReader.open(dire);  
	    IndexSearcher is=new IndexSearcher(ir);  	    
	    TopDocs td=is.search(query,luceneQueryBean.getQueryCount(),sort);        
	    System.out.println("共为您查找到"+td.totalHits+"条结果");  
	    ScoreDoc[] sds =td.scoreDocs;
	    List<Document> listDoc = new ArrayList<Document>();
	    for(int i=0;i<sds.length;i++){
	    	int docId = sds[i].doc;
	    	Document doc =is.doc(docId);
	    	listDoc.add(doc);
	    	//break;
	    }
    	return listDoc;
    }
    
    
    /**
     * 查询出索引的值(多字段索引)
     * @param fieldName
     * @param content
     * @param storeType
     * @throws IOException 
     * @throws ParseException 
     */
    public static List<Document> queryMutiParser(String fieldName[],String keyWord[],Occur[] occ,LuceneQueryBean luceneQueryBean) throws IOException, ParseException{
    	Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);     
    	BooleanQuery booleanQuery = new BooleanQuery();
    	Query query = MultiFieldQueryParser.parse(Version.LUCENE_46,keyWord,fieldName,occ,analyzer);
    	booleanQuery.add(query, Occur.MUST);
    	Directory dire=getDirectory(luceneQueryBean);
	    IndexReader ir=DirectoryReader.open(dire);  
	    IndexSearcher is=new IndexSearcher(ir);  	    
	    TopDocs td=is.search(booleanQuery,luceneQueryBean.getQueryCount());        
	    System.out.println("共为您查找到"+td.totalHits+"条结果");  
	    ScoreDoc[] sds =td.scoreDocs;
	    List<Document> listDoc = new ArrayList<Document>();
	    for(int i=0;i<sds.length;i++){
	    	int docId = sds[i].doc;
	    	Document doc =is.doc(docId);
	    	listDoc.add(doc);
	    }
    	return listDoc;
    }
    
    
    /**
     * 创建索引
     * @param analyzer
     * @param fieldName
     * @param content
     * @param storeType
     * @throws IOException 
     */
    public static void createIndex(String fieldName,String content, Store storeType,LuceneQueryBean luceneQueryBean) throws IOException {
    	   Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);  
    	   Directory dire = getDirectory(luceneQueryBean); 
	       IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_46, analyzer);  
	       IndexWriter iw=new IndexWriter(dire, iwc);  
	       LuceneUtil.addDoc(iw,fieldName,content,storeType);  
	       iw.close();			
	}
    
    
    /**
     * 创建索引
     * @param luceneBean
     * @param storeType
     * @throws IOException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void createIndex(LuceneBean luceneBean, Store storeType,LuceneQueryBean luceneQueryBean) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
	 	   Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);  
	 	   Directory dire= getDirectory(luceneQueryBean);
		   IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_46, analyzer);  
		   IndexWriter iw=new IndexWriter(dire, iwc);  
		   LuceneUtil.addDoc(iw,luceneBean,storeType);  
		   iw.close();			
	}
    
    
    
    /**
     * 添加索引
     * @param iw
     * @param luceneBean
     * @param storeType
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws IOException 
     */
    private static void addDoc(IndexWriter iw, LuceneBean luceneBean,Store storeType) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
    	 Document doc=new Document(); 
    	 Class<?> clazz = LuceneBean.class;
    	 Field fileds[] = clazz.getDeclaredFields();     
    	 for(int i = 0; i<fileds.length; i++){
    		 String fieldName =  fileds[i].getName();
    		 String methodName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1, fieldName.length());
    		 Method method = clazz.getDeclaredMethod(methodName);
    		 Object o =  method.invoke(luceneBean);
    		 String content = "";
    		 if(null!=o){
    			 content = o+"";
        		 doc.add(new TextField(fieldName,content,storeType)); 
    		 }
    	 }		
		 iw.addDocument(doc);  
         iw.commit(); 
         iw.close();		
	}


	/**
     * 删除具体的索引，根据关键字
     * @param analyzer
     * @param fieldName
     * @param content
     * @throws ParseException 
     * @throws IOException 
     */
    public static void deleteIndex(String fieldName,String content,LuceneQueryBean luceneQueryBean) throws ParseException, IOException{
    	Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);  
    	Directory dire= getDirectory(luceneQueryBean); 
	    QueryParser parser = new QueryParser(Version.LUCENE_46,fieldName,analyzer);  
	    BooleanQuery booleanQuery = new BooleanQuery();
	    Query query = parser.parse(content);	 
	    booleanQuery.add(query, Occur.MUST);
		IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_46, analyzer);  
	    IndexWriter iw=new IndexWriter(dire, iwc);  
	    iw.deleteDocuments(booleanQuery);
	   	iw.commit();
	 	iw.close(); 
	   
    }

    
    /**
     * 删除具体的索引，根据关键字
     * @param analyzer
     * @param fieldName
     * @param content
     * @throws ParseException 
     * @throws IOException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     */
    public static void updateIndex(String fieldName,String content,LuceneBean newLuceneBean,Store storeType,LuceneQueryBean luceneQueryBean) throws ParseException, IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
    	Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_46);  
    	QueryParser parser = new QueryParser(Version.LUCENE_46,fieldName,analyzer);  
    	Query query = parser.parse(content);
    	Directory dire= getDirectory(luceneQueryBean);
	    IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_46, analyzer);  
	    IndexWriter iw=new IndexWriter(dire, iwc);  
	    iw.deleteDocuments(query);
	    LuceneUtil.addDoc(iw,newLuceneBean,storeType);	    
    }

    /**
     * 创建索引内容
     * @param iw
     * @param fieldName
     * @param content
     * @param storeType
     * @throws IOException 
     */
	private static void addDoc(IndexWriter iw, String fieldName,String content, Store storeType) throws IOException {
		 Document doc=new Document(); 
		 doc.add(new TextField(fieldName, content,storeType));  
		 iw.addDocument(doc);  
         iw.commit(); 
         iw.close();
	}



	 public static void main(String[] args) throws Exception{  
		  /* String[] str = new String[]{"波哥","叶生","土豪金","阿萨德","复合风管","ioi","请问","皇家空军和","该发言人陶","涂鸦","大范甘迪","54415","hjkh","gfbhf","与i呼吁i","hhih","周戴","裴总","一二三四一二三期"};
		   Random rand = new Random();
		   StringBuffer bf;
		   long  start = new Date().getTime();
		   long  end;
		   for(int j=10000; j<100000;j++){
			     bf = new StringBuffer();
			     LuceneBean luceneBean = new LuceneBean();
				 luceneBean.setId("36"+j);				 
				 for(int k=0;k<rand.nextInt(100)+1;k++){
					 bf.append(str[rand.nextInt(1000)%str.length]);					
				 }	
				 luceneBean.setContent(bf.toString());				 
				 luceneBean.setTitle("测试索引"+j);
				 luceneBean.setTime("20150608");
				 createIndex(luceneBean,Store.YES);
				 end = new Date().getTime();
				 double d = (end-start)/1000;
				 System.out.println("j="+j+",time="+d);
		   }*/
			/* LuceneBean luceneBean = new LuceneBean();
			 luceneBean.setId("34546");
			 luceneBean.setContent("阿达三大煽风点火首付款三代福克斯集散地");
			 luceneBean.setTitle("测试索引");
			 luceneBean.setTime("20150608");
			 //createIndex(luceneBean,Store.YES);
			 System.out.println("sdadasd");*/
			/* List<Document> listDoc =  LuceneUtil.queruParser("title", "测试索引", Store.YES);
			 for(int i=0;i<listDoc.size();i++){		
				Document doc = listDoc.get(i);
				System.out.println("---doc--id---"+doc.get("id")+",----doc--content-----"+doc.get("content"));
			 }*/
			// LuceneUtil.deleteIndex("content", "三");
			 
			/* LuceneBean luceneBean1 = new LuceneBean();
			 luceneBean1.setId("34546");
			 luceneBean1.setContent("阿达四大煽风点火首付款四代福克斯集散地");
			 luceneBean1.setTitle("呵呵");
			 luceneBean1.setTime("20150608");
			 createIndex(luceneBean1,Store.YES);
			 LuceneUtil.updateIndex("id", "34546", luceneBean1,Store.YES);
			 listDoc =  LuceneUtil.queruParser("content", "四", Store.YES);
			 for(int i=0;i<listDoc.size();i++){		
				Document doc = listDoc.get(i);
				System.out.println("---doc--id---"+doc.get("id")+",----doc--content-----"+doc.get("content"));
			 }*/
		 
		     /**
		      * 存放索引的路径 ，以及每次索引查询出的条数
		      */
		     LuceneQueryBean  luceneQueryBean = new LuceneQueryBean();
		     
		     SortField sortsfield = new SortField("id",Type.INT, true);//按ID大小降序排列
		     SortField sortsfield1 = new SortField("time",Type.STRING, true);//按时间字符串降序排列
		     Sort sort = new Sort(sortsfield,sortsfield1);
		 
			 String[] fields={"content","content","content"};
			 String[] keyWords={"波哥","土豪金","周戴"};
			 Occur[]  occ = {Occur.MUST,Occur.MUST,Occur.MUST};  
			 List<Document> listDoc =  queryMutiParserSort(fields, keyWords, occ, sort,luceneQueryBean);
			// List<Document> listDoc =  queryMutiParser(fields, keyWords, occ,luceneQueryBean);
			// listDoc =  LuceneUtil.queruParser("content", "四", Store.YES);
			 for(int i=0;i<listDoc.size();i++){		
				Document doc = listDoc.get(i);
				System.out.println("---doc--id---"+doc.get("id")+",----doc--content-----"+doc.get("content"));
			 }
			 System.out.println("----listDoc.size()---"+listDoc.size());
		 
	    }  
		
}
