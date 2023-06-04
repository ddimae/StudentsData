package ntukhpi.semit.dde.studentsdata.servlets.emails;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import ntukhpi.semit.dde.studentsdata.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/email_active")
public class EmailChangeActiveServlet extends HttpServlet {

    private Email myEmail;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EmailChangeActiveServlet#doGet");
        //Get parameters
        String idEmailStr = request.getParameter("id_email");
//        System.out.println(idEmailStr);
        String idOwnerStr = request.getParameter("id_owner");
//        System.out.println(idOwnerStr);

        // Find objects
        Long idEmail = Long.parseLong(idEmailStr);
//        System.out.println(idEmail);
        Long idOwner = Long.parseLong(idOwnerStr);
//        System.out.println(idOwner);
//        System.out.println(email);
        Person owner = DAOObjects.daoPerson.findById(idOwner);
        Email email = DAOObjects.daoEmail.findById(idEmail);
        //Пошук поточного контакта "Основний"
        //Email emailPrior = DAOObjects.daoEmail.findPrior(owner);
        //Якщо планується Основний email зробити неактивним,
        // то така зміна є неможливою
        if (email.isActive() && email.isPrior()) {
            System.out.println("Зробити  \"Основний\" email \"Неактивним\" неможливо !!!");
            String path = request.getContextPath() + "/emails?id_owner=" + idOwner + "&msgcode=9";
            response.sendRedirect(path);
            return;
        }
        // Готується зміна на протилежне
        email.setActive(!email.isActive());

        //Call Update
        boolean updateRes = DAOObjects.daoEmail.update(email.getId(), email);
        if (updateRes) {
            System.out.println("Статус email змінений!");
//            back to emails_persons
            String path = request.getContextPath() + "/emails?id_owner=" + idOwner + "&msgcode=10";
            response.sendRedirect(path);
        } else {
            System.out.println("Помилка оновлення статусу актуальності! Update SQL mistake!!!");
            String path = request.getContextPath() + "/emails?id_owner=" + idOwner + "&msgcode=11";
            response.sendRedirect(path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }
}