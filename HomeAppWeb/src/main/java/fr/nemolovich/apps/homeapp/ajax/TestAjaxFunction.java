package fr.nemolovich.apps.homeapp.ajax;

import fr.nemolovich.apps.nemolight.provided.ajax.IAjaxFunction;
import org.json.JSONObject;

/**
 *
 * @author Nemolovich
 */
public class TestAjaxFunction implements IAjaxFunction {

    private static final String FUNC_NAME = "testAjaxFunction";

    @Override
    public JSONObject call(JSONObject param) {
        JSONObject result = new JSONObject();
        result.put("myTest", "testValue");
        return result;
    }

    @Override
    public String getFuncName() {
        return FUNC_NAME;
    }

}
