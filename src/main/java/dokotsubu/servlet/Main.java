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

import dokotsubu.model.GetMutterListLogic;
import dokotsubu.model.Mutter;
import dokotsubu.model.PostMutterLogic;
import dokotsubu.model.User;
import dokotsubu.util.ValidationUtils;

@WebServlet("/app/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute();
		request.setAttribute("mutterList", mutterList);
		if (mutterList == null){
			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg",List.of( "つぶやきはありません。"));
			return;}

		//ログインしているか確認するためセッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			//ログインしていない場合はリダイレクト
			response.sendRedirect("index.jsp");
		}else {
			//ログイン済みの場合フォワード
			RequestDispatcher dispatcher =
					request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost (HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException{
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");
		
		//入力値バリデーション
		if(!ValidationUtils.validateTextLength(request, response, text, "/WEB-INF/jsp/main.jsp"))return;

		//セッションスコープに保存されたユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		
		//呟きを生成してつぶやきリストに追加
		Mutter mutter = new Mutter (loginUser.getId(),text);
		PostMutterLogic postMutterLogic = new PostMutterLogic();
		boolean result = postMutterLogic.execute(mutter);
		if (!result){
			request.setAttribute("errorMsg",List.of( "投稿に失敗しました。"));
		}
		
		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute();
		request.setAttribute("mutterList", mutterList);
		
		//メイン画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher
				("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
