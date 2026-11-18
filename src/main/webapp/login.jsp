
<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp"%>
    <title>CRUD Manager - Login</title>
</head>

<body>

    <div class="container" style="margin-top: 100px;">

        <div class="row">

            <div class="col-md-4 col-md-offset-4">

                <div class="panel panel-default">

                    
<div class="panel-heading text-center"
     style="background-color: #292929 !important;
            padding: 20px;">

    <h3 style="color: #ffffff !important;
               font-weight: bold;
               margin: 0;">
        Login - Barbearia
    </h3>

</div>


                    <div class="panel-body">

                        <c:if test="${not empty message}">
                            <div class="alert alert-danger">
                                <c:out value="${message}" />
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/login"
                              method="POST">

                            <div class="form-group">

                                <label for="username">
                                    Usuário
                                </label>

                                <input type="text"
                                       class="form-control"
                                       id="username"
                                       name="username"
                                       placeholder="Digite seu usuário"
                                       maxlength="100"
                                       autocomplete="username"
                                       required
                                       autofocus />

                            </div>

                            <div class="form-group">

                                <label for="password">
                                    Senha
                                </label>

                                <input type="password"
                                       class="form-control"
                                       id="password"
                                       name="password"
                                       placeholder="Digite sua senha"
                                       autocomplete="current-password"
                                       required />

                            </div>

                            <button type="submit"
                                    class="btn btn-primary btn-block">

                                Entrar

                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>

    </div>

</body>
</html>
