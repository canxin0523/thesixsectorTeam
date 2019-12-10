package city.thesixsectorteam.wheelworld.common.domain;

import java.util.HashMap;

public class SixsectorResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public SixsectorResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public SixsectorResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public SixsectorResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
