package ntukhpi.semit.dde.studentsdata.servlets.groups;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.files.ExcelUtilities;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;

import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.*;

@WebServlet("/groups/save_students1")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class GroupDownloadF1Servlet extends HttpServlet {

    public static List<AcademicGroup> groups = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GroupDownloadF1Servlet#doGet");
        Long idGroup = Long.parseLong(request.getParameter("id"));
        AcademicGroup groupFromDB = DAOObjects.daoAcademicGroup.findById(idGroup);
        String agName = groupFromDB.getGroupName();
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
//
        String filename = ExcelUtilities.saveToWBExcelWithName(fullSavePath,agName,groupFromDB,"F1");
        //Отправка файлу
        File file = new File(filename);
        filename = file.getName();
        ServletOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", ATTACHMENT_FILENAME + rfc5987_encode(filename)  + "\"");
            response.setContentLength((int) file.length());
            FileInputStream fileInputStream = new FileInputStream(file);
            inputStream = new BufferedInputStream(fileInputStream);
            int readBytes = 0;
            while ((readBytes = inputStream.read()) != -1)
                outputStream.write(readBytes);
        }catch (ExportException e){
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        request.setAttribute("groups", groups);
        String path = "/views/groups/groups.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }



}
