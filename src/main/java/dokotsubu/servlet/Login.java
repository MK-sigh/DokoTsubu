package dokotsubu.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dokotsubu.model.LoginLogic;
import dokotsubu.model.User;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
        
		//ユーザー名、PWが充たされている場合
		if((name != null && name.length() !=0)
			&& (pass != null && pass.length() !=0)){
			User user = new User(pass,name);
			LoginLogic loginLogic = new LoginLogic();
			//DBに存在するかチェック（PWチェック込み）
			User findUser = loginLogic.find(user);
			
			//ログイン処理
			if (findUser != null) {
				// ログイン成功時：ユーザー情報をセッションに保存
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", findUser);
			}else{
				request.setAttribute("errorMsg", "パスワードが間違っているか、ユーザーが未登録です");
			}
		}else{
			//どちらか入力されていなければエラーメッセージ
			request.setAttribute("errorMsg", "必要項目が未入力です");
		}
		//ログイン結果画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher
				("WEB-INF/jsp/loginResult.jsp");
		dispatcher.forward(request, response);
	}

}
