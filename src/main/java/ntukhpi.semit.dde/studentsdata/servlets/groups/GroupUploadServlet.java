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
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class GroupUploadServlet extends HttpServlet {

    public static List<AcademicGroup> groups = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("GroupUploadServlet#doPost");
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
            String filePath = null;
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && fileName.length() > 0) {
                    filePath = fullSavePath + File.separator + fileName;
                    System.out.println("Write attachment to file: " + filePath);
                    // Write to file
                    part.write(filePath);
                }
            }
            AcademicGroup newGroup = ExcelUtilities.readFromWBExcelFullToClearDB(filePath);
            if (newGroup != null) {
                boolean saveToDBOk = DAOObjects.daoAcademicGroup.saveAcademicGroupToDB(newGroup);
                if (saveToDBOk) {
                    System.out.println("AcademicGroup "+newGroup.getGroupName()+"was read from Excel and was saved in DB");
                    System.out.println(newGroup.toStringWithGrouplist());
                }
            } else {
                System.out.println("No AcademicGroup to save");
            }

        } finally {
            /// Upload successfully!.
            response.sendRedirect(request.getContextPath() + "/groups");
        }

    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }


}
