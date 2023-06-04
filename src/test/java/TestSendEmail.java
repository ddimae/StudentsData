import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.files.ExcelUtilities;
import ntukhpi.semit.dde.studentsdata.servlets.service.EmailSender;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSendEmail {

    @Test
    void testSendEmail() {
        //Перевіряйте імена файлів!!!! Особливо із букавами, які є і в укр, і в инглиш
        //У цій назві КН - це НЕ українські букви, але спробуйте візуально знайти відмінності!!!
        //Також пошта може не відправлятися через роутер регіонального провайдера або приватну мережу
        //Гарантований результат був при відправці через мобільний інтернет
        EmailSender.sendEmail("ddimae72@gmail.com", "KН-221в_F2.xlsx", ExcelUtilities.STUDENTSDATA_FILES_FOLDER);
    }

    @Test
    void testSend() {
        EmailSender.send();
    }


}
