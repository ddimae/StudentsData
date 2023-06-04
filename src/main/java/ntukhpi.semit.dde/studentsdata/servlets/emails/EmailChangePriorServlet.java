package ntukhpi.semit.dde.studentsdata.servlets.emails;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/email_prior")
public class EmailChangePriorServlet extends HttpServlet {

    private Email myEmail;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("EmailChangePriorServlet#doGet");
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
        Email emailPrior = DAOObjects.daoEmail.findPrior(owner);
        //Якщо планується неактивний email зробити основним,
        // то така зміна є неможливою
        if (!email.isPrior()&&!email.isActive()) {
            System.out.println("Зробити \"Неактивний\" email \"Основним\" неможливо !!!");
            String path = request.getContextPath() + "/emails?id_owner=" + idOwner+ "&msgcode=5";
            response.sendRedirect(path);
            return;
        }
        // Готується зміна на протилежне
        email.setPrior(!email.isPrior());
        //Call Update
        boolean updateRes = false;
        if (emailPrior != null&&!emailPrior.equals(email)) {
            //спочатку зміна email поточного основного у додатковий
            emailPrior.setPrior(!email.isPrior());
            updateRes = DAOObjects.daoEmail.update(emailPrior.getId(), emailPrior);
            if (!updateRes) {
                System.out.println("Помилка скидання Основного email! Update SQL mistake!!!");
                String path = request.getContextPath() + "/emails?id_owner=" + idOwner+ "&msgcode=4";
                response.sendRedirect(path);
                return;
            }
        }
        //Встановлюємо поточний телефон як основний
        updateRes = DAOObjects.daoEmail.update(email.getId(), email);
        if (updateRes) {
            //back to emails_persons
            if (email.equals(emailPrior)&&!email.isPrior()) {
                System.out.println("\"Основний\" email вилучено!");
                String path = request.getContextPath() + "/emails?id_owner=" + idOwner+ "&msgcode=6";
                response.sendRedirect(path);
                return;
            } else {
                System.out.println("\"Основний\" email змінено!");
                String path = request.getContextPath() + "/emails?id_owner=" + idOwner+ "&msgcode=7";
                response.sendRedirect(path);
                return;
            }

        } else {
            System.out.println("Помилка оновлення! Перевірте наявність email із статусом \"Основний\"! Update SQL mistake!!!");
            String path = request.getContextPath() + "/emails?id_owner="+idOwner+ "&msgcode=8";
            response.sendRedirect(path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}