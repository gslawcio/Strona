<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<title>Ciekawostki - Rejestracja</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" rel="stylesheet">
  </head>

  <body>
    
    <jsp:include page="fragment/navbar.jspf" />
    
   <%--  <nav class = "navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <a href="#" class="navbar-brand">Ciekawostki</a>
        
        <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
          <span class="glyphicon glyphicon-list"></span>
        </button>
        
        <div class="collapse navbar-collapse navHeaderCollapse">
          <ul class="nav navbar-nav navbar-right">
            <li class="active"><a href="#">Główna</a></li>
            <li><a href="#">Dodaj</a></li>
            
            <c:choose>
            	<c:when test="${not empty sessionScope.user}">
            		<li><a href="${pageContext.request.contextPath}/logout">Wyloguj się</a></li>
            	</c:when>
            	<c:otherwise>
            		<li><a href="${pageContext.request.contextPath}/login">Zaloguj się</a></li>
            	</c:otherwise>
            </c:choose>
            
          </ul>
        </div>
      </div>
    </nav> --%>
    
    
    
     <div class="container">
		<div class="col-md-8 col-md-offset-2">
			<form class="form-signin" method="post" action="add">
				<h2 class="form-signin-heading">Dodaj nowe znalezisko</h2>
				<input name="inputName" type="text" class="form-control" placeholder="Co dodajesz?" required autofocus />
				<input name="inputUrl" type="url" class="form-control" placeholder="URL" required autofocus /> 
				<textarea name="inputDescription" rows="5" name="inputUsername"
					class="form-control" placeholder="Opis" required autofocus></textarea>
				<input class="btn btn-lg btn-primary btn-block" type="submit"  value="Dodaj!" />
			</form>
		</div>
    </div>
    
    
    <jsp:include page="fragment/footer.jspf" />
    
    
<!-- <footer class="footer">
      <div class="container">
        <p class="navbar-text">Ciekawostki - developed by Grabski Sławomir</p>
      </div>
    </footer> -->
    
    <script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="resources/js/bootstrap.js"></script>
  </body>
</html>