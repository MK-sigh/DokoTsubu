# ちょっとつよくなったDokoTsubu
  
<img width="1412" height="765" alt="削除機能" src="https://github.com/user-attachments/assets/64f62e5c-47c4-4125-a65a-446c5491bfd3" />

## 概要
DokoTsubu（どこつぶ）は、ユーザー登録・ログイン機能付きの簡易つぶやき投稿アプリです。ユーザーは自分のつぶやきを投稿・編集・削除でき、他のユーザーのつぶやきも閲覧可能です。

セキュリティ面では、以下の対策が実装されています：
- CSRFトークンによるPOSTリクエスト保護
- セッション管理（ログイン時に古いセッション破棄＆新規セッション生成）
- パスワードはBCryptでハッシュ化して保存
- ログインフィルターで未ログインユーザーのアクセス制御
- HTMLエスケープによるXSS攻撃防止

## 技術スタック
- Java 17
- Jakarta Servlet 5
- JSP
- Maven
- H2 Database
- Apache Commons Text
- BCrypt

## ディレクトリ構成
```
dokoTsubu/
├─ src/main/java/dokotsubu/servlet/   # サーブレット
├─ src/main/java/dokotsubu/model/     # ビジネスロジック・DTO
├─ src/main/java/dokotsubu/DAO/       # データアクセスオブジェクト
├─ src/main/java/dokotsubu/filter/    # フィルター（Login, CSRF）
├─ src/main/java/dokotsubu/util/      # DBManager
├─ src/main/webapp/WEB-INF/jsp/       # JSPファイル
│   ├─ main.jsp
│   ├─ updateMutter.jsp
│   ├─ registerView.jsp
│   ├─ registerResult.jsp
│   ├─ loginResult.jsp
│   └─ logout.jsp
├─ src/main/webapp/index.jsp           # ログイン画面
├─ src/main/webapp/css/style.css       # CSS
├─ pom.xml
└─ web.xml
```

## 主な機能
### ユーザー登録
- フォームからユーザー名とパスワードを入力
- パスワードはBCryptでハッシュ化して保存
- 登録後、登録完了画面へ遷移

### ログイン
- ユーザー名とパスワードで認証
- 認証成功時は新しいセッション生成＆CSRFトークン生成
- ログイン後メイン画面に遷移

### つぶやき機能
- 投稿: テキストを入力してつぶやき投稿（POST）
- 編集: 自分のつぶやきのみ編集可能
- 削除: 自分のつぶやきのみ削除可能
- 検索: キーワードでつぶやきを検索

### セキュリティ
- CSRFトークンを全てのPOSTフォームに埋め込み
- 未ログインユーザーのアクセスはLoginFilterで制御
- ログイン時に既存セッション破棄、新規セッション生成
- `StringEscapeUtils.escapeHtml4()`によるHTTPエスケープ
- セッションタイムアウト（15分）設定済み

## ビルド & デプロイ
1. Mavenでビルド:
```
mvn clean package
```
2. WARファイルをTomcat等のServletコンテナにデプロイ
3. `http://localhost:8080/dokoTsubu/` にアクセス

## 既知の制限
- タイムアウト時のエラーメッセージはまだ実装されていません
- CSRFトークンはログイン時以外は生成されますが、ログアウト時の処理は簡易的です

## データベース
- H2 Database使用
- テーブル例:
```
ACCOUNTS(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), PASS VARCHAR(255));
MUTTERS(ID INT AUTO_INCREMENT PRIMARY KEY, USER_ID INT, TEXT VARCHAR(255));
```
- DBManager.java で接続管理

## 注意点
- JSPファイルは `WEB-INF/jsp/` 配下に配置
- ServletのURLパターンは `/app/*` に統一
- フィルターでPOSTリクエストのCSRFチェックを行う
