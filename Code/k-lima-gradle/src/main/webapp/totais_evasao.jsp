<%-- 
    Document   : totais_evasao
    Created on : 30/07/2017, 13:52:36
    Author     : Leandro Ensina
--%>


<%@page import="com.google.common.collect.HashMultimap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="unioeste.geral.bo.Aluno"%>
<%@page import="unioeste.geral.manager.AlunoManager"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html class="no-js" lang="en" dir="ltr">
<%
	String caminho = request.getContextPath();
%>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tutoria</title>


    <link rel="stylesheet" type="text/css" href="<%=caminho%>/css/datatables.min.css"/>
    <script type="text/javascript" src="<%=caminho%>/js/datatables.min.js"></script>
    <script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
            $('#example').dataTable();
	} );
    </script>

    <link rel="stylesheet" href="<%=caminho%>/css/foundation.css">
    <link rel="stylesheet" href="<%=caminho%>/css/app.css">
  </head>
  <body>

    <div class="off-canvas-wrapper">
      <div class="off-canvas-wrapper-inner" data-off-canvas-wrapper> <!-- Essa div e a de cima servem para fazer a parte do menu lateral -->

        <jsp:include page="#{caminho}/includes/menu.jsp"/>

          <!-- MAIN SECTION -->

          <div class="row centralizado">
            <div class="medium-12 columns" style="text-align: center;">
                <h1>Histórico de Evasão</h1>
            </div>
          </div>
          
          <hr />

          <div class="row">
            <table id="example" class="display">
              <thead>
                  <tr>
                      <th>Ano</th>
                      <th>Cancelado</th>
                      <th>Cancelado por Abandono</th>
                      <th>Transferido</th>
                      <th>Total</th>
                  </tr>
              </thead>
              <tbody>
                    <%
                    AlunoManager manager = new AlunoManager();
                    HashMultimap<String, Object> condicaoAbandono;
                    HashMultimap<String, Object> condicaoCancelamento;
                    HashMultimap<String, Object> condicaoTransferido;
                    Integer ano = 2003;
                    Long qtdAbandono;
                    Long qtdCancelado;
                    Long qtdTransferido;
                    Long qtdAbandonoTotal = 0L;
                    Long qtdCanceladoTotal = 0L;
                    Long qtdTransferidoTotal = 0L;
                    Long total;

                    while(ano < 2017){
                        condicaoAbandono = HashMultimap.create();
                        condicaoAbandono.put("anoSaida", Integer.toString(ano));
                        condicaoAbandono.put("situacaoAtual", "Cancelado Por Abandono");
                        
                        condicaoCancelamento = HashMultimap.create();
                        condicaoCancelamento.put("anoSaida", Integer.toString(ano));
                        condicaoCancelamento.put("situacaoAtual", "Cancelado");
                        
                        condicaoTransferido = HashMultimap.create();
                        condicaoTransferido.put("anoSaida", Integer.toString(ano));
                        condicaoTransferido.put("situacaoAtual", "Transferido");
                        
                        qtdAbandono = manager.recuperarQtdAlunosPorAtributos(condicaoAbandono, null);
                        qtdCancelado = manager.recuperarQtdAlunosPorAtributos(condicaoCancelamento, null);
                        qtdTransferido = manager.recuperarQtdAlunosPorAtributos(condicaoTransferido, null);
                        
                        qtdAbandonoTotal += qtdAbandono;
                        qtdCanceladoTotal += qtdCancelado;
                        qtdTransferidoTotal += qtdTransferido;
                        
                    %>
                        <tr>
                            <td><%= ano %></td>
                            <td><%= qtdCancelado %></td>
                            <td><%= qtdAbandono %></td>
                            <td><%= qtdTransferido %></td>
                            <td><%= qtdCancelado + qtdAbandono + qtdTransferido %></td>
                        </tr>
                    <%
                        ano++;
                    }
                    
                    total = qtdCanceladoTotal + qtdAbandonoTotal + qtdTransferidoTotal;
                        
                    %>
                     <tr>
                        <td><b>Total</b></td>
                        <td><b><%= qtdCanceladoTotal %></b></td>
                        <td><b><%= qtdAbandonoTotal %></b></td>
                        <td><b><%= qtdTransferidoTotal %></b></td>
                        <td><b><%= total %></b></td>
                     </tr>
                     <tr>
                        <td><b>Total em %</b></td>
                        <td><b><%= (100*qtdCanceladoTotal)/total %>%</b></td>
                        <td><b><%= (100*qtdAbandonoTotal)/total %>%</b></td>
                        <td><b><%= (100*qtdTransferidoTotal)/total %>%</b></td>
                        <td>--</td>
                     </tr>
                </tbody>
            </table>
          </div>          
        </div>
      </div>
    </div>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>

    <!--
    <script src="<%=caminho%>/js/vendor/jquery.js"></script>
    -->
    <script src="<%=caminho%>/js/vendor/what-input.js"></script>
    <script src="<%=caminho%>/js/vendor/foundation.js"></script>
    <script src="<%=caminho%>/js/app.js"></script>
    <script type="text/javascript">
	// For demo to fit into DataTables site builder...
	$('#example')
		.removeClass( 'display' )
		.addClass('tdisplay');
    </script>
  </body>
</html>
