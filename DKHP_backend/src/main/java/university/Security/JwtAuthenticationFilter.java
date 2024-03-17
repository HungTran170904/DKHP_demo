package university.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		System.out.println("Url: "+request.getRequestURL());
		String header=request.getHeader(JwtConfig.header);
		if (request.getRequestURL().indexOf("http://localhost:8080/api/auth")==0) {
            chain.doFilter(request, response);
            return;
        }
		if(header==null||!header.startsWith(JwtConfig.prefix)) 
			throw new AuthenticationCredentialsNotFoundException("Token is invalid!");
		String token=header.substring(JwtConfig.prefix.length(),header.length());
		if(StringUtils.hasText(token)) {
			String id=jwtProvider.getIdFromToken(token);
			CustomUserDetails userDetails=customUserDetailsService.loadById(id);
			 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
	                    userDetails.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		System.out.println("Filter ok");
		chain.doFilter(request, response);
	}

}
