package pChat.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pChat.model.GetMutterListLogic;
import pChat.model.Mutter;

public class ValidationUtils {

    private ValidationUtils() {} // インスタンス化禁止

    public static boolean validateTextLength(HttpServletRequest request, HttpServletResponse response,
         String text, String forwardPath) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();

        if (text == null || text.isBlank()) {
            errors.add("本文は必須です");
        } else if (text.length() > 140) {
            errors.add("本文は140文字以内で入力してください");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errorMsgs", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
            //チャットリストを取得して、リクエストスコープに保存
            GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
            List<Mutter> mutterList = getMutterListLogic.execute();
            request.setAttribute("mutterList", mutterList);
            //フォワード
            dispatcher.forward(request, response);
            return false; // エラーあり
        }
        return true; // 正常
    }

    public static boolean validateId(HttpServletRequest request, HttpServletResponse response,
         String id, String forwardPath) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        if (id == null || !id.matches("^[1-9][0-9]*$")) {
            errors.add("不正なIDです");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errorMsgs", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
            //チャットリストを取得して、リクエストスコープに保存
            GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
            List<Mutter> mutterList = getMutterListLogic.execute();
            request.setAttribute("mutterList", mutterList);
            //フォワード
            dispatcher.forward(request, response);
            return false; // エラーあり
        }
        return true; // 正常
    }

    public static boolean validateName(HttpServletRequest request, HttpServletResponse response,
         String name, String forwardPath) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        if (name == null || !name.matches("^[a-zA-Z0-9_]{3,20}$")) {
                errors.add("ユーザー名は3〜20文字の英数字とアンダースコアのみです。");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errorMsgs", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
            //チャットリストを取得して、リクエストスコープに保存
            GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
            List<Mutter> mutterList = getMutterListLogic.execute();
            request.setAttribute("mutterList", mutterList);
            //フォワード
            dispatcher.forward(request, response);
            return false; // エラーあり
        }
        return true; // 正常
    }

    public static boolean validatePass(HttpServletRequest request, HttpServletResponse response, 
            String pass, String forwardPath) throws ServletException, IOException {
        
        List<String> errors = new ArrayList<>();
        if (pass == null || !pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\w!@#$%^&*]{8,32}$")) {
            errors.add("パスワードは8～32文字の大文字・小文字・英数字・記号の組み合わせです。");
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errorMsgs", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
            //チャットリストを取得して、リクエストスコープに保存
            GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
            List<Mutter> mutterList = getMutterListLogic.execute();
            request.setAttribute("mutterList", mutterList);
            //フォワード
            dispatcher.forward(request, response);
            return false; // エラーあり
        }
        return true; // 正常
    }
}
