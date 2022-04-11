<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>欢迎注册EasyMall</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/regist.css"/>
    <%-- 引入jQuery函数库 --%>
    <script src="${pageContext.request.contextPath}/js/jQuery-3.6.0.js"></script>
    <%--  前台校验  --%>
    <script type="text/javascript">
        var formObj = {
            "checkForm":function (){
                var canSub = true;
                //1.获取参数
                //2.作出校验
                canSub = this.checkNull("username","用户名不能为空") && canSub;
                canSub = this.checkNull("password","密码不能为空") && canSub;
                canSub = this.checkNull("password2","确认密码不能为空") && canSub;
                canSub = this.checkNull("nickname","昵称不能为空") && canSub;
                canSub = this.checkNull("email","邮箱不能为空") && canSub;
                canSub = this.checkNull("valistr","验证码不能为空") && canSub;
                //密码一致性校验
                canSub = this.checkPassword() && canSub;
                //邮箱格式校验
                canSub = this.checkEmail() && canSub;
                return canSub;
            },
            "checkNull":function (name,msg){
                //非空校验
                var tag = $("input[name='"+name+"']").val();
                //清空操作
                this.setMsg(name,"");
                if(tag == ""){
                    this.setMsg(name,msg);
                    return false;
                }
                return true;
            },
            "checkPassword":function (){
                //密码一致性校验
                var password = $("input[name='password']").val();
                var password2 = $("input[name='password2']").val();
                if (password!="" && password2!="" && password!=password2){
                    this.setMsg("password2","两次密码不一致");
                    return false;
                }
                return true;
            },
            "checkEmail":function (){
                //邮箱校验
                var reg = /\w+@\w+(\.\w+)+/;
                var email = $("input[name='email']").val();
                if (email!="" && !reg.test(email)){
                    this.setMsg("email","邮箱格式不正确");
                    return false;
                }
                return true;
            },
            "setMsg":function (name,msg){
                //消息提示
                $("input[name='"+name+"']").nextAll("span").text(msg).css("color","red");
            }
        };
        //文档就绪事件
        $(function (){
            //为username输入框添加鼠标离焦事件
           $("input[name='username']").blur(function (){
               formObj.checkNull("username","用户名不能为空");
               //获取输入框中值
               var username = $(this).val();
               //ajax校验用户名是否存在
               if (username != ""){//判断ajax是否需要执行
                   $("#username_msg").load("${pageContext.request.contextPath}/AjaxCheckUsernameServlet",{"username":username});
               }

           });
            $("input[name='password']").blur(function (){
                formObj.checkNull("password","密码不能为空");
            });
            $("input[name='password2']").blur(function (){
                formObj.checkNull("password2","确认密码不能为空");
                formObj.checkPassword();
            });
            $("input[name='nickname']").blur(function (){
                formObj.checkNull("nickname","昵称不能为空");
            });
            $("input[name='email']").blur(function (){
                formObj.checkNull("email","邮箱不能为空");
                formObj.checkEmail();
            });
            $("input[name='valistr']").blur(function (){
                formObj.checkNull("valistr","验证码不能为空");
            });
            //为图片添加单击事件，更新验证码
            $("#img").click(function (){
                var date = new Date();
                var time = date.getTime();
                $(this).attr("src","${pageContext.request.contextPath}/ValidateServlet?time="+time);
            });
        });
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/RegistServlet" method="POST" onsubmit="return formObj.checkForm()">
    <h1>欢迎注册EasyMall</h1>
    <table>
        <tr>
            <td class="tds" colspan="2" style="color: red;text-align: center">
                ${msg}
            </td>
        </tr>
        <tr>
            <td class="tds">用户名：</td>
            <td>
                <%-- 回显用户名 --%>
                <input type="text" name="username"
                       value="${param.username}"/>
                <span id="username_msg"></span>
            </td>
        </tr>
        <tr>
            <td class="tds">密码：</td>
            <td>
                <input type="password" name="password"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">确认密码：</td>
            <td>
                <input type="password" name="password2"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">昵称：</td>
            <td>
                <input type="text" name="nickname"
                       value="${param.nickname}"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">邮箱：</td>
            <td>
                <input type="text" name="email"
                       value="${param.email}"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">验证码：</td>
            <td>
                <input type="text" name="valistr"/>
                <img id="img" src="${pageContext.request.contextPath}/ValidateServlet" width="" height="" alt="" />
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="sub_td" colspan="2" class="tds">
                <input type="submit" value="注册用户"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
