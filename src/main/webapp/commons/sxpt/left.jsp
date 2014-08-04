<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="left">
     <c:forEach  items="${allMenus }" var="item">
     <div class="leftit">--==${item.menuName} ==--</div>
    <div class="menu">
	  <table width="142" border="0" align="center" cellpadding="0" cellspacing="0">
	  	<c:forEach items="${item.children }" var = "menu">
	  		<tr>
	  			<td width="44" height="44">
	  				<img  src="${ctx }/${sxpt }/${menu.imgUrl }">
	  			</td>
	            <td width="98"><a href="${menu.menuUrl }" target="mainFrame">${menu.menuName }</a></td>
	  		</tr>
	  	</c:forEach>
	  </table>
    </div>
    </c:forEach>
</div>
