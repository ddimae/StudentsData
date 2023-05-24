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

@WebServlet("/phone_prior")
public class PhonePersonChangePriorServlet extends HttpServlet {

    private PhoneNumber myPhone;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PhonePersonChangePriorServlet#doGet");
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
        PhoneNumber phonePrior = DAOObjects.daoPhone.findPrior(owner);
        //Якщо планується неактивний телефон зробити основним,
        // то така зміна є неможливою
        if (!phone.isPrior()&&!phone.isActive()) {
            System.out.println("Зробити \"Неактивний\" телефон \"Основним\" неможливо !!!");
            String path = request.getContextPath() + "/phones?id_owner=" + idOwner+ "&msgcode=5";
            response.sendRedirect(path);
            return;
        }
        // Готується зміна на протилежне
        phone.setPrior(!phone.isPrior());
        //Call Update
        boolean updateRes = false;
        if (phonePrior != null&&!phonePrior.equals(phone)) {
            //спочатку зміна телефону поточного основного у додатковий
            phonePrior.setPrior(!phone.isPrior());
            updateRes = DAOObjects.daoPhone.update(phonePrior.getId(), phonePrior);
            if (!updateRes) {
                System.out.println("Помилка скидання Основного телефону! Update SQL mistake!!!");
                String path = request.getContextPath() + "/phones?id_owner=" + idOwner+ "&msgcode=4";
                response.sendRedirect(path);
                return;
            }
        }
        //Встановлюємо поточний телефон як основний
        updateRes = DAOObjects.daoPhone.update(phone.getId(), phone);
        if (updateRes) {
            //back to phones_persons
            if (phone.equals(phonePrior)&&!phone.isPrior()) {
                System.out.println("\"Основний\" телефон вилучено!");
                String path = request.getContextPath() + "/phones?id_owner=" + idOwner+ "&msgcode=6";
                response.sendRedirect(path);
                return;
            } else {
                System.out.println("\"Основний\" телефон змінено!");
                String path = request.getContextPath() + "/phones?id_owner=" + idOwner+ "&msgcode=7";
                response.sendRedirect(path);
                return;
            }

        } else {
            System.out.println("Помилка оновлення! Перевірте наявність телефону із статусом \"Основний\"! Update SQL mistake!!!");
            String path = request.getContextPath() + "/phones?id_owner="+idOwner+ "&msgcode=8";
            response.sendRedirect(path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}