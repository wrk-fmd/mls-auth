package at.wrk.fmd.mls.auth.endpoint;

import at.wrk.fmd.mls.auth.entity.Concern;
import at.wrk.fmd.mls.auth.exception.NotFoundException;

/**
 * This class provides helper methods to check if a resource could be resolved by its id
 * Each method throws a {@link NotFoundException} with an appropriate message if the resolved param is null
 */
class ParamValidator {

    public static void validate(Concern param) {
        validate(param, "The concern does not exist");
    }

    private static void validate(Object param, String message) {
        if (param == null) {
            throw new NotFoundException(message);
        }
    }
}