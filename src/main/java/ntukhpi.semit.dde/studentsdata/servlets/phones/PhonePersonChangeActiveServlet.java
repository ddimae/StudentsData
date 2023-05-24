package ntukhpi.semit.dde.studentsdata.servlets.phones;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/phone_active")
public class PhonePersonChangeActiveServlet extends HttpServlet {

    private PhoneNumber myPhone;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PhonePersonChangeActiveServlet#doGet");
        //Get parameters
        String idPhoneStr = request.getParameter("id_phone");
//        System.out.println(idPhoneStr);
        String idOwnerStr = request.getParameter("id_owner");
//        System.out.println(idOwnerStr);

        // Find objects
        Long idPhone = Long.parseLong(idPhoneStr);
//        System.out.println(idPhone);
        Long idOwner = Long.parseLong(idOwnerStr);
//        System.out.println(idOwner);
//        System.out.println(phoneNumber);
        Person owner = DAOObjects.daoPerson.findById(idOwner);
        PhoneNumber phone = DAOObjects.daoPhone.findById(idPhone);
        //Пошук поточного контакта "Основний"
        //PhoneNumber phonePrior = DAOObjects.daoPhone.findPrior(owner);
        //Якщо планується Основний телефон зробити неактивним,
        // то така зміна є неможливою
        if (phone.isActive() && phone.isPrior()) {
            System.out.println("Зробити  \"Основний\" телефон \"Неактивним\" неможливо !!!");
            String path = request.getContextPath() + "/phones?id_owner=" + idOwner + "&msgcode=9";
            response.sendRedirect(path);
            return;
        }
        // Готується зміна на протилежне
        phone.setActive(!phone.isActive());

        //Call Update
        boolean updateRes = DAOObjects.daoPhone.update(phone.getId(), phone);
        if (updateRes) {
            System.out.println("Статус телефону змінений!");
//            back to phones_persons
            String path = request.getContextPath() + "/phones?id_owner=" + idOwner + "&msgcode=10";
            response.sendRedirect(path);
        } else {
            System.out.println("Помилка оновлення статусу актуальності! Update SQL mistake!!!");
            String path = request.getContextPath() + "/phones?id_owner=" + idOwner + "&msgcode=11";
            response.sendRedirect(path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }
}