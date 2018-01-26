<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#companiesForSelect").dblclick(function() {
		companiesSelected();
	});
	$("#selectCompanies").dblclick(function() {
		companiesUnselected();
	});
});
function companiesSelected() {
	var selOpt = $("#companiesForSelect option:companiesSelected");

	selOpt.remove();
	var selObj = $("#selectCompanies");
	selObj.append(selOpt);

	var selOpt = $("#selectCompanies")[0];
	ids = "";
	for (var i = 0; i < selOpt.length; i++) {
		ids += (selOpt[i].value  + ",");
	}

	if (ids != "") {
		ids = ids.substring(0, ids.length - 1);
	}
	$('#txtCompaniesSelect').val(ids);
}

function selectedAll() {
	var selOpt = $("#companiesForSelect option");

	selOpt.remove();
	var selObj = $("#selectCompanies");
	selObj.append(selOpt);

	var selOpt = $("#selectCompanies")[0];
	ids = "";
	for (var i = 0; i < selOpt.length; i++) {
		ids += (selOpt[i].value  + ",");
	}

	if (ids != "") {
		ids = ids.substring(0, ids.length - 1);
	}
	$('#txtCompaniesSelect').val(ids);
}

function companiesUnselected() {
	var selOpt = $("#selectCompanies option:companiesSelected");
	selOpt.remove();
	var selObj = $("#companiesForSelect");
	selObj.append(selOpt);

	var selOpt = $("#selectCompanies")[0];
	ids = "";
	for (var i = 0; i < selOpt.length; i++) {
		ids += (selOpt[i].value + ",");
	}
	
	if (ids != "") {
		ids = ids.substring(0, ids.length - 1);
	}
	$('#txtCompaniesSelect').val(ids);
}

function unselectedAll() {
	var selOpt = $("#selectCompanies option");
	selOpt.remove();
	var selObj = $("#companiesForSelect");
	selObj.append(selOpt);

	$('#txtCompaniesSelect').val("");
}
</script>
<div class="form-group">
<input id="txtCompaniesSelect" type="hidden" value="${txtCompaniesSelect}"
			name="txtCompaniesSelect" />
	<label for="host" class="col-sm-3 control-label">公司</label>
	<div class="col-sm-9">
		<table class="tweenBoxTable" name="companies_tweenbox"
			id="companies_tweenbox" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<td>已选公司</td>
					<td></td>
					<td>可选公司</td>
				</tr>
				<tr>
					<td><select id="selectCompanies" multiple="multiple"
						class="input-large" name="selectCompanies"
						style="height: 150px; width: 150px">
						<c:forEach items="${userCompany}" var="key">
						<option value="${key.id}">${key.name}</option>
						</c:forEach>
					</select></td>
					<td align="center">
						<div style="margin-left: 5px; margin-right: 5px">
							<button onclick="selectedAll()" class="btn btn-primary"
								type="button" style="width: 50px;" title="全选">&lt;&lt;</button>
						</div>
						<div style="margin-left: 5px; margin-right: 5px; margin-top: 5px;">
							<button onclick="companiesSelected()" class="btn btn-primary"
								type="button" style="width: 50px;" title="选择">&lt;</button>
						</div>
						<div style="margin-left: 5px; margin-right: 5px; margin-top: 5px;">
							<button onclick="companiesUnselected()" class="btn btn-primary"
								type="button" style="width: 50px;" title="取消">&gt;</button>
						</div>
						<div style="margin-left: 5px; margin-right: 5px; margin-top: 5px">
							<button onclick="unselectedAll()" class="btn btn-primary"
								type="button" style="width: 50px;" title="全取消">&gt;&gt;</button>
						</div>
					</td>
					<td><select id="companiesForSelect"
						multiple="multiple" class="input-large"
						style="height: 150px; width: 150px">
						<c:forEach items="${company}" var="key">
						<option value="${key.id}">${key.name}</option>
						</c:forEach>
					</select></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>