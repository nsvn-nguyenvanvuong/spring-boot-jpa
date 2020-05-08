package com.nittsu.kinjirou.identity.security.auth;

import java.util.Collection;

import com.nittsu.kinjirou.identity.security.model.UserAuthentication;
import com.nittsu.kinjirou.identity.security.model.token.RawAccessJwtToken;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * An {@link org.springframework.security.core.Authentication} implementation
 * that is designed for simple presentation of JwtToken.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 2877954820905567501L;

    private final String TOKEN_UNTRUSTED = "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead";

    private UserAuthentication userContext;
    private RawAccessJwtToken rawAccessToken;

    public JwtAuthenticationToken(final RawAccessJwtToken unsafeToken) {
        super(null);

        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(final UserAuthentication userContext, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        
        this.eraseCredentials();
        this.userContext = userContext;

        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(TOKEN_UNTRUSTED);
        }

        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userContext;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
