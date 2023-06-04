package ntukhpi.semit.dde.studentsdata.servlets.emails;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.utils.ContactMessages;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_email")
public class EmailRemoveServlet extends HttpServlet {

    Email myEmail = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EmailRemoveServlet#doGet");
        Long id_owner = Long.parseLong(request.getParameter("id_owner"));
        Person owner = DAOObjects.daoPerson.findById(id_owner);
        System.out.println(owner.getClass() + ": " + owner);
        Long id_email = Long.parseLong(request.getParameter("id_email"));
        Email emailToDel = DAOObjects.daoEmail.findById(id_email);
        myEmail = emailToDel;
        request.setAttribute("error", null);
        System.out.println("emailToDel:" + emailToDel);
        request.setAttribute("email", emailToDel);
        request.setAttribute("owner", owner);
        String path = "/views/emails/email_delete.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("EmailRemoveServlet#doPost");
        String idEmailStr = request.getParameter("id_email");
        Long idEmailDel = Long.parseLong(idEmailStr);
        String idOwnerStr = request.getParameter("id_owner");
        Long idOwner = Long.parseLong(idOwnerStr);
        //Call Update
        boolean deleteRes = DAOObjects.daoEmail.delete(idEmailDel);
        if (deleteRes) {
            //back to list of person's emails
            System.out.println("Email вилучений!!!");
            String path = request.getContextPath() + "/emails?id_owner="+idOwner+ "&msgcode=12";
            response.sendRedirect(path);
        } else {
            System.out.println("Помилка вилучення! Delete SQL mistake!!!");
            request.setAttribute("error", ContactMessages.MESSAGE13.getText());
            request.setAttribute("email", myEmail);
            request.setAttribute("owner", idEmailStr); //!!!!
            String path = "/views/emails/email_delete.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }
    }
}
