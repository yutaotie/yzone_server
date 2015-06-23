package com.wtyt.yzone.jedis;


import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import com.wtyt.util.init.StartUp;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;





public class JedisUtil {
	
	private static JedisPool pool = (JedisPool) StartUp.factory.getBean("jedisPool");


	/**
	 * 生产jedis
	 * @return
	 */
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	
	
	
	/**
	 * 销毁jedis
	 * @param jedis
	 */
	public static void returnJedis(Jedis jedis){		
		pool.returnResource(jedis);
	}
	
	/*private static JedisPool pool;

	*//**
	 * 建立连接池 真实环境，一般把配置参数缺抽取出来。
	 * 
	 *//*
	private static void createJedisPool() {

		// 建立连接池配置参数
		JedisPoolConfig config = new JedisPoolConfig();

		// 设置最大连接数
		config.setMaxTotal(100);

		// 设置最大阻塞时间，记住是毫秒数milliseconds
		config.setMaxWaitMillis(1000);

		// 设置空间连接
		config.setMaxIdle(10);

		// 创建连接池
		pool = new JedisPool(config, JedisConstants.SERVER_IP,
				JedisConstants.PORT, JedisConstants.TIMEOUT,
				JedisConstants.PASSWORD);

	}

	*//**
	 * 在多线程环境同步初始化
	 *//*
	private static synchronized void poolInit() {
		if (pool == null)
			createJedisPool();
	}

	*//**
	 * 获取一个jedis 对象
	 * 
	 * @return
	 *//*
	public static Jedis getJedis() {

		if (pool == null)
			poolInit();
		return pool.getResource();
	}

	*//**
	 * 归还一个连接
	 * 
	 * @param jedis
	 *//*
	public static void returnRes(Jedis jedis) {
		pool.returnResource(jedis);
	}
	*/
	
	public static void main(String[] args) {
		
		//http://my.oschina.net/u/273598/blog/101042
		
		   /* Jedis jedis = getJedis();
		    System.out.println(jedis);
		    Jedis jedis2 = getJedis();
		    Pipeline pipeline = jedis.pipelined();
		    long start = System.currentTimeMillis();
		    for (int i = 0; i < 100000; i++) {
		        pipeline.set("p" + i, "p" + i);
		        
		    }
	
		    pipeline.syncAndReturnAll();
		    long end = System.currentTimeMillis();
		    System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " seconds");
		    //jedis.disconnect();
		    //returnJedis(jedis);
		    
		    jedis.lpush("yu_tao_tie", "1234");
		    jedis.lpush("yu_tao_tie", "5678");
		    jedis.lpush("yu_tao_tie", "91011");
		    jedis.lpush("yu_tao_tie", "121314");
		    
		    jedis.del("yu_tao_tie");
		    
		    
		    List<String> list = jedis.lrange("yu_tao_tie", 0, jedis.llen("yu_tao_tie")-1);
		    for(int i=0; i<list.size(); i++) {
		        System.out.println("Stored string in redis:: "+list.get(i));
		      }
		    
		    System.out.println(jedis2);
		    jedis = getJedis();
		  
		    System.out.println(jedis);*/
		    
		  //连接redis ，redis的默认端口是6379
		ExecutorService es = Executors.newFixedThreadPool(100);
		   for(int i=0;i<100;i++){
			   es.execute(new Runnable() {
				public void run() {
					while(true){
						Jedis  jedis = getJedis(); 
						System.out.println("线程"+Thread.currentThread().getName()+",获取jedis:"+jedis);
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {						
							e.printStackTrace();
						}
						returnJedis(jedis);					
					}
				}
			});			   
		   }
		    

		    /* //验证密码，如果没有设置密码这段代码省略
		   

		    jedis.connect();//连接
		    jedis.disconnect();//断开连接

		    Set keys = jedis.keys("*"); //列出所有的key
		       keys = jedis.keys("key"); //查找特定的key

		    //移除给定的一个或多个key,如果key不存在,则忽略该命令. 
		       System.out.println(jedis.del("key1"));
		       System.out.println(jedis.del("key1","key2","key3","key4","key5"));

		    //移除给定key的生存时间(设置这个key永不过期)
		       System.out.println(jedis.persist("key1")); 

		    //检查给定key是否存在
		       System.out.println(jedis.exists("key1")); 

		    //将key改名为newkey,当key和newkey相同或者key不存在时,返回一个错误
		     //  System.out.println(jedis.rename("key1", "key2"));

		    //返回key所储存的值的类型。 
		    //none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表) 
		       System.out.println(jedis.type("key1"));

		    //设置key生存时间，当key过期时，它会被自动删除。 
		       System.out.println(jedis.expire("key1", 5));//5秒过期 

		    //字符串值value关联到key。 
		       System.out.println(jedis.set("key1", "value1")); 

		    //将值value关联到key，并将key的生存时间设为seconds(秒)。 
		    jedis.setex("foo", 5, "haha"); 

		    //清空所有的key
		    jedis.flushAll();

		    //返回key的个数 
		    jedis.dbSize();

		    //哈希表key中的域field的值设为value。 
		    jedis.hset("key1", "field1", "field1-value"); 
		    jedis.hset("key1", "field2", "field2-value"); 

		    Map map = new HashMap(); 
		    map.put("field1", "field1-value"); 
		    map.put("field2", "field2-value"); 
		    jedis.hmset("key1", map); 

		    //返回哈希表key中给定域field的值 
		    jedis.hget("key1", "field1");

		    //返回哈希表key中给定域field的值(多个)
		    List list = jedis.hmget("key1","field1","field2"); 
		    for(int i=0;i<list.size();i++){ 
		       System.out.println(list.get(i)); 
		    } 

		    //返回哈希表key中所有域和值
		    Map<String,String> maps = jedis.hgetAll("key1"); 
		    for(Map.Entry entry: maps.entrySet()) { 
		       System.out.print(entry.getKey() + ":" + entry.getValue() + "\t"); 
		    } 

		    //删除哈希表key中的一个或多个指定域
		    jedis.hdel("key1", "field1");
		    jedis.hdel("key1", "field1","field2");

		    //查看哈希表key中，给定域field是否存在。 
		    jedis.hexists("key1", "field1");

		    //返回哈希表key中的所有域
		    jedis.hkeys("key1");

		    //返回哈希表key中的所有值
		    jedis.hvals("key1");

		    //将值value插入到列表key的表头。 
		    jedis.lpush("key1", "value1-0"); 
		    jedis.lpush("key1", "value1-1"); 
		    jedis.lpush("key1", "value1-2"); 

		    //返回列表key中指定区间内的元素,区间以偏移量start和stop指定.
		    //下标(index)参数start和stop从0开始;
		    //负数下标代表从后开始(-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推)
		    list = jedis.lrange("key1", 0, -1);//stop下标也在取值范围内(闭区间)
		    for(int i=0;i<list.size();i++){ 
		       System.out.println(list.get(i)); 
		    } 

		    //返回列表key的长度。 
		    jedis.llen("key1");

		    //将member元素加入到集合key当中。 
		    jedis.sadd("key1", "value0"); 
		    jedis.sadd("key1", "value1"); 

		    //移除集合中的member元素。 
		    jedis.srem("key1", "value1"); 

		    //返回集合key中的所有成员。 
		    Set set = jedis.smembers("key1"); 

		    //判断元素是否是集合key的成员
		    jedis.sismember("key1", "value2"); 

		    //返回集合key的元素的数量
		    jedis.scard("key1");
		     
		    //返回一个集合的全部成员，该集合是所有给定集合的交集
		    jedis.sinter("key1","key2");
		     
		    //返回一个集合的全部成员，该集合是所有给定集合的并集
		    jedis.sunion("key1","key2");

		    //返回一个集合的全部成员，该集合是所有给定集合的差集
		    jedis.sdiff("key1","key2");
		    
		    
		    jedis.zrangeByScoreWithScores("", 1, 10, 5, 10);*/
		    
		   // lrem(final String key, long count, final String value) 
		       //清空所有的key
		       //jedis.flushAll();
		      /* Pipeline  piple = jedis.pipelined();		     
			   piple.hsetnx("ZT_1", "isRoot", "0");
			   piple.hsetnx("ZT_1", "fartherId", "0");
			   piple.hsetnx("ZT_1", "belongUserId", "1234");
			   piple.hsetnx("ZT_1", "content", "我是主题贴");
			   
			 
			   

			   
			   piple.hsetnx("ZT_2", "isRoot", "1");
			   piple.hsetnx("ZT_2", "fartherId", "ZT_1");
			   piple.hsetnx("ZT_2", "belongUserId", "5647");
			   piple.hsetnx("ZT_2", "content", "我是主题贴的回复2");			   
			   piple.rpush("ZT_1_SON", "ZT_2");
			   
			   
			   piple.hsetnx("ZT_3", "isRoot", "1");
			   piple.hsetnx("ZT_3", "fartherId", "ZT_1");
			   piple.hsetnx("ZT_3", "belongUserId", "1234");
			   piple.hsetnx("ZT_3", "content", "我是主题贴的回复3");
			   piple.rpush("ZT_1_SON", "ZT_3");
			   
			   
			   piple.hsetnx("ZT_4", "isRoot", "1");
			   piple.hsetnx("ZT_4", "fartherId", "ZT_1");
			   piple.hsetnx("ZT_4", "belongUserId", "1234");	
			   piple.hsetnx("ZT_4", "content", "我是主题贴的回复4");
			   piple.rpush("ZT_1_SON", "ZT_4");
			  
			   
			   
			   piple.hsetnx("ZT_5", "isRoot", "1");
			   piple.hsetnx("ZT_5", "fartherId", "ZT_3");
			   piple.hsetnx("ZT_5", "belongUserId", "1234");
			   piple.hsetnx("ZT_5", "content", "我是回复3的回复");
			   piple.rpush("ZT_3_SON", "ZT_5"); 
			   
			   piple.hsetnx("ZT_8", "isRoot", "1");
			   piple.hsetnx("ZT_8", "fartherId", "ZT_3");
			   piple.hsetnx("ZT_8", "belongUserId", "1234");
			   piple.hsetnx("ZT_8", "content", "我是回复3的回复");
			   piple.rpush("ZT_3_SON", "ZT_8");
			   
			   
			  
			   
			   
			   piple.hsetnx("ZT_6", "isRoot", "1");
			   piple.hsetnx("ZT_6", "fartherId", "ZT_5");
			   piple.hsetnx("ZT_6", "belongUserId", "1234");
			   piple.hsetnx("ZT_6", "content", "我是回复5的回复");
			   piple.rpush("ZT_5_SON", "ZT_6");
			   
			   piple.hsetnx("ZT_7", "isRoot", "1");
			   piple.hsetnx("ZT_7", "fartherId", "ZT_2");
			   piple.hsetnx("ZT_7", "belongUserId", "1234");
			   piple.hsetnx("ZT_7", "content", "我是回复2的回复");
			   piple.rpush("ZT_2_SON", "ZT_7");
			   
			   
			   piple.syncAndReturnAll();
			   
			   
			   getPostsDetail("ZT_1");
			   
			   deletePostsDetail("ZT_2");*/
			   
			  /* Map<String,String> maps = jedis.hgetAll("ZT_1"); 
			   for(Map.Entry entry: maps.entrySet()) { 
			       System.out.print(entry.getKey() + ":" + entry.getValue() + "\t"); 
			   }  */

	}


	/**
	 * 
	 * @param string
	 */
	private static void deletePostsDetail(String string) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 获取帖子内容
	 * @param string
	 */
	private static void getPostsDetail(String key) {
		Jedis jedis = getJedis();		
		System.out.println("KEY:="+key+","+jedis.hget(key, "content"));
		getSonPostsDetailAsy(jedis,key);
		
	}


	/**
	 * 获取字帖内容
	 * @param jedis
	 * @param key
	 */
	private static void getSonPostsDetailAsy(Jedis jedis, String key) {
		List<String> list = null;
		long length = jedis.llen(key+"_SON");
		System.out.println(jedis.llen(key+"_SON"));
		if(length>0){			
			list = jedis.lrange(key+"_SON", 0,length);
			for(String keySon:list){
				System.out.println("KEY:="+keySon+","+jedis.hget(keySon, "content"));
				getSonPostsDetailAsy(jedis,keySon);
			}			
		}
		
	}




	public void run() {
		// TODO Auto-generated method stub
		
	}

}
