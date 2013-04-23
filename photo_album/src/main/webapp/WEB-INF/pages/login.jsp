<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Photo album</title>
		<style>
			.errorblock {
				color: #ff0000;
				background-color: #ffEEEE;
				border: 3px solid #ff0000;
				padding: 8px;
				margin: 16px;
			}
		</style>
	</head>
	<body>
		<h3>Login to Photo Album</h3>
		<c:if test="${not empty error}">
			<div class="errorblock">
				Your login attempt was not successful, try again.<br /> Caused :
				${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
			</div>
		</c:if>
		<div class="container">
			<div class="content">
				<form action="<c:url value='/j_spring_security_check'/>" method="post">
					<fieldset>
						<table>
							<tr>
								<td align="left"><label for="j_username">Username:</label></td>
								<td><input type="text" name="j_username" id="j_username" /></td>
							</tr>
							<tr>
								<td align="left"><label for="j_password">Password:</label></td>
								<td><input type="password" name="j_password" id="j_password" /></td>
							</tr>
							<tr>
								<td><input type="submit" value="Submit"></td>
								<td><input type="reset" value="Cancel" /></td>
							</tr>
						</table>
					</fieldset>
				</form>
			</div>
		</div>
	</body>
</html>