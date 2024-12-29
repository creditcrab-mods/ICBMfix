package icbm.sentry;

import java.util.List;

import icbm.sentry.access.AccessLevel;
import icbm.sentry.access.UserAccess;

public interface ISpecialAccess {
    AccessLevel getUserAccess(final String p0);

    List<UserAccess> getUsers();

    boolean addUserAccess(final String p0, final AccessLevel p1, final boolean p2);

    boolean removeUserAccess(final String p0);

    List<UserAccess> getUsersWithAcess(final AccessLevel p0);
}
