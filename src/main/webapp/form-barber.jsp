
<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp"%>
    <title>CRUD Manager - Barbeiros</title>
</head>
<body>
    <%@include file="nav-menu.jsp"%>

    <div id="container" class="container-fluid">

        <h3 class="page-header">
            ${not empty barber ? 'Atualizar' : 'Adicionar'} Barbeiro
        </h3>

        <form action="${pageContext.request.contextPath}/barber/${action}"
              method="POST">

            <input type="hidden"
                   name="barberId"
                   value="${barber.getId()}" />

            <!-- Nome e telefone -->
            <div class="row">

                <div class="form-group col-md-6">
                    <label for="name">Nome</label>

                    <input type="text"
                           class="form-control"
                           id="name"
                           name="name"
                           placeholder="Nome do barbeiro"
                           value="${barber.getName()}"
                           maxlength="150"
                           required
                           autofocus="autofocus" />
                </div>

                <div class="form-group col-md-6">
                    <label for="phone">Telefone</label>

                    <input type="tel"
                           class="form-control"
                           id="phone"
                           name="phone"
                           placeholder="(35) 99999-9999"
                           value="${barber.getPhone()}"
                           maxlength="20"
                           required />
                </div>

            </div>

            <!-- Especialidade e usuário -->
            <div class="row">

                <div class="form-group col-md-6">
                    <label for="specialty">Especialidade</label>

                    <input type="text"
                           class="form-control"
                           id="specialty"
                           name="specialty"
                           placeholder="Ex: Corte masculino"
                           value="${barber.getSpecialty()}"
                           maxlength="100"
                           required />
                </div>

                <div class="form-group col-md-6">
                    <label for="user">Usuário</label>

                    <select id="user"
                            class="form-control selectpicker"
                            name="user"
                            required>

                        <option value="">
                            Selecione um usuário
                        </option>

                        <c:forEach var="user" items="${users}">

                            <option value="${user.getId()}"
                                ${not empty barber &&
                                  barber.getUser().getId() == user.getId()
                                  ? 'selected' : ''}>

                                ${user.getName()}

                            </option>

                        </c:forEach>

                    </select>
                </div>

            </div>

            <!-- Horários de trabalho -->
            <div class="row">

                <div class="form-group col-md-4">
                    <label for="startTime">Início do expediente</label>

                    <input type="time"
                           class="form-control"
                           id="startTime"
                           name="startTime"
                           value="${fn:substring(barber.getStartTime(), 0, 5)}"
                           required />
                </div>

                <div class="form-group col-md-4">
                    <label for="endTime">Fim do expediente</label>

                    <input type="time"
                           class="form-control"
                           id="endTime"
                           name="endTime"
                           value="${fn:substring(barber.getEndTime(), 0, 5)}"
                           required />
                </div>

                <div class="form-group col-md-4">
                    <label>Situação</label>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox"
                                   id="active"
                                   name="active"
                                   value="true"
                                   ${empty barber || barber.isActive()
                                     ? 'checked' : ''} />

                            Barbeiro ativo
                        </label>
                    </div>
                </div>

            </div>

            <hr />

            <!-- Botões -->
            <div id="actions" class="row pull-right">
                <div class="col-md-12">

                    <a href="${pageContext.request.contextPath}/barbers"
                       class="btn btn-default">
                        Cancelar
                    </a>

                    <button type="submit" class="btn btn-primary">

                        ${not empty barber
                          ? 'Atualizar' : 'Cadastrar'} Barbeiro

                    </button>

                </div>
            </div>

        </form>

    </div>
</body>
</html>
