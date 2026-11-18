
<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <%@include file="base-head.jsp"%>
    <title>CRUD Manager - Usuários</title>
</head>

<body>

    <%@include file="modal.html"%>
    <%@include file="nav-menu.jsp"%>

    <div id="container" class="container-fluid">

        <!-- Mensagens -->
        <div id="alert"
             style="${not empty message ? 'display: block;' : 'display: none;'}"
             class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">

            <a href="#" class="close" data-dismiss="alert"
               aria-label="close">&times;</a>

            <c:out value="${message}" />
        </div>

        <!-- Cabeçalho -->
        <div id="top" class="row">

            <div class="col-md-3">
                <h3>Usuários</h3>
            </div>

            <div class="col-md-6">
                <div class="input-group h2">

                    <input name="data[search]"
                           class="form-control"
                           id="search"
                           type="text"
                           placeholder="Pesquisar usuários">

                    <span class="input-group-btn">
                        <button class="btn btn-danger" type="button">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>

                </div>
            </div>

            <div class="col-md-3">

                <a href="${pageContext.request.contextPath}/user/form"
                   class="btn btn-danger pull-right h2">

                    <span class="glyphicon glyphicon-plus"></span>
                    &nbsp;Adicionar Usuário
                </a>

            </div>

        </div>

        <hr />

        <!-- Tabela de usuários -->
        <div id="list" class="row">

            <div class="table-responsive col-md-12">

                <table class="table table-striped table-hover"
                       cellspacing="0" cellpadding="0">

                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Sexo</th>
                            <th>E-mail</th>
                            <th>Editar</th>
                            <th>Excluir</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach var="user" items="${users}">

                            <tr>

                                <td>
                                    <c:out value="${user.name}" />
                                </td>

                                <td>
                                    <c:out value="${user.gender}" />
                                </td>

                                <td>
                                    <c:out value="${user.email}" />
                                </td>

                                <!-- Editar -->
                                <td class="actions">

                                    <a class="btn btn-info btn-xs"
                                       href="${pageContext.request.contextPath}/user/update?userId=${user.id}">

                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>

                                </td>

                                <!-- Excluir -->
                                <td class="actions">

                                    <a class="btn btn-danger btn-xs modal-remove"
                                       user-id="${user.id}"
                                       user-name="${user.name}"
                                       data-toggle="modal"
                                       data-target="#delete-modal"
                                       href="#">

                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>

                                </td>

                            </tr>

                        </c:forEach>

                    </tbody>
                </table>

            </div>
        </div>

        <!-- Paginação funcional -->
        <div id="bottom" class="row">

            <div class="col-md-12">

                <c:if test="${totalPages > 1}">

                    <ul class="pagination">

                        <!-- Página anterior -->
                        <li class="${currentPage == 1 ? 'disabled' : ''}">

                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <a href="${pageContext.request.contextPath}/users?page=${currentPage - 1}">
                                        &lt; Anterior
                                    </a>
                                </c:when>

                                <c:otherwise>
                                    <a>&lt; Anterior</a>
                                </c:otherwise>
                            </c:choose>

                        </li>

                        <!-- Números das páginas -->
                        <c:forEach var="pageNumber"
                                   begin="1"
                                   end="${totalPages}">

                            <li class="${pageNumber == currentPage ? 'active' : ''}">

                                <a href="${pageContext.request.contextPath}/users?page=${pageNumber}">
                                    ${pageNumber}
                                </a>

                            </li>

                        </c:forEach>

                        <!-- Próxima página -->
                        <li class="${currentPage == totalPages ? 'disabled' : ''}">

                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <a href="${pageContext.request.contextPath}/users?page=${currentPage + 1}">
                                        Próximo &gt;
                                    </a>
                                </c:when>

                                <c:otherwise>
                                    <a>Próximo &gt;</a>
                                </c:otherwise>
                            </c:choose>

                        </li>

                    </ul>

                </c:if>

            </div>
        </div>

    </div>

    <!-- Scripts originais -->
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <script type="text/javascript">

        $(document).ready(function() {

            // Fecha o alerta após 3 segundos
            setTimeout(function() {
                $("#alert").slideUp(500);
            }, 3000);

            // Configura o modal de exclusão
            $(".modal-remove").click(function() {

                var userName = $(this).attr("user-name");
                var userId = $(this).attr("user-id");

                $(".modal-body #hiddenValue").text(
                    "o usuário '" + userName + "'"
                );

                $("#id").val(userId);

                $("#form").attr(
                    "action",
                    "${pageContext.request.contextPath}/user/delete"
                );

            });

        });

    </script>

</body>
</html>
