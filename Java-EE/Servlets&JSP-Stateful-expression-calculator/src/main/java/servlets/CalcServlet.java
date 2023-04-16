package servlets;

import util.Evaluator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@WebServlet("/calc/*")
public class CalcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        StringBuilder exp = new StringBuilder(((String) session.getAttribute("expression"))
                .replaceAll(" ", ""));
        for(int i = 0; i < exp.length(); i++) {
            while(Character.isAlphabetic(exp.charAt(i))) {
                String value = (String) session.getAttribute(String.valueOf(exp.charAt(i)));
                if (value == null) {
                    resp.sendError(HttpServletResponse.SC_CONFLICT, "Variable " + exp.charAt(i) + " has no value");
                    return;
                }
                exp.replace(i, i + 1, value);
            }
        }
        PrintWriter writer = resp.getWriter();
        writer.write(Evaluator.evaluate(exp.toString(), false));
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String resource = req.getRequestURI().substring(6);

        if (resource.equals("expression")) {
            putExpression(req, resp, session, resource);
        } else if (resource.length() == 1) {
            putVariable(req, resp, session, resource);
        }
    }

    private void putExpression(HttpServletRequest request, HttpServletResponse response, HttpSession session, String resource) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream());
        String expression = scanner.nextLine();
        if (expression == null || expression.length() == 0
                || expression.replaceAll("[+*/-]","").length()==expression.length())
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect expression");
        else {
            response.setStatus(session.getAttribute(resource) == null ?
                    HttpServletResponse.SC_CREATED : HttpServletResponse.SC_OK);
            session.setAttribute(resource, expression);
        }
    }

    private void putVariable(HttpServletRequest request, HttpServletResponse response, HttpSession session, String resource) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream());
        String paramValue = scanner.next();
        if (paramValue.matches("-?[0-9]+")) {
            int num = Integer.parseInt(paramValue);
            if (num > 10000 || num < -10000) {
                response.sendError(403, "Values should be in [-10000,10000] range");
            } else {
                response.setStatus(session.getAttribute(resource) == null ? 201 : 200);
                session.setAttribute(resource, paramValue);
            }
        } else if (session.getAttribute(paramValue) != null) {
            response.setStatus(session.getAttribute(resource) == null ? 201 : 200);
            session.setAttribute(resource, paramValue);
        } else {
            response.sendError(400, "Incorrect value/reference for variable");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String resource = req.getRequestURI().substring(6);
        if (session.getAttribute(resource) == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Resource does not exist");
        } else {
            session.setAttribute(resource, null);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
