package com.ultraflynn.sygrl;

public interface SSOAuthenticator {
    AuthorizationCode requestAuthorizationCode();

    AccessToken requestAccessToken(AuthorizationCode authorizationCode);

    User requestCharacterInfo(AccessToken accessToken);

    AccessToken revalidateToken(AccessToken accessToken);
}
