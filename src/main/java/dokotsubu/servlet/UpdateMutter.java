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

@WebServlet ("/app/Update")
public class UpdateMutter extends HttpServlet{
    private static final long serialVersionUID = 1L;

      protected void doGet (HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException{
            //パラメータ取得
            //idの値チェック＆キャスト
            String idStr = request.getParameter("id");
            if (idStr == null || !idStr.matches("^[1-9][0-9]*$")) {
                  request.setAttribute("errorMsg", "不正なIDです");
                  RequestDispatcher dispatcher =
                  request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
                  dispatcher.forward(request, response);
                  return;
            }
            int id = Integer.parseInt(idStr);
            MuttersDAO muttersDao = new MuttersDAO();
            Mutter mutter = muttersDao.findById(id);
            //存在チェック
            if (mutter == null) {
                  request.setAttribute("errorMsg", "指定されたつぶやきは存在しません。");
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
                  request.setAttribute("errorMsg", "ログインが必要です。");
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
                  request.setAttribute("errorMsg", "自分以外のつぶやきは編集できません。");
                  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
                  dispatcher.forward(request, response);
                  return;
            }
      }


    protected void doPost (HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException{
        //リクエストパラメータの取得
        request.setCharacterEncoding("UTF-8");
        //idの値チェック＆キャスト
        String idStr = request.getParameter("id");
        if (idStr == null || !idStr.matches("^[1-9][0-9]*$")) {
            request.setAttribute("errorMsg", "不正なIDです");
            RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
            dispatcher.forward(request, response);
            return;
        }
        int id = Integer.parseInt(idStr);
        String text = request.getParameter("text");
		
		if (text != null && text.length() !=0) {
                  //セッションスコープに保存されたユーザー情報を取得
                  HttpSession session = request.getSession();
                  User loginUser = (User) session.getAttribute("loginUser");
                  //ログインチェック
                  if (loginUser == null) {
                        request.setAttribute("errorMsg", "ログインが必要です。");
                        response.sendRedirect("index.jsp");
                        return;
                  }
                  //ユーザー照合
                  if(id == loginUser.getId()){
                        //正常：編集作業を行う
                        UpdateMutterLogic updateMutterLogic = new UpdateMutterLogic();
                        boolean result = updateMutterLogic.execute(id, text);
                        if (!result){
                              request.setAttribute("errorMsg", "更新に失敗しました。");
                        }
                  }else{
                        //異常：メイン画面にフォワード
                        request.setAttribute("errorMsg", "自分以外のつぶやきは編集できません。");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
                        dispatcher.forward(request, response);
                        return;
                  }
            }else {
			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg", "テキストが入力されていません。");
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
