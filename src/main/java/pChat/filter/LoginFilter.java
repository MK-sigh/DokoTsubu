package pChat.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import pChat.model.User;

@WebFilter("/app/*")///app/* 配下のすべてのリクエストがフィルターを通過
public class LoginFilter implements Filter{

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException{
    
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //セッションがなければ新しく作らない
        HttpSession session = request.getSession(false);
        User loginUser = null;

        if (session != null){
            loginUser = (User) session.getAttribute("loginUser");
        }
        if (loginUser == null){
            //ログイン画面に飛ばす
            response.sendRedirect(request.getContextPath()+"/Login");
            return;
        }
        //ログイン済みの場合、次のサーブレットまたは次のフィルターにリクエストを渡す
        chain.doFilter(request, response);

    }
    
}
