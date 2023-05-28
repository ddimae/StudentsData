package ntukhpi.semit.dde.studentsdata.servlets.groups;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.files.ExcelUtilities;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.STUDENTSDATA_FILES_FOLDER;

@WebServlet("/groups/load_students")
public class GroupUploadServlet extends HttpServlet {

    public static List<AcademicGroup> groups = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("GroupUploadServlet#doPost");
        Long idGroup = Long.parseLong(request.getParameter("id"));
        AcademicGroup groupFromDB = DAOObjects.daoAcademicGroup.findById(idGroup);
        String agName = groupFromDB.getGroupName();
        String fullfilename = ExcelUtilities.saveToWBExcel(agName, groupFromDB, "F1");
        try {
            //https://o7planning.org/11069/uploading-and-downloading-files-stored-to-hard-drive-with-java-servlet
            // Gets absolute path to root directory of web app.
            String appPath = request.getServletContext().getRealPath("");
            appPath = appPath.replace('\\', '/');

            // The directory to save uploaded file
            String fullSavePath = null;
            if (appPath.endsWith("/")) {
                fullSavePath = appPath + STUDENTSDATA_FILES_FOLDER;
            } else {
                fullSavePath = appPath + "/" + STUDENTSDATA_FILES_FOLDER;
            }

            // Creates the save directory if it does not exists
            File fileSaveDir = new File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            // Part list (multi files).
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && fileName.length() > 0) {
                    String filePath = fullSavePath + File.separator + fileName;
                    System.out.println("Write attachment to file: " + filePath);
                    // Write to file
                    part.write(filePath);
                }
            }
            // Upload successfully!.
            response.sendRedirect(request.getContextPath() + "/uploadFileResults");
        } catch (Exception e) {
            //e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsps/uploadFile.jsp");
            dispatcher.forward(request, response);
        }
        request.setAttribute("groups", groups);
        String path = "/views/groups/groups.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    private String extractFileName(Part part) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String contentDisp = part.getHeader("content-disposition");
        // String[] items = contentDisp.split(";");
        // for (String s : items) {
        if (contentDisp.trim().startsWith("filename")) {
            // C:\file1.zip
            // C:\Note\file2.zip
            String clientFileName = contentDisp.substring(contentDisp.indexOf("=") + 2, contentDisp.length() - 1);
            clientFileName = clientFileName.replace("\\", "/");
            int i = clientFileName.lastIndexOf('/');
            // file1.zip
            // file2.zip
            return clientFileName.substring(i + 1);
        }
        // }
        return null;
    }

}
