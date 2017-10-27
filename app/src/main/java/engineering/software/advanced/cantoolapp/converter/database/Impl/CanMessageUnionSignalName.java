package engineering.software.advanced.cantoolapp.converter.database.Impl;

import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

/**
 * Created by lhr on 2017/10/27.
 */

public class CanMessageUnionSignalName {
    private String name;
    private String open = "false";
    private Set<SingleSignalName> children;


    public CanMessageUnionSignalName(String name, Set<SingleSignalName> children) {
        this.name = name;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SingleSignalName> getChildren() {
        return children;
    }

    public void setChildren(Set<SingleSignalName> children) {
        this.children = children;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}

class SingleSignalName {
    private String name;

    public SingleSignalName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
