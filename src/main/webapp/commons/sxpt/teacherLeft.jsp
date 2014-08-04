<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="left">
	<div class="leftit">--==备课管理 ==--</div>
    <div class="menu">
	  <table width="142" border="0" align="center" cellpadding="0" cellspacing="0">
	  		<tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/lesson/LessonPrepare!list.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}" target="mainFrame">备课管理</a></td>
	  		</tr>
	  		<tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/exam/index.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}&query.lessonType=2" target="mainFrame">教学测验</a></td>
	  		</tr>
	  		<tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/lesson/Inform!list.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}" target="mainFrame">通知公告</a></td>
	  		</tr>
	  		<%--<c:if test="${role != 0&& role != 1}">
	  		--%><tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/lesson/PrepareExample!list.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}" target="mainFrame">范例展示</a></td>
	  		</tr>
	  		<%--</c:if>
	  		--%><tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/lesson/PrepareWork!list.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}" target="mainFrame">布置作业</a></td>
	  		</tr>
	  		<tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/discuss/Topic!list.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.actorId=${classId}&query.type=3" target="mainFrame">班级讨论</a></td>
	  		</tr>
	  		<tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/survey/project!list.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}&query.state=2" target="mainFrame">问卷调查</a></td>
	  		</tr>
	  		<c:if test="${query.role>1&&query.role<5}">
	  		<tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/res/publish!list.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}" target="mainFrame">资源发布</a></td>
	  		</tr>
	  		</c:if>
	  		<c:if test="${query.role == 2 || query.role == 3 || query.role == 4}">
	  		<tr>
	  			<td width="44" height="44">
	  				<img src="${ctx}/${sxpt}/images/menu02.png">
	  			</td>
	            <td width="98"><a href="${ctx}/discuss/TopicGroup!list.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}" target="mainFrame">讨论组管理</a></td>
	  		</tr>
	  		</c:if>
	  </table>
    </div>
    <c:if test="${query.role == 2 || query.role == 3 || query.role == 4}">
    <div class="leftit">--==教学管理==--</div>
    <div class="menu">
	  <table width="142" border="0" align="center" cellpadding="0" cellspacing="0">
	  	<tr>
  			<td width="44" height="44">
  				<img  src="${ctx}/${sxpt}/images/menu02.png">
  			</td>
            <td width="98"><a href="${ctx }/bj/ClassBjUser!findBjUsers.do?query.classBjId=${classId}" target="mainFrame">学员名单</a></td>
  		</tr>
  		<tr>
  			<td width="44" height="44">
  				<img  src="${ctx}/${sxpt}/images/menu02.png">
  			</td>
            <td width="98"><a href="${ctx}/bj/ClassTimeTable!fullCalendar.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}" target="mainFrame">班级课表</a></td>
  		</tr>
	  </table>
    </div>
    </c:if>
     <div class="leftit">--==发布预览==--</div>
    <div class="menu">
    	<table width="142" border="0" align="center" cellpadding="0" cellspacing="0">
    		<tr>
    			<td width="44" height="44">
    				<img  src="${ctx}/${sxpt}/images/menu02.png">
    			</td>
    			<td width="98"><a href="/course/myCourse!publishPreview.do?query.role=${role}&query.termId=${termId}&query.courseId=${courseId}&query.classId=${classId}" target="_blank">发布预览</a></td>
    		</tr>
    	</table>
    </div>
</div>