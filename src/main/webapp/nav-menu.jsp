
<jsp:directive.page contentType="text/html; charset=UTF-8" />

<nav id="menu" class="navbar navbar-inverse navbar-fixed-top">

    <div class="container-fluid">

        <!-- Nome e botão do menu mobile -->
        <div class="navbar-header">

            <button type="button"
                    class="navbar-toggle collapsed"
                    data-toggle="collapse"
                    data-target="#navbar"
                    aria-expanded="false"
                    aria-controls="navbar">

                <span class="sr-only">Abrir menu</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>

            </button>

            <a class="navbar-brand"
               href="${pageContext.request.contextPath}/posts">

                <span class="glyphicon glyphicon-scissors"></span>
                <strong>&nbsp;Barbearia Manager</strong>

            </a>

        </div>

        <!-- Itens do menu -->
        <div id="navbar" class="navbar-collapse collapse">

            <ul class="nav navbar-nav navbar-right">

                <!-- Início -->
                <li>
                    <a href="${pageContext.request.contextPath}/posts">
                        <span class="glyphicon glyphicon-home"></span>
                        &nbsp;Início
                    </a>
                </li>

                <!-- Clientes -->
                <li>
                    <a href="${pageContext.request.contextPath}/users">
                        <span class="glyphicon glyphicon-user"></span>
                        &nbsp;Clientes
                    </a>
                </li>

                <!-- Barbeiros -->
                <li>
                    <a href="${pageContext.request.contextPath}/barbers">
                        <span class="glyphicon glyphicon-scissors"></span>
                        &nbsp;Barbeiros
                    </a>
                </li>

                <!-- Agendamentos -->
                <li>
                    <a href="${pageContext.request.contextPath}/appointments">
                        <span class="glyphicon glyphicon-calendar"></span>
                        &nbsp;Agendamentos
                    </a>
                </li>

                <!-- Cadastros originais do professor -->
                <li class="dropdown">

                    <a href="#"
                       class="dropdown-toggle"
                       data-toggle="dropdown"
                       role="button"
                       aria-haspopup="true"
                       aria-expanded="false">

                        <span class="glyphicon glyphicon-folder-open"></span>
                        &nbsp;Outros
                        <span class="caret"></span>

                    </a>

                    <ul class="dropdown-menu">

                        <li>
                            <a href="${pageContext.request.contextPath}/posts">
                                <span class="glyphicon glyphicon-pencil"></span>
                                &nbsp;Posts
                            </a>
                        </li>

                        <li>
                            <a href="${pageContext.request.contextPath}/companies">
                                <span class="glyphicon glyphicon-briefcase"></span>
                                &nbsp;Empresas
                            </a>
                        </li>

                    </ul>

                </li>

                <!-- Perfil -->
                <li class="dropdown">

                    <a href="#"
                       class="dropdown-toggle"
                       data-toggle="dropdown"
                       role="button"
                       aria-haspopup="true"
                       aria-expanded="false">

                        <span class="glyphicon glyphicon-user"></span>
                        &nbsp;Perfil
                        <span class="caret"></span>

                    </a>

                    <ul class="dropdown-menu">

                        <!-- Identificação do usuário -->
                        <li class="dropdown-header">
                            ${sessionScope.loggedUser}
                        </li>

                        <li class="divider"></li>

                        <!-- Logout -->
                        <li>
                            <form action="${pageContext.request.contextPath}/logout"
                                  method="POST"
                                  style="margin: 0;">

                                <button type="submit"
                                        class="btn btn-link"
                                        style="width: 100%;
                                               text-align: left;
                                               color: #333;
                                               padding: 10px 20px;
                                               text-decoration: none;">

                                    <span class="glyphicon glyphicon-log-out"></span>
                                    &nbsp;Sair

                                </button>

                            </form>
                        </li>

                    </ul>

                </li>

            </ul>

        </div>

    </div>

</nav>

<br />
<br />
<br />
