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

@WebServlet("/add_email")
public class EmailPersonAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EmailPersonAddServlet#doGet");
        Long id_owner = Long.parseLong(request.getParameter("id_owner"));
        Person owner = DAOObjects.daoPerson.findById(id_owner);
        System.out.println(owner.getClass() + ": " + owner);
        request.setAttribute("error", "");
        Email emailToIns = new Email(true, false, owner, "");
        //Now id = null!!! We must set not null value!!!
        emailToIns.setId(0l);
        System.out.println("emailToIns:" + emailToIns);
        request.setAttribute("email", emailToIns);
        request.setAttribute("owner", emailToIns.getOwner());
        String path = "/views/emails/email.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EmailPersonAddServlet#doPost");
        //Get parameters
        String idEmailStr = request.getParameter("id_email");
//        System.out.println(idEmailStr);
        String idOwnerStr = request.getParameter("id_owner");
//        System.out.println(idOwnerStr);
        String mailName = request.getParameter("email");
//        System.out.println(Email);
        String activeStr = new String(request.getParameter("active").getBytes("ISO-8859-1"), "utf-8");
//        System.out.println(activeStr);
        String priorStr = new String(request.getParameter("prior").getBytes("ISO-8859-1"), "utf-8");
//        System.out.println(priorStr);

        // Create object
        Long idEmail = Long.parseLong(idEmailStr);
//        System.out.println(idEmail);
        Long idOwner = Long.parseLong(idOwnerStr);
//        System.out.println(idOwner);
//        System.out.println(Email);
        boolean active = "Активний".equals(activeStr);
//        System.out.println("Активний = " + active);
        boolean prior = "Основний".equals(priorStr);
//        System.out.println("Основний = " + prior);
        Person owner = DAOObjects.daoPerson.findById(idOwner);
        Email email = new Email(active, prior, owner, mailName);
        email.setId(idEmail);
        if (email.isPrior() && !email.isActive()) {
            System.out.println("\"Неактивний\" email не може бути \"Основним\"!!!");
            request.setAttribute("error", ContactMessages.MESSAGE03.getText());
            request.setAttribute("email", email);
            request.setAttribute("owner", owner);
            String path = "/views/emails/email.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
            return;
        }
        //Пошук поточного контакта "Основний"
        //Якщо новий контакт є основним, то поточний сбрасується у додатковий
        Email emailPrior = DAOObjects.daoEmail.findPrior(owner);
        if (emailPrior != null && email.isPrior()) {
            //спочатку зміна телефону поточного основного у додатковий
            emailPrior.setPrior(!email.isPrior());
            boolean updateRes = DAOObjects.daoEmail.update(emailPrior.getId(), emailPrior);
            if (!updateRes) {
                System.out.println("Помилка скидання Основного email! Update SQL mistake!!!");
                String path = request.getContextPath() + "/emails?id_owner=" + idOwner+ "&msgcode=4";
                response.sendRedirect(path);
                return;
            }
        }
        //Call Insert
        boolean insertRes = DAOObjects.daoContact.insert(email);
        if (insertRes) {
            //back to list Emails
            System.out.println("Новий email був доданий!");
            String path = request.getContextPath() + "/emails?id_owner=" + idOwner+ "&msgcode=1";
            response.sendRedirect(path);
            return;
        } else {
            System.out.println("Помилка додавання! Insert SQL mistake!!!");
            request.setAttribute("error", ContactMessages.MESSAGE02.getText());
            request.setAttribute("email", email);
            request.setAttribute("owner", owner);
            String path = "/views/emails/email.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }

    }
}
