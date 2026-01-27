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


@WebFilter(urlPatterns = {"/app/Main", "/app/Update", "/app/Delete", "/app/Post"}) // POSTを受けるサーブレットを指定
public class CsrfFilter implements Filter{
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException{

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)res;
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        //POST リクエストだけ CSRF チェック対象
        if("POST".equalsIgnoreCase(request.getMethod())){ //get,postを判定し大文字・小文字を区別せず 比較する
            String token = request.getParameter("csrfToken"); //フォームから送られてきたトークンを取得
            String sessionToken = (String) session.getAttribute("csrfToken"); //セッションに保存されたトークンを取得

            if (token == null || !token.equals((sessionToken))){
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "不正なリクエストです。"); //HTTP 403 Forbidden を返す
                return;
            }
        }
        chain.doFilter(request, response);
    }
    
}
