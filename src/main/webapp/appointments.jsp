
<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp"%>
    <title>CRUD Manager - Agendamentos</title>
</head>
<body>

    <%@include file="modal.html"%>
    <%@include file="nav-menu.jsp"%>

    <div id="container" class="container-fluid">

        <!-- Mensagens de sucesso ou erro -->
        <div id="alert"
             style="${not empty message ? 'display: block;' : 'display: none;'}"
             class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">

            <a href="#" class="close" data-dismiss="alert"
               aria-label="close">&times;</a>

            ${message}
        </div>

        <!-- Cabeçalho -->
        <div id="top" class="row">

            <div class="col-md-3">
                <h3>Agendamentos</h3>
            </div>

            <div class="col-md-6">
                <div class="input-group h2">

                    <input class="form-control"
                           id="search"
                           type="text"
                           placeholder="Pesquisar agendamentos">

                    <span class="input-group-btn">
                        <button class="btn btn-danger" type="button">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>

                </div>
            </div>

            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/appointment/form"
                   class="btn btn-danger pull-right h2">

                    <span class="glyphicon glyphicon-plus"></span>
                    &nbsp;Novo Agendamento
                </a>
            </div>

        </div>

        <hr />

        <!-- Listagem dos agendamentos -->
        <div id="list" class="row">

            <div class="table-responsive col-md-12">

                <table class="table table-striped table-hover"
                       cellspacing="0" cellpadding="0">

                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Cliente</th>
                            <th>Barbeiro</th>
                            <th>Serviço</th>
                            <th>Data</th>
                            <th>Horário</th>
                            <th>Situação</th>
                            <th>Observações</th>
                            <th>Editar</th>
                            <th>Excluir</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach var="appointment"
                                   items="${appointments}">

                            <tr>
                                <td>${appointment.id}</td>

                                <td>${appointment.user.name}</td>

                                <td>${appointment.barber.name}</td>

                                <td>
                                    <c:out value="${appointment.service}" />
                                </td>

                                <td>${appointment.appointmentDate}</td>

                                <td>${appointment.appointmentTime}</td>

                                <td>
                                    <c:choose>

                                        <c:when test="${appointment.status eq 'Agendado'}">
                                            <span class="label label-primary">
                                                Agendado
                                            </span>
                                        </c:when>

                                        <c:when test="${appointment.status eq 'Concluido'}">
                                            <span class="label label-success">
                                                Concluído
                                            </span>
                                        </c:when>

                                        <c:when test="${appointment.status eq 'Cancelado'}">
                                            <span class="label label-danger">
                                                Cancelado
                                            </span>
                                        </c:when>

                                        <c:otherwise>
                                            <c:out value="${appointment.status}" />
                                        </c:otherwise>

                                    </c:choose>
                                </td>

                                <td>
                                    <c:out value="${appointment.notes}" />
                                </td>

                                <!-- Editar -->
                                <td class="actions">

                                    <a class="btn btn-info btn-xs"
                                       href="${pageContext.request.contextPath}/appointment/update?appointmentId=${appointment.id}">

                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>

                                </td>

                                <!-- Excluir -->
                                <td class="actions">

                                    <a class="btn btn-danger btn-xs modal-remove"
                                       data-id="${appointment.id}"
                                       data-client="${appointment.user.name}"
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

        
<!-- Paginação de Agendamentos -->
<div id="bottom" class="row">

    <div class="col-md-12">

        <c:if test="${totalPages > 1}">

            <ul class="pagination">

                <!-- Anterior -->
                <li class="${currentPage == 1 ? 'disabled' : ''}">

                    <c:choose>

                        <c:when test="${currentPage > 1}">
                            <a href="${pageContext.request.contextPath}/appointments?page=${currentPage - 1}">
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

                        <a href="${pageContext.request.contextPath}/appointments?page=${pageNumber}">
                            ${pageNumber}
                        </a>

                    </li>

                </c:forEach>

                <!-- Próximo -->
                <li class="${currentPage == totalPages ? 'disabled' : ''}">

                    <c:choose>

                        <c:when test="${currentPage < totalPages}">
                            <a href="${pageContext.request.contextPath}/appointments?page=${currentPage + 1}">
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

    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <script type="text/javascript">

        $(document).ready(function() {

            // Oculta mensagens após três segundos
            setTimeout(function() {
                $("#alert").slideUp(500);
            }, 3000);

            // Configura a confirmação de exclusão
            $(".modal-remove").click(function() {

                var appointmentId = $(this).attr("data-id");
                var clientName = $(this).attr("data-client");

                $(".modal-body #hiddenValue").text(
                    "o agendamento do cliente '" + clientName + "'"
                );

                $("#id").val(appointmentId);

                $("#form").attr(
                    "action",
                    "${pageContext.request.contextPath}/appointment/delete"
                );

            });

        });

    </script>

</body>
</html>
