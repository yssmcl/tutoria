/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unioeste.geral.servlet;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import unioeste.geral.bo.Aluno;
import unioeste.geral.bo.Curso;
import unioeste.geral.manager.AlunoManager;
import unioeste.geral.manager.CursoManager;
import com.google.gson.*;
import java.io.FileWriter;
import java.util.stream.Collectors;

@WebServlet(name = "GraficosServlet", urlPatterns = {"/GraficosServlet"})
public class GraficosServlet extends HttpServlet{

    
    private AlunoManager aluno = new AlunoManager();
    private CursoManager cursoMana = new CursoManager();
    
  
    
    public void java2Json(String titulo, String objeto) throws IOException{
        
        FileWriter writer = new FileWriter("E:/faculdade/4 ano/"+titulo+".json");
        writer.write(objeto);
        writer.close();        
    }
    
    
    public  List<String> carregaDadosNomeCurso(){        
    List<String> nomesDosCursos = new ArrayList<>();

    List<Curso> listaCursos = new CursoManager().recuperarCursosPorAtributo("nome","%");
    for(int i=0; i<listaCursos.size(); i++){
        nomesDosCursos.add(listaCursos.get(i).getNome());
    }

        return nomesDosCursos;
    }
   
    public Long carregaDadosSituacaoAnoAtual(Object ano, Object situacao, Object curso){        
                 
        Multimap<String, Object> condicaoAND = HashMultimap.create();
		
		condicaoAND.put("curso", curso);
		condicaoAND.put("situacaoAtual" , situacao);
		condicaoAND.put("anoAtual" , ano);
                
         return  new AlunoManager().recuperarQtdAlunosPorAtributos(condicaoAND, null);
        
    }
    
     public long carregaDadosAnoAtual(Object ano, Object curso){                         
        
        Multimap<String, Object> condicaoAND = HashMultimap.create();
            
            condicaoAND.put("curso", curso);		
            condicaoAND.put("anoAtual" , ano);
            
        return  new AlunoManager().recuperarQtdAlunosPorAtributos(condicaoAND, null);
        
    }
     
    public Long carregaDadosSituacaoAtual( Object situacao, Object curso){        
        Multimap<String, Object> condicaoAND = HashMultimap.create();
        Multimap<String, Object> condicaoOR = HashMultimap.create();
        
            condicaoAND.put("curso", curso);
            
            if(situacao.equals("AllSituacao")){
                condicaoOR.put("situacaoAtual" , "Cancelado");               
                condicaoOR.put("situacaoAtual", "Cancelado Por Abandono");		
		condicaoOR.put("situacaoAtual", "Transferido");
            }else condicaoAND.put("situacaoAtual" , situacao);
           
        return  new AlunoManager().recuperarQtdAlunosPorAtributos(condicaoAND, condicaoOR);
    }

    
    public long carregaDados( Multimap<String, Object> condicaoAND, Multimap<String, Object> condicaoOR){ // achei que fica mais legivel, provavelmente não
            return new AlunoManager().recuperarQtdAlunosPorAtributos(condicaoAND, condicaoOR);
    }
    
    public  List<String> carregaPeriodos( ){
        List<String> filtrosEixoX = new ArrayList<>();
            filtrosEixoX.add("1"); // carrega dados se curso selecionado para x
            filtrosEixoX.add("2");
            filtrosEixoX.add("3");
            filtrosEixoX.add("4");                
            filtrosEixoX.add("5");
        return filtrosEixoX; 
    } // TODO deixar Dinamico
    
    public List<String> carregaSituacao(){
        List<String> filtrosEixoX = new ArrayList<>();
            filtrosEixoX.add("Cursando"); // carrega dados se curso selecionado para x
            filtrosEixoX.add("Formado");
            filtrosEixoX.add("Cancelado por Abandono");
            filtrosEixoX.add("Cancelado");                
            filtrosEixoX.add("Transferido");
        return filtrosEixoX;
    } // TODO deixar Dinamico
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                request.setCharacterEncoding("UTF-8"); // pega caracteres UTF-8 do JSP
        try{    

            CursoManager cursoMana = new CursoManager();
            List<String> cursos = new ArrayList<>();
            List<String> valoresBase = new ArrayList<>();
            List<Long> valoresEixoX = new ArrayList<>();
            
            
            List<String> periodos = carregaPeriodos();
            List<String> situacoes = carregaSituacao();
            
            String tipoFiltroBase = request.getParameter("TipoFiltroX");
            String tipoFiltroX = request.getParameter("TipoFiltroY");
            String tipoFiltroY = request.getParameter("TipoFiltroAuxiliar");
            
            String filtroX = request.getParameter("FiltroX");
            String filtrosEixoY = request.getParameter("FiltroY");  
            String filtroAuxiliar = request.getParameter("auxiliaresSelecionado");

            System.out.println("unioeste.geral.servlet.GraficosServlet.doPost()");
            System.out.println("TipoFiltroX: "+tipoFiltroBase);
            System.out.println("TipoFiltroY: "+tipoFiltroX);
            System.out.println("TipoFiltroAuxiliar: "+tipoFiltroY);
            
            System.out.println("FiltroX: "+filtroX);
            System.out.println("FiltroY: "+filtrosEixoY);
            System.out.println("auxiliaresSelecionado: "+filtroAuxiliar);
             // data.add();
              
            Multimap<String, Object> condicaoAND = HashMultimap.create();;
            Multimap<String, Object> condicaoOR = HashMultimap.create();;
        
            System.out.println("Antes do if");
            
            if(filtroX.equals("AllCursos")) valoresBase = carregaDadosNomeCurso().stream().collect(Collectors.toList());//;
            else if(filtroX.equals("AllAnos")) valoresBase = periodos.stream().collect(Collectors.toList());
            else if(filtroX.equals("AllSituacao")) valoresBase = situacoes.stream().collect(Collectors.toList());
            else if(filtroX.equals("Todos os Abandonos e transferidos")){
                    valoresBase.add("Cancelado");
                    valoresBase.add("Cancelado Por Abandono");
                    valoresBase.add("Transferido");
            }
            if(valoresBase.isEmpty())
                valoresBase.add(filtroX);
  
            if(valoresBase.size()==1){
                if(tipoFiltroBase.equals("curso")) {
                    Curso curso = new CursoManager().recuperarCursosPorAtributo("nome", filtroX).get(0);
                    condicaoAND.put("curso",curso);
                }
                else condicaoAND.put(tipoFiltroBase,filtroX);  // tipoFiltroBase tem o nome da tabela e valroesDoEixoX o nome do atributo ex.: Curso, Matemática 
                 if(filtrosEixoY.equals("Todos os Abandonos e transferidos")){
                    condicaoOR.put(tipoFiltroX, "Cancelado");
                    condicaoOR.put(tipoFiltroX, "Cancelado Por Abandono");
                    condicaoOR.put(tipoFiltroX, "Transferido");
                }else condicaoAND.put(tipoFiltroX, filtrosEixoY);
                    
                if(tipoFiltroY.equals("Todos os Abandonos e transferidos")){
                    condicaoOR.put(tipoFiltroY, "Cancelado");
                    condicaoOR.put(tipoFiltroY, "Cancelado Por Abandono");
                    condicaoOR.put(tipoFiltroY, "Transferido");
                }else condicaoAND.put(tipoFiltroY, filtroAuxiliar);
                valoresEixoX.add(carregaDados(condicaoAND,condicaoOR));  
            } else {
                if(filtrosEixoY.equals("AllAnos") || filtrosEixoY.equals("AllCursos") || filtrosEixoY.equals("AllSituacao") )
                filtrosEixoY="%%";
                 
                if(filtroAuxiliar.equals("AllAnos") || filtroAuxiliar.equals("AllCursos") || filtroAuxiliar.equals("AllSituacao") )
                    filtroAuxiliar="%%";
                
                if(filtrosEixoY.equals("Todos os Abandonos e transferidos")){
                   condicaoOR.put(tipoFiltroX, "Cancelado");
                   condicaoOR.put(tipoFiltroX, "Cancelado Por Abandono");
                   condicaoOR.put(tipoFiltroX, "Transferido");
               }else condicaoAND.put(tipoFiltroX, filtrosEixoY);

               if(tipoFiltroY.equals("Todos os Abandonos e transferidos")){
                   condicaoOR.put(tipoFiltroY, "Cancelado");
                   condicaoOR.put(tipoFiltroY, "Cancelado Por Abandono");
                   condicaoOR.put(tipoFiltroY, "Transferido");
               }else condicaoAND.put(tipoFiltroY, filtroAuxiliar);
                for(int i=0; i<valoresBase.size();i++){                    
                    if(tipoFiltroBase.equals("curso")) {
                        Curso curso = new CursoManager().recuperarCursosPorAtributo("nome", valoresBase.get(i)).get(0);
                        condicaoAND.put("curso",curso);
                        System.out.println(curso.getNome());
                    } else condicaoAND.put(tipoFiltroBase, valoresBase.get(i));
                    valoresEixoX.add(carregaDados(condicaoAND,condicaoOR));  
                }
            }
                // =============== criação do json:          
            
            JsonParser jsonParser = new JsonParser();
            Object objeto= new Object();
            String dados=new String();          
            
            if(valoresBase.size()==1){
                dados = dados.concat("{"+tipoFiltroBase.toString()+":["+valoresBase.get(0).toString()+"],");
            } else{
                dados = dados.concat("{"+tipoFiltroBase);
                dados = dados.concat(":[\""+valoresBase.get(0).toString()+"\"");
                for(int i=1; i<valoresBase.size(); i++){
                    dados = dados.concat(",\""+valoresBase.get(i).toString()+"\"");
                } 
                dados = dados.concat("],");
            }
           
            if(valoresEixoX.size()==1){
                dados = dados.concat(tipoFiltroX+":[\""+valoresEixoX.get(0)+"\"],");
            }else{
                dados = dados.concat(tipoFiltroBase.toString());
                dados = dados.concat(":[\""+valoresEixoX.get(0)+"\"");
                for(int i=1; i<valoresBase.size(); i++){
                    dados = dados.concat(",\""+valoresEixoX.get(i)+"\"");
                } 
                dados = dados.concat("],");
            }
            dados = dados.concat(tipoFiltroY+":[\""+filtroAuxiliar+"\"]}");
            
            
            System.out.println("dados: "+dados);
            
            //objeto = jsonParser.parse(dados);
           
            java2Json(filtroX,dados);
            request.getRequestDispatcher("graficos_dinamicos.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace(); // faz sentido isso aqui?
            System.out.println(e.getMessage());
        }
        
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
