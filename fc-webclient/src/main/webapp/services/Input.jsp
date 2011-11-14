<%@page contentType="text/html;charset=UTF-8" %>
<HTML>
<HEAD>
    <TITLE>输入</TITLE>
    <STYLE TYPE="text/css">
        body {
            font-family: Helvetica, Arial, sans-serif;
            font-size: small
        }
    </STYLE>
</HEAD>
<BODY>
<H1>输入</H1>

<%
    String method = request.getParameter("method");
    int methodID = 0;
    if (method == null) methodID = -1;

    boolean valid = true;

    if (methodID != -1) methodID = Integer.parseInt(method);
    switch (methodID) {
        case 2:
            valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 5:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD COLSPAN="1" ALIGN="LEFT">ALE 服务终端:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="endpoint" style="width:450px;"><br/>
                比如: http://localhost:8080/fc-server-1.0.2/services/ALEService
            </TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="Invoke">
    <INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
        break;
    case 10:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="Invoke">
    <INPUT TYPE="RESET" VALUE="Clear">
</FORM>
<%
        break;
    case 14:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">名称:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName" SIZE=50></TD>
        </TR>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">文件路径:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specFilePath" SIZE=50></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 50:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">名称:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName55" SIZE=20></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 57:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">名称:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName102" SIZE=20></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 104:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 109:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">通知 URI:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="notificationURI114" SIZE=20></TD>
        </TR>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">名称:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName116" SIZE=20></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 118:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">通知 URI:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="notificationURI123" SIZE=20></TD>
        </TR>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">名称:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName125" SIZE=20></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 127:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">名称:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName192" SIZE=20></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 194:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">文件路径:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specFilePath" SIZE=50></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 289:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD WIDTH="5%"></TD>
            <TD COLSPAN="2" ALIGN="LEFT">名称:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="specName294" SIZE=20></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 296:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 301:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;

    case 317:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="lrmethod" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD COLSPAN="1" ALIGN="LEFT">ALELR 服务终端:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="lrendpoint" style="width:450px;"><br/>
                比如: http://localhost:8080/fc-server-1.0.2/services/ALELRService
            </TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;

// define(String name, LRSpec spec)
// update(String name, LRSpec spec)
    case 305:
    case 307:
        valid = false;
%>
<form method="post" action="Result.jsp" target="result">
    <input type="hidden" name="lrmethod" value="<%=method%>">
    <table border="0">
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">阅读器名称:</td>
            <td align="left"><input type="text" name="readerName" size="50"></td>
        </tr>
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">文件路径:</td>
            <td align="left"><input type="text" name="specFilePath" size="50"></td>
        </tr>
    </table>
    <br>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</form>
<%
        break;

// undefine(String name)
// getLRSpec (String name)
    case 306:
    case 309:
        valid = false;
%>
<form method="post" action="Result.jsp" target="result">
    <input type="hidden" name="lrmethod" value="<%=method%>">
    <table border="0">
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">阅读器名称:</td>
            <td align="left"><input type="text" name="readerName" size="50"></td>
        </tr>
    </table>
    <br>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</form>
<%
        break;

// getLogicalReaderNames()
    case 308:
        valid = false;
%>
<form method="post" action="Result.jsp" target="result">
    <input type="hidden" name="lrmethod" value="<%=method%>">
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</form>
<%

        break;

    case 310:
    case 311:
    case 312:
        valid = false;
%>
<form method="post" action="Result.jsp" target="result">
    <input type="hidden" name="lrmethod" value="<%=method%>">
    <table border="0">
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">阅读器名称:</td>
            <td align="left"><input type="text" name="readerName" size="50"></td>
        </tr>
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">文件路径:</td>
            <td align="left"><input type="text" name="filePath" size="50"></td>
        </tr>
    </table>
    <br>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</form>
<%
        break;

    case 313:
        valid = false;
%>
<form method="post" action="Result.jsp" target="result">
    <input type="hidden" name="lrmethod" value="<%=method%>">
    <table border="0">
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">阅读器名称:</td>
            <td align="left"><input type="text" name="readerName" size="50"></td>
        </tr>
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">文件路径:</td>
            <td align="left"><input type="text" name="filePath" size="50"></td>
        </tr>
    </table>
    <br>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</form>
<%
        break;

    case 314:
        valid = false;
%>
<form method="post" action="Result.jsp" target="result">
    <input type="hidden" name="lrmethod" value="<%=method%>">
    <table border="0">
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">阅读器名称:</td>
            <td align="left"><input type="text" name="readerName" size="50"></td>
        </tr>
        <tr>
            <td width="5%"></td>
            <td colspan="2" align="left">属性名称:</td>
            <td align="left"><input type="text" name="propertyName" size="50"></td>
        </tr>
    </table>
    <br>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</form>
<%
        break;

    case 315:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="lrmethod" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 316:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="lrmethod" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 318:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="lrmethod" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;

    case 1111111111:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <TABLE>
        <TR>
            <TD COLSPAN="1" ALIGN="LEFT">URL:</TD>
            <TD ALIGN="left"><INPUT TYPE="TEXT" NAME="url1111111111" SIZE=20></TD>
        </TR>
    </TABLE>
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
        break;
    case 1111111112:
        valid = false;
%>
<FORM METHOD="POST" ACTION="Result.jsp" TARGET="result">
    <INPUT TYPE="HIDDEN" NAME="method" VALUE="<%=method%>">
    <BR>
    <INPUT TYPE="SUBMIT" VALUE="调用">
    <INPUT TYPE="RESET" VALUE="重置">
</FORM>
<%
            break;
    }
    if (valid) {
%>
请在左侧选择您要进行的操作.
<%
        return;
    }
%>

</BODY>
</HTML>