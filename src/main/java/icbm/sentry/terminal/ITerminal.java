package icbm.sentry.terminal;

import java.util.List;

import icbm.sentry.ISpecialAccess;
import icbm.sentry.gui.IScroll;

public interface ITerminal extends ISpecialAccess, IScroll {
    List<String> getTerminalOuput();

    boolean addToConsole(final String p0);
}
