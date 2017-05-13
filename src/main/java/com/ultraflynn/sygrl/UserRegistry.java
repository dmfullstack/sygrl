package com.ultraflynn.sygrl;

public class UserRegistry {
    private final SSOAuthenticator authenticator;
    private final Repository repository;

    public UserRegistry(SSOAuthenticator authenticator, Repository repository) {
        this.authenticator = authenticator;
        this.repository = repository;
    }

    public User addNewUser() {
        AuthorizationCode authorizationCode = authenticator.requestAuthorizationCode();
        AccessToken accessToken = authenticator.requestAccessToken(authorizationCode);
        User user = authenticator.requestCharacterInfo(accessToken);
        repository.saveUser(user);
        return user;
    }

    public void revalidateAllTokens() {
        repository.getAllUsers().stream()
                .filter(User::hasTokenExpired)
                .map(user -> {
                    AccessToken accessToken = authenticator.revalidateToken(user.getToken());
                    return user.withUpdatedToken(accessToken);
                })
                .forEach(repository::saveUser);
    }
}
