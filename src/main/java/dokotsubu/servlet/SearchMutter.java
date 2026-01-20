package dokotsubu.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import dokotsubu.DAO.MuttersDAO;
import dokotsubu.model.GetMutterListLogic;
import dokotsubu.model.Mutter;

@WebServlet ("/app/Search")
public class SearchMutter extends HttpServlet{
    private static final long serialVersionUID = 1L;
    protected void doPost (HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException{
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("keyword");
		
		//入力値☑
		if (keyword != null && keyword.length() !=0) {
            MuttersDAO dao = new MuttersDAO();
            List<Mutter> mutterList = dao.search(keyword);
            request.setAttribute("mutterList", mutterList);

			
		}else {
            //つぶやきリストを取得して、リクエストスコープに保存
            GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
            List<Mutter> mutterList = getMutterListLogic.execute();
            request.setAttribute("mutterList", mutterList);
		}

		//メイン画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher
				("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
