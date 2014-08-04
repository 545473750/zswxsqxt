<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript">
<!--
function disableSubmit(finalResult,submitButtonId) {
	if(finalResult) {
		document.getElementById(submitButtonId).disabled = true;
		return finalResult;
	}else {
		return finalResult;
	}
}

function batchDelete(action,checkboxName,form){
    if (!hasOneChecked(checkboxName)){
            alert('请选择要操作的对象!');
            return;
    }
    if (confirm('确定执行[删除]操作?')){
        form.action = action;
        form.submit();
    }
}
//添加
var add = function(action,form){
	form.action = action;
    form.submit();
};
//修改
var edit = function(action,checkboxName,form){
	if (!hasOneChecked(checkboxName)){
        alert('请选择要操作的对象!');
        return;
	} else {
		var items = document.getElementsByName(checkboxName);
		var itemId = null;
		var count = 0;
		for (var i = 0; i < items.length; i++){
	        if (items[i].checked == true){
	        	itemId = items[i].value;
	            count++;
	        }
	    }
		if (count != 1) {
	    	alert('请选择一项!');
	        return;
	    } else {
	    	form.action = action+'?'+itemId;
	        form.submit();
	    }
	}
};

function batchEdit(action,checkboxName,form){
    if (!hasOneChecked(checkboxName)){
            alert('请选择要操作的对象!');
            return;
    } else {
    	var items = document.getElementsByName(checkboxName);
    	var itemId = null;
    	var count = 0;
    	for (var i = 0; i < items.length; i++){
            if (items[i].checked == true){
            	itemId = items[i].value;
                count++;
            }
        }
    	if (count != 1) {
        	alert('请选择一项!');
            return;
        } else {
        	window.parent.frames.mainFrame.location.href = action+'?'+itemId;
        }
    }
    
}



function openDialog(url){
	var horizontalPadding = 30;
    var verticalPadding = 30;
    document.getElementById("addSit").src=url;
   	$("#dialog").dialog( "open" );
    
}

function closeDialog() {
	$('#dialog').dialog('close');
	document.execCommand('Refresh');
	//document.designMode = "off";
	//document.execCommand('refresh', false, "");
}
function closeDialogNoRefresh() {
	$('#dialog').dialog('close');
	if($('#stationDialog') != null) {
		$('#stationDialog').dialog('close');
	}
}

function closeDialogNoRefreshById(dialogId) {
	$('#'+dialogId).dialog('close');
}

function hasOneChecked(name){
    var items = document.getElementsByName(name);
    if (items.length > 0) {
        for (var i = 0; i < items.length; i++){
            if (items[i].checked == true){
                return true;
            }
        }
    } else {
        if (items.checked == true) {
            return true;
        }
    }
    return false;
}

function setAllCheckboxState(name,state) {
	var elms = document.getElementsByName(name);
	for(var i = 0; i < elms.length; i++) {
		elms[i].checked = state;
	}
}

function getReferenceForm(elm) {
	while(elm && elm.tagName != 'BODY') {
		if(elm.tagName == 'FORM') return elm;
		elm = elm.parentNode;
	}
	return null;
}

function openDialogById(url,dialogId,iframeId){
			var horizontalPadding = 30;
		    var verticalPadding = 30;
		    document.getElementById(""+iframeId).src=url;
		   	$("#"+dialogId).dialog( "open" );
    
}

/**
 *限制字符显示个数
 */
function limitn(parameters, n) {
	if (parameters) {
		if (parameters.length > n) {
			document.write("<a title=\"" + parameters + "\" style=\"text-decoration: none\">" + parameters.substr(0, n) + "...</a>");
		} else {
			document.write(parameters);
		}
	}
}
//-->
</script>