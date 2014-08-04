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

     function gotoTopAnyPage(pageAdd)
     {
			
		    		    
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
		  	     
	      document.getElementById("_topPageForm_").pageNumber.value=pageNum;
	      document.getElementById("_topPageForm_").submit();
	     
     }
     
</script>
  
		<%if (_curPageNo_ <= 1 ) {%> <a href="#" class="fb2">上一页</a> 
        <%} else {%> 
        <a href="#" onclick="gotoTopAnyPage(<%=_curPageNo_-1%>)" >上一页</a>
         <%} %> 
          &nbsp;&nbsp;
       
        <%if (_curPageNo_ >= _allPageNum_) {%> 
        <a href="#" class="fb2">下一页</a> 
        <%} else {%> 
        <a href="#"  onclick="gotoTopAnyPage(<%=_curPageNo_+1%>)" >下一页</a> 
        <%}%> &nbsp;&nbsp;

<form id="_topPageForm_" method="post" name="_topPageForm_" action="<%=target%>">
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
