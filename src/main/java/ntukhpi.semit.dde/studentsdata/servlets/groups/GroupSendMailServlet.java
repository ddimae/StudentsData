package ntukhpi.semit.dde.studentsdata.servlets.groups;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.files.ExcelUtilities;
import ntukhpi.semit.dde.studentsdata.servlets.service.EmailSender;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.*;

@WebServlet("/groups/send_students")
public class GroupSendMailServlet extends HttpServlet {

    public static List<AcademicGroup> groups = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("GroupSendMailServlet#doPost");
        String reportForm = request.getParameter("report_form");
        if (reportForm.charAt(0)=='F') {
            Long idGroup = Long.parseLong(request.getParameter("id"));
            AcademicGroup groupFromDB = DAOObjects.daoAcademicGroup.findById(idGroup);
            String agName = groupFromDB.getGroupName();
            //Формування файлу для отправки та збереження його на диску
//        Якщо каталогу на сервері немає, то його треба створити
//        Gets absolute path to root directory of web app.
            String appPath = request.getServletContext().getRealPath("");
            appPath = appPath.replace('\\', '/');
//        The directory to save uploaded file
            String fullSavePath = null;
            if (appPath.endsWith("/")) {
                fullSavePath = appPath + STUDENTSDATA_FILES_FOLDER;
            } else {
                fullSavePath = appPath + "/" + STUDENTSDATA_FILES_FOLDER;
            }
//        Creates the save directory if it does not exists
            File fileSaveDir = new File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
//         Запис у формат Excel
            String fullfilename =ExcelUtilities.saveToWBExcelWithName(fullSavePath, agName, groupFromDB, reportForm);
            System.out.println(fullfilename);
            //Отправка файлу на пошту
            //отримується адрес пошти з request
            String emailTo = request.getParameter("email_to");
            System.out.println(emailTo);
            Path path = Paths.get(fullfilename);
            String filename = path.getFileName().toString();
            System.out.println(filename);
            EmailSender.sendEmail(emailTo, filename, fullSavePath);
        }
//        groups = DAOObjects.daoAcademicGroup.getAllList();
//        groups = groups.stream().sorted(Comparator.comparing(AcademicGroup::getGroupName)).toList();
//        request.setAttribute("groups", groups);
        String path = request.getContextPath() + "/groups";
        response.sendRedirect(path);
//        String path = "/groups";
//        ServletContext servletContext = getServletContext();
//        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//        requestDispatcher.forward(request, response);

    }



}
