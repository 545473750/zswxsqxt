<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="com.opendata.common.util.PageBar"%>
<%@ page import="cn.org.rapid_framework.page.Page"%>
<%@ page import="java.net.URLEncoder"%>

<%
	try{
	String pageParameter="";//当前页面所有的参数
	PageBar _pageBar_=(PageBar)request.getAttribute("_pageBar_");
	if(_pageBar_!=null)
	{
	Map _Parameters_=_pageBar_.getParameter();
	Set _keys_=_Parameters_.keySet();
	Page _page_=_pageBar_.getPage();
	Iterator _iteKeys_=_keys_.iterator();
	int _curPageNo_=_page_.getThisPageNumber();
	long _allPageNum_=_pageBar_.getAllPageCount();
	long _totalCount_=_page_.getTotalCount();
	int _pageSize_=_page_.getPageSize();
	String target=request.getContextPath()+request.getParameter("target");
%>
<script type="text/javascript"> 

     function gotoAnyPage(pageAdd)
     {
			var pageSize=parseInt(document.getElementById("_pageSize_").value);
			
		    if(pageSize!=""&&(!isNaN(pageSize)))
		    {
		    		
		      if(pageSize<1)
		      {
		         pageSize=<%=_pageSize_%>;
		      }
		      	document._pageForm_.pageSize.value=pageSize;
		    }else
		    {
		      alert("请输入正确的数字!");
		      document.getElementById("_pageSize_").select();
		      return;
		    }
		    		    
	      var pageNum=pageAdd;
	      var countPage="<%=_allPageNum_%>";
	      if(pageNum>countPage)
	      {
	       pageNum=countPage;
	      }
	      if(pageNum<1)
	      {
	        pageNum="1";
	      }
	     
	      document._pageForm_.pageNumber.value=pageNum;
	      document.getElementById("_pageForm_").submit();
	     
     }
     
     function gotoPointPage()
     {
      	
		    
	      var indexPage=parseInt(document.getElementById("indexPage").value);
	     
		    if(indexPage!=""&&(!isNaN(indexPage)))
		    {
		      var countPage=<%=_allPageNum_%>;
		      if(indexPage<1)
		      {
		         indexPage=1;
		      }
		      if(indexPage>countPage)
		      {
		        indexPage=countPage;
		      }
		      gotoAnyPage(indexPage);
		    }else
		    {
		      alert("请输入正确的数字!");
		      document.getElementById("indexPage").select();;
		    }
		    
		   
     }
</script>
  <table border="0" align="center" cellpadding="0" cellspacing="0" width="100%" style="margin-top:-1px;">
    <tr>
      <td height="22" align="center"  >
      	共<%=_totalCount_ %>条&nbsp;&nbsp;
        <font color="red"><%=_curPageNo_%></font> / <%=_allPageNum_%>&nbsp;&nbsp;&nbsp;
        
        <%if (_allPageNum_ <= 1 || _curPageNo_==1) {%> <a href="#" class="fb2" >首页</a> 
        <%} else {%> 
        <a href="#" onclick="gotoAnyPage(1)" >首页</a> 
        <%}%>
        &nbsp;&nbsp;
        
        <%if (_curPageNo_ <= 1) {%> <a href="#" class="fb2">上一页</a> 
        <%} else {%> 
        <a href="#" onclick="gotoAnyPage(<%=_curPageNo_-1%>)" >上一页</a>
         <%} %> 
          &nbsp;&nbsp;
       
        <%if (_curPageNo_ >= _allPageNum_) {%> 
        <a href="#" class="fb2">下一页</a> 
        <%} else {%> 
        <a href="#"  onclick="gotoAnyPage(<%=_curPageNo_+1%>)" >下一页</a> 
        <%}%> &nbsp;&nbsp;
        
         <%
				 if (_curPageNo_ >= _allPageNum_) 
				 {
				%> 
				<a href="#" class="fb2">尾页</a> 
				 <%
				 } else 
				 {
				 %> 
				 <a href="#" onclick="gotoAnyPage(<%=_allPageNum_%>)" >尾页</a> 
			<%
				 }
			%> 
 &nbsp;&nbsp;
 		每页<input name="_pageSize_" id="_pageSize_" type="text"  value="<%=_pageSize_ %>" class="pageinput" />条
		转到<input name="indexPage" id="indexPage" type="text"  value="<%=_curPageNo_ %>" class="pageinput" />页<input type="button" class="but_gray" name="point" value="GO" onclick="gotoPointPage()" />
      </td>
    </tr> 
</table>
<form id="_pageForm_" method="post" name="_pageForm_" action="<%=target%>">
<%
	
  while(_iteKeys_.hasNext())
  {
     String name=_iteKeys_.next().toString();
     String id = name;
     if(name.indexOf(".")!=-1){
    	 id = name.substring(name.indexOf(".")+1);
     }
     String value="";
     if(_Parameters_.get(name)!=null)
     {
      	value=_Parameters_.get(name).toString();
%>
	 <input type="hidden" name="<%=name%>" id="<%=id%>" value="<%=value%>">
<%
	}
  }
 %>
</form>
<%
}
}catch(Exception e)
{
  e.printStackTrace();
}
%>
