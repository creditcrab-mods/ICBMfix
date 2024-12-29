package icbm.sentry.terminal.command;

import java.util.ArrayList;
import java.util.List;

import icbm.sentry.ISpecialAccess;
import icbm.sentry.access.AccessLevel;
import icbm.sentry.platform.TTurretPlatform;
import icbm.sentry.terminal.ITerminal;
import icbm.sentry.terminal.TerminalCommand;
import net.minecraft.entity.player.EntityPlayer;

public class CommandDestroy extends TerminalCommand {
    @Override
    public String getCommandPrefix() {
        return "destroy";
    }

    @Override
    public boolean processCommand(
        final EntityPlayer player, final ITerminal terminal, final String[] args
    ) {
        if (!(terminal instanceof TTurretPlatform)) {
            return false;
        }

        final TTurretPlatform turret = (TTurretPlatform) terminal;

        if (args.length > 1) {
            turret.destroyTurret();
            terminal.addToConsole("Destroyed Turret");
            return true;
        }

        turret.destroy(false);
        return true;
    }

    @Override
    public boolean canPlayerUse(final EntityPlayer var1, final ISpecialAccess mm) {
        return mm.getUserAccess(var1.getDisplayName()).ordinal()
            >= AccessLevel.ADMIN.ordinal();
    }

    @Override
    public boolean showOnHelp(final EntityPlayer player, final ISpecialAccess mm) {
        return this.canPlayerUse(player, mm);
    }

    @Override
    public List<String> getCmdUses(final EntityPlayer player, final ISpecialAccess mm) {
        final List<String> cmds = new ArrayList<>();
        cmds.add("destroy");
        cmds.add("destroy turret");
        return cmds;
    }

    @Override
    public boolean canMachineUse(final ISpecialAccess mm) {
        return mm instanceof TTurretPlatform;
    }
}
