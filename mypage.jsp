<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="./css/spring.css">
	<link rel="stylesheet" href="./css/page-title.css">
	<link rel="stylesheet" href="./css/message.css">
	<link rel="stylesheet" href="./css/header.css">
	<link rel="stylesheet" href="./css/submit-btn.css">
	<link rel="stylesheet" href="./css/horizontal-list-table.css">
	<title>マイページ</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div id="contents">
	<h1>マイページ画面</h1>
	<s:if test="userInfoDTO!=null">
		<table>
			<tr>
				<th><s:label value="姓"/></th>
				<td><s:property value="UserInfoDTO.familyName"/></td>
			</tr>

			<tr>
				<th><s:label value="名"/></th>
				<td><s:property value="UserInfoDTO.firstName"/></td>
			</tr>

			<tr>
				<th><s:label value="ふりがな"/></th>
				<td><s:property value="UserInfoDTO.familyNameKana"/>&emsp;<s:property value="UserInfoDTO.firstNameKana"/></td>
			</tr>

			<tr>
				<th><s:label value="性別"/></th>
				<td><s:if test="UserInfoDTO.sex==0">男性</s:if><s:else>女性</s:else></td>
			</tr>

			<tr>
				<th><s:label value="メールアドレス"/></th>
				<td><s:property value="UserInfoDTO.email"/></td>
			</tr>
		</table>
		<s:form action="PurchaseHistoryAction">
			<div class="submit-btn-box">
				<s:submit class="submit-btn" value="購入履歴"/>
			</div>
		</s:form>
		<s:form action="UnsubscribeAction">
			<div class="submit-btn-box">
				<s:submit class="submit-btn" value="退会する"/>
			</div>
		</s:form>
	</s:if>
	<s:else>
		<div class="info">
			ユーザー情報がありません。
		</div>
	</s:else>
	</div>
</body>
</html>