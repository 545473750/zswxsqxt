<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/repeatSubmit.tld"  prefix="repeatsubmit" %>
<%@ taglib uri="/WEB-INF/tld/language.tld"  prefix="lg" %>
<%//用于实现jsp模板的继承关系,请查看:http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="pattern" value="pattern/default"/>
<c:set var="sxpt" value="pattern/sxpt"/>
<c:set var="portal" value="pattern/portal"/>
