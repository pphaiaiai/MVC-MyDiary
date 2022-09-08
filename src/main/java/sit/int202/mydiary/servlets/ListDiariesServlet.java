package sit.int202.mydiary.servlets;

import jakarta.persistence.EntityManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import sit.int202.mydiary.entities.Diary;
import sit.int202.mydiary.entities.Owner;
import sit.int202.mydiary.repositories.DiaryRepository;
import sit.int202.mydiary.repositories.OwnerRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "ListDiariesServlet", value = "/list-diaries")
public class ListDiariesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if( action == null){
            HttpSession session = request.getSession(false);
            Owner owner = (Owner)session.getAttribute("owner");
            DiaryRepository diaryRepo = new DiaryRepository();
            List<Diary> diaries = diaryRepo.getResultListFromNamedQueryWithParam("Diary.ShowAllByOwner" , owner);
            request.setAttribute("diaries", diaries);
            getServletContext().getRequestDispatcher("/home_page.jsp").forward(request,response);

         } else if (action.equals("new")){
            getServletContext().getRequestDispatcher("/diary.jsp").forward(request,response);
            return;
        } else if (action.equals("edit")){
            DiaryRepository diaryRepo = new DiaryRepository();
            try {
                Long id = Long.parseLong( request.getParameter("id") ) ;
                Diary diary = diaryRepo.find(id) ;
                if(diary != null){
                    request.setAttribute("diary",diary);
                    getServletContext().getRequestDispatcher("/diary.jsp").forward(request, response);
                    return ;
                }
            } catch ( NumberFormatException nfe){
                System.out.println("Error : "+nfe.getMessage());
            }
        } else if( action.equals("remove") ){
            try {
                Long id = Long.parseLong( request.getParameter("id") ) ;
                try {
                    OwnerRepository ownerRepo = new OwnerRepository() ;
                    EntityManager em = ownerRepo.getEntityManager() ;
                    em.getTransaction().begin();
                    Diary diary = em.find(Diary.class, id) ;
                    Owner owner = diary.getOwnerUsername() ;
                    owner.getDiaries().remove(diary) ;
                    em.merge(owner) ;
                    em.remove(diary);
                    em.getTransaction().commit();
                    em.close() ;
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }

                response.sendRedirect("list-diaries");
                return ;
            } catch (NumberFormatException nfe){
                System.out.println("---------------------"+nfe.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false) ;
        Owner owner = (Owner) session.getAttribute("owner") ;
        String action = request.getParameter("action") ;

        DiaryRepository diaryRepo = new DiaryRepository() ;
        String strDiaryDate = request.getParameter("diaryDate") ;
        String title = request.getParameter("title") ;
        String body = request.getParameter("body") ;

        System.out.println("diary Date : "+strDiaryDate);

        // edit diary
        if( action != null && action.equals("edit") ){
            try {
                Long id = Long.parseLong( request.getParameter("id") ) ;
                Diary diary = diaryRepo.find(id) ;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    diary.setDiaryDate(sdf.parse(strDiaryDate));
                    diary.setTitle(title);
                    diary.setBody(body);

                    diaryRepo.update(diary) ;

                } catch ( ParseException pe ){
                    System.out.println("---------------------"+pe.getMessage());
                }
            } catch (NumberFormatException nfe){
                System.out.println("---------------------"+nfe.getMessage());
            }

            // create diary
        } else {
            Diary diary = new Diary() ;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                diary.setDiaryDate(sdf.parse(strDiaryDate));
                diary.setTitle(title);
                diary.setBody(body);
                diary.setOwnerUsername(owner);

                diaryRepo.save(diary) ;

            } catch (ParseException pe){
                System.out.println("---------------------"+pe.getMessage());
            }
        }

        response.sendRedirect("list-diaries");
    }
}
