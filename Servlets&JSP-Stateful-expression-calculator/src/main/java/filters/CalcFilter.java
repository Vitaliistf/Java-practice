package filters;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/calc/*")
public class CalcFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getInputStream()!=null)
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}