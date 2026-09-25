package com.ca.charteredAccountant.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 * Reads "Authorization: Bearer &lt;jwt&gt;" and, when valid, puts a {@link LoggedInUserDetails}
 * principal into the security context. Invalid tokens are ignored here; the entry point
 * answers 401 if the path needs authentication.
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader(Constants.AUTH_HEADER);

		if (header != null && header.startsWith(Constants.BEARER_PREFIX)
				&& SecurityContextHolder.getContext().getAuthentication() == null) {

			LoggedInUserDetails user = jwtTokenProvider.parseToken(header.substring(Constants.BEARER_PREFIX.length()));

			if (user != null && user.getRole() != null) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						List.of(new SimpleGrantedAuthority(Constants.ROLE_PREFIX + user.getRole().name())));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}

}
