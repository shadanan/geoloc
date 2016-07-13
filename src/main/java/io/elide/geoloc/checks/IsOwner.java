package io.elide.geoloc.checks;

import com.yahoo.elide.security.ChangeSpec;
import com.yahoo.elide.security.RequestScope;
import com.yahoo.elide.security.checks.InlineCheck;
import com.yahoo.elide.security.checks.OperationCheck;

import java.util.Optional;

import io.elide.geoloc.models.User;

public class IsOwner extends OperationCheck<User> {
    @Override
    public boolean ok(User user, RequestScope requestScope, Optional<ChangeSpec> changeSpec) {
        return user.equals(requestScope.getUser().getOpaqueUser());
    }
}
