/*
 * [文 件 名]:PageListDto.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-8-17
 * [编　　码]: UTF-8
*/

package com.b5m.web.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *	[简要描述]:
 *	[详细描述]:
 *	@author	[Wiley]
 *	@email	[wiley.wang@b5m.com]
 *	@version	[版本号,2012-8-17]
 *	@see		[PageListDto]
 *	@package	[com.b5m.plugin]
 *	@since	[comb5mpluginserver]
 */
public class B5MPageList<E> implements Serializable
{

    private static final long serialVersionUID = -311871945728875353L;
    
    private List<E> all=new ArrayList<E>();
    
    private int pageNo;

    private int pageSize;

    private long totalCount;

    private int totalPages;
    
    private long beginRow;
    
    private long endRow;
    
    private boolean isFirstPage;
    
    private boolean isLastPage;
    
    private boolean hasPrevPage;
    
    private boolean hasNextPage;
    
    private String toolBar;

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(long totalCount)
    {
        this.totalCount = totalCount;
    }

    public int getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }
    
    public long getBeginRow()
    {
        return beginRow;
    }

    public void setBeginRow(long beginRow)
    {
        this.beginRow = beginRow;
    }

    public long getEndRow()
    {
        return endRow;
    }

    public void setEndRow(long endRow)
    {
        this.endRow = endRow;
    }
   
    public boolean isFirstPage()
    {
        return isFirstPage;
    }

    public void setFirstPage(boolean isFirstPage)
    {
        this.isFirstPage = isFirstPage;
    }

    public boolean isLastPage()
    {
        return isLastPage;
    }

    public void setLastPage(boolean isLastPage)
    {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPrevPage()
    {
        return hasPrevPage;
    }

    public void setHasPrevPage(boolean hasPrevPage)
    {
        this.hasPrevPage = hasPrevPage;
    }

    public boolean isHasNextPage()
    {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage)
    {
        this.hasNextPage = hasNextPage;
    }

    public List<E> getAll(){
        return all;
    }
    
    public void addAll(Collection<? extends E> c){
        all.addAll(c);
    }
    
    public void buildToolBar(String uri){
        StringBuffer buf = new StringBuffer();
        if (this.isFirstPage())
        {
            buf.append("<a href=\"javascript:void(-1);\">首页</a>");
        }
        else
        {
            buf.append("<a href=\""+uri+"&page=1\">首页</a>");
        }
        if (this.isHasPrevPage())
        {
            buf.append("<a href=\""+uri+"&page=" + (this.getPageNo() - 1) + "\">上一页 </a>");
        }
        else
        {
            buf.append("<a href=\"javascript:void(-1);\">上一页</a>");
        }
        if (this.isHasNextPage())
        {
            buf.append("<a href=\""+uri+"&page=" + (this.getPageNo() + 1) + "\">下一页</a>");
        }
        else
        {
            buf.append("<a href=\"javascript:void(-1);\">下一页</a>");
        }
        if (this.isLastPage())
        {
            buf.append("<a href=\"javascript:void(-1);\">末页</a>");
        }
        else
        {
            buf.append("<a href=\""+uri+"&page=" + this.getTotalPages() + "\">末页</a>");
        }
        this.toolBar=buf.toString();
    }
    
    public String getToolBar(){
        return this.toolBar;
    }
    
    private void updatePageInfo(){
    	int temp = Integer.parseInt((this.totalCount / this.pageSize) + "");
    	if(this.totalCount % this.pageSize != 0){
    		temp = temp + 1;
    	}
    	this.totalPages = temp;
    }
    
}
