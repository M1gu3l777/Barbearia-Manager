
<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="base-head.jsp"%>
    <title>CRUD Manager - Agendamentos</title>
</head>
<body>

    <%@include file="nav-menu.jsp"%>

    <div id="container" class="container-fluid">

        <h3 class="page-header">
            ${not empty appointment ? 'Atualizar' : 'Adicionar'} Agendamento
        </h3>

        <form action="${pageContext.request.contextPath}/appointment/${action}"
              method="POST">

            <input type="hidden"
                   name="appointmentId"
                   value="${appointment.id}" />

            <!-- Cliente e barbeiro -->
            <div class="row">

                <div class="form-group col-md-6">
                    <label for="user">Cliente</label>

                    <select id="user"
                            name="user"
                            class="form-control"
                            required>

                        <option value="">Selecione um cliente</option>

                        <c:forEach var="user" items="${users}">
                            <option value="${user.id}"
                                ${not empty appointment &&
                                  appointment.user.id == user.id
                                  ? 'selected' : ''}>
                                <c:out value="${user.name}" />
                            </option>
                        </c:forEach>

                    </select>
                </div>

                <div class="form-group col-md-6">
                    <label for="barber">Barbeiro</label>

                    <select id="barber"
                            name="barber"
                            class="form-control"
                            required>

                        <option value="">Selecione um barbeiro</option>

                        <c:forEach var="barber" items="${barbers}">
                            <option value="${barber.id}"
                                ${not empty appointment &&
                                  appointment.barber.id == barber.id
                                  ? 'selected' : ''}>
                                <c:out value="${barber.name}" />
                            </option>
                        </c:forEach>

                    </select>
                </div>

            </div>

            <!-- Serviço -->
            <div class="row">

                <div class="form-group col-md-12">
                    <label for="service">Serviço</label>

                    <select id="service"
                            name="service"
                            class="form-control"
                            required>

                        <option value="">Selecione um serviço</option>

                        <option value="Corte masculino"
                            ${appointment.service eq 'Corte masculino'
                              ? 'selected' : ''}>
                            Corte masculino
                        </option>

                        <option value="Barba"
                            ${appointment.service eq 'Barba'
                              ? 'selected' : ''}>
                            Barba
                        </option>

                        <option value="Corte e barba"
                            ${appointment.service eq 'Corte e barba'
                              ? 'selected' : ''}>
                            Corte e barba
                        </option>

                        <option value="Sobrancelha"
                            ${appointment.service eq 'Sobrancelha'
                              ? 'selected' : ''}>
                            Sobrancelha
                        </option>

                        <option value="Acabamento"
                            ${appointment.service eq 'Acabamento'
                              ? 'selected' : ''}>
                            Acabamento
                        </option>

                    </select>
                </div>

            </div>

            <!-- Data e horário -->
            <div class="row">

                <div class="form-group col-md-6">
                    <label for="appointmentDate">Data</label>

                    <input type="date"
                           class="form-control"
                           id="appointmentDate"
                           name="appointmentDate"
                           value="${appointment.appointmentDate}"
                           required />
                </div>

                <div class="form-group col-md-6">
                    <label for="appointmentTime">Horário</label>

                    <input type="time"
                           class="form-control"
                           id="appointmentTime"
                           name="appointmentTime"
                           value="${appointment.appointmentTime}"
                           required />
                </div>

            </div>

            <!-- Situação -->
            <div class="row">

                <div class="form-group col-md-6">
                    <label for="status">Situação</label>

                    <select id="status"
                            name="status"
                            class="form-control"
                            required>

                        <option value="Agendado"
                            ${empty appointment ||
                              appointment.status eq 'Agendado'
                              ? 'selected' : ''}>
                            Agendado
                        </option>

                        <option value="Concluido"
                            ${appointment.status eq 'Concluido'
                              ? 'selected' : ''}>
                            Concluído
                        </option>

                        <option value="Cancelado"
                            ${appointment.status eq 'Cancelado'
                              ? 'selected' : ''}>
                            Cancelado
                        </option>

                    </select>
                </div>

            </div>

            <!-- Observações -->
            <div class="row">

                <div class="form-group col-md-12">
                    <label for="notes">Observações</label>

                    <textarea id="notes"
                              name="notes"
                              class="form-control"
                              rows="4"
                              placeholder="Observações sobre o agendamento"><c:out value="${appointment.notes}" /></textarea>
                </div>

            </div>

            <hr />

            <!-- Botões -->
            <div id="actions" class="row pull-right">

                <div class="col-md-12">

                    <a href="${pageContext.request.contextPath}/appointments"
                       class="btn btn-default">
                        Cancelar
                    </a>

                    <button type="submit" class="btn btn-primary">
                        ${not empty appointment
                          ? 'Atualizar' : 'Cadastrar'} Agendamento
                    </button>

                </div>

            </div>

        </form>

    </div>

</body>
</html>
