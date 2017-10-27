package engineering.software.advanced.cantoolapp.converter.database.Impl;

import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

/**
 * Created by lhr on 2017/10/26.
 */

public class CanMessageUnionSignal {
    private CanMessage canMessage;
    private Set<CanSignal> canSignals;

    public CanMessageUnionSignal() {

    }

    public CanMessageUnionSignal(CanMessage canMessage, Set<CanSignal> canSignals) {
        this.canMessage = canMessage;
        this.canSignals = canSignals;
    }

    public CanMessage getCanMessage() {
        return canMessage;
    }

    public void setCanMessage(CanMessage canMessage) {
        this.canMessage = canMessage;
    }

    public Set<CanSignal> getCanSignals() {
        return canSignals;
    }

    public void setCanSignals(Set<CanSignal> canSignals) {
        this.canSignals = canSignals;
    }
}
