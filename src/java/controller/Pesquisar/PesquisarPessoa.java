
package controller.Pesquisar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.PessoaDAO;
import java.io.IOException;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;

/**
 *
 * @author smili08
 */
@WebServlet(name = "PesquisarPessoa", urlPatterns = {"/PesquisarPessoa"})
public class PesquisarPessoa extends HttpServlet {

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
            String cpfPessoa = request.getParameter("cpfPessoa");
            PessoaDAO pessoaDAO = new PessoaDAO();
            Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Pessoa pessoa = (Pessoa) pessoaDAO.PesquisarPessoa(cpfPessoa);
            response.getWriter().write(json.toJson(pessoa));
        } catch (SQLException | ClassNotFoundException ex) {
            response.getWriter().write("\"erro\": " + ex.getMessage());
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
