package com.wtyt.yzone.lucene;

public class LuceneQueryBean {
	
	private String indexPath = LuceneUtil.INDEX_STORE_PATH;//索引的路径位置
	
	private int queryCount = 1000;//默认每次索引出1000条数据

	
	/**
	 * @return the indexPath
	 */
	public String getIndexPath() {
		return indexPath;
	}

	/**
	 * @param indexPath the indexPath to set
	 */
	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	/**
	 * @return the queryCount
	 */
	public int getQueryCount() {
		return queryCount;
	}

	/**
	 * @param queryCount the queryCount to set
	 */
	public void setQueryCount(int queryCount) {
		this.queryCount = queryCount;
	}
	
	
	
	

}
