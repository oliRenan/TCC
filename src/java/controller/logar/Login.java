
package controller.logar;


import dao.ClienteDAO;
import dao.FuncionarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cliente;
import model.Funcionario;


/**
 *
 * @author smili08
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
           try {
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            Cliente cliente = new ClienteDAO().pesquisarCliente(login, senha);
            Funcionario funcionario = new FuncionarioDAO().pesquisarFuncionario(login, senha);
            
            if(cliente != null){
             
                HttpSession sessao = request.getSession(true);
                sessao.setAttribute("cliente", cliente);               
                request.getRequestDispatcher("homeCliente.jsp").forward(request, response);          
            }
            else if(funcionario != null){
                HttpSession sessao = request.getSession(true);               
                sessao.setAttribute("funcionario", funcionario);
                request.getRequestDispatcher("homeFuncionario.jsp").forward(request, response);
             }
            else{
                 request.setAttribute("mensagem", "Usuário ou senha invalida");
                request.getRequestDispatcher("paginaLogin.jsp").forward(request, response);
            }
           
        } catch(SQLException | ClassNotFoundException ex) {
            request.setAttribute("mensagem", ex.getMessage());
        }
    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
