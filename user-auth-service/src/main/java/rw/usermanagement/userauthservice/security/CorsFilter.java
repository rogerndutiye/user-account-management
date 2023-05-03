package rw.usermanagement.userauthservice.security;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(request, response);
    }
}
