package dokotsubu.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import dokotsubu.DAO.MuttersDAO;
import dokotsubu.model.GetMutterListLogic;
import dokotsubu.model.Mutter;
import dokotsubu.model.UpdateMutterLogic;
import dokotsubu.model.User;
import dokotsubu.util.ValidationUtils;

@WebServlet ("/app/Update")
public class UpdateMutter extends HttpServlet{
      private static final long serialVersionUID = 1L;

      protected void doGet (HttpServletRequest request,HttpServletResponse response)
                  throws ServletException, IOException{
            //パラメータ取得
            //id値バリデーション
            String idStr = request.getParameter("id");
            if(!ValidationUtils.validateId(request, response, idStr, "WEB-INF/jsp/main.jsp"))return;
            //キャスト
            int id = Integer.parseInt(idStr);
            MuttersDAO muttersDAO = new MuttersDAO();
            Mutter mutter = muttersDAO.findById(id);
            //存在チェック
            if (mutter == null) {
                  request.setAttribute("errorMsg", List.of("指定されたつぶやきは存在しません。"));
                  //メイン画面にフォワード
                  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
                  dispatcher.forward(request, response);
                  return;
            }

            //セッションスコープに保存されたユーザー情報を取得
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("loginUser");
            //ログインチェック
            if (loginUser == null) {
                  request.setAttribute("errorMsg", List.of("ログインが必要です。"));
                  response.sendRedirect("index.jsp");
                  return;
            }
            //ユーザー照合
            if(mutter.getUserId() == loginUser.getId()){
                  //正常：編集フォームを表示する
                  request.setAttribute("mutter", mutter);
                  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updateMutter.jsp");
                  dispatcher.forward(request, response);
            }else{
                  //異常：メイン画面にフォワード
                  request.setAttribute("errorMsg", List.of("自分以外のつぶやきは編集できません。"));
                  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
                  dispatcher.forward(request, response);
                  return;
            }
      }


      protected void doPost (HttpServletRequest request,HttpServletResponse response)
                  throws ServletException, IOException{
            //リクエストパラメータの取得
            request.setCharacterEncoding("UTF-8");
            //id値バリデーション
            String idStr = request.getParameter("id");
            if(!ValidationUtils.validateId(request, response, idStr, "/WEB-INF/jsp/main.jsp"))return;
            //キャスト
            int id = Integer.parseInt(idStr);
            //本文のバリデーション
            String text = request.getParameter("text");
            if(!ValidationUtils.validateTextLength(request, response, text, "/WEB-INF/jsp/main.jsp"))return;

            //セッションスコープに保存されたユーザー情報を取得
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("loginUser");
            //ログインチェック
            if (loginUser == null) {
                  request.setAttribute("errorMsg", List.of("ログインが必要です。"));
                  response.sendRedirect("index.jsp");
                  return;
            }

            //ユーザー照合
            MuttersDAO muttersDAO = new MuttersDAO();
            Mutter mutter = muttersDAO.findById(id);
            if(mutter.getUserId() == loginUser.getId()){
                  //正常：編集作業を行う
                  UpdateMutterLogic updateMutterLogic = new UpdateMutterLogic();
                  boolean result = updateMutterLogic.execute(id, text);
                  if (!result){
                        request.setAttribute("errorMsg", List.of("更新に失敗しました。"));
                  }
            }else{
                  //異常：メイン画面にフォワード
                  request.setAttribute("errorMsg", List.of("自分以外のつぶやきは編集できません。"));
                  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
                  dispatcher.forward(request, response);
                  return;
            }

            //つぶやきリストを取得して、リクエストスコープに保存
            GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
            List<Mutter> mutterList = getMutterListLogic.execute();
            request.setAttribute("mutterList", mutterList);

            //メイン画面にフォワード
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
            dispatcher.forward(request, response);
	}
}
