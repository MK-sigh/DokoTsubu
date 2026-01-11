package dokotsubu.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dokotsubu.DAO.UserDAO;
import dokotsubu.model.LoginData;
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
        
        LoginData loginData = new LoginData(name, pass);
        
        // DAOを使ってDBからユーザーを探す
        UserDAO dao = new UserDAO();
        User user = dao.findByLogin(loginData);
        
        if (user != null) {
            // ログイン成功時：ユーザー情報をセッションに保存
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user

			);
        }
		//ログイン結果画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher
				("WEB-INF/jsp/loginResult.jsp");
		dispatcher.forward(request, response);
	}

}
