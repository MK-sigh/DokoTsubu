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
import dokotsubu.model.DeleteMutterLogic;
import dokotsubu.model.GetMutterListLogic;
import dokotsubu.model.Mutter;
import dokotsubu.model.User;
import dokotsubu.util.ValidationUtils;

@WebServlet ("/app/Delete")
public class DeleteMutter extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doPost (HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException{
        //パラメータ取得
        //idのバリデーション
        String idStr = request.getParameter("id");
        if (!ValidationUtils.validateId(request, response, idStr, "/WEB-INF/jsp/main.jsp")) return;
        // 正常ならキャスト
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
            //正常：削除処理
            DeleteMutterLogic deleteMutterLogic = new DeleteMutterLogic();
            boolean result = deleteMutterLogic.execute(id);
            if(!result){
                request.setAttribute("errorMsg", "削除に失敗しました");
            }
            //つぶやきリストを取得して、リクエストスコープに保存
            GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
            List<Mutter> mutterList = getMutterListLogic.execute();
            request.setAttribute("mutterList", mutterList);
        }else{
            //異常：エラーメッセージ
            request.setAttribute("errorMsg", "自分以外のつぶやきは削除できません。");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        dispatcher.forward(request, response);
    }
    
}
