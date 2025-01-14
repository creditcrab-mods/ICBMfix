package icbm.sentry.terminal.command;

import java.util.ArrayList;
import java.util.List;

import icbm.sentry.ISpecialAccess;
import icbm.sentry.access.AccessLevel;
import icbm.sentry.platform.TileEntityTurretPlatform;
import icbm.sentry.terminal.ITerminal;
import icbm.sentry.terminal.TerminalCommand;
import icbm.sentry.turret.sentries.TAutomaticTurret;
import net.minecraft.entity.player.EntityPlayer;

public class CommandTarget extends TerminalCommand {
    @Override
    public String getCommandPrefix() {
        return "target";
    }

    @Override
    public boolean processCommand(
        final EntityPlayer player, final ITerminal terminal, final String[] args
    ) {
        if (terminal instanceof TileEntityTurretPlatform) {
            final TileEntityTurretPlatform turret = (TileEntityTurretPlatform) terminal;

            if (turret.getTurret(false) instanceof TAutomaticTurret) {
                final TAutomaticTurret sentry
                    = (TAutomaticTurret) turret.getTurret(false);

                if (args.length > 1) {
                    final String obj = args[1];
                    String bool = "";
                    boolean change = false;

                    if (args.length > 2) {
                        bool = args[2];
                        change = Boolean.parseBoolean(bool);
                    }

                    if (obj.equalsIgnoreCase("player")) {
                        if (!bool.isEmpty()) {
                            sentry.targetPlayers = change;
                        } else {
                            sentry.targetPlayers = !sentry.targetPlayers;
                        }

                        return true;
                    }

                    if (obj.equalsIgnoreCase("hostile")) {
                        if (!bool.isEmpty()) {
                            sentry.targetHostile = change;
                        } else {
                            sentry.targetHostile = !sentry.targetHostile;
                        }

                        return true;
                    }

                    if (obj.equalsIgnoreCase("friendly")) {
                        if (!bool.isEmpty()) {
                            sentry.targetFriendly = change;
                        } else {
                            sentry.targetFriendly = !sentry.targetFriendly;
                        }

                        return true;
                    }

                    if (obj.equalsIgnoreCase("air")) {
                        if (!bool.isEmpty()) {
                            sentry.targetAir = change;
                        } else {
                            sentry.targetAir = !sentry.targetAir;
                        }

                        return true;
                    }
                }

                terminal.addToConsole("[player|hostile|friendly|air] [true|false]");
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean canPlayerUse(final EntityPlayer var1, final ISpecialAccess mm) {
        return mm.getUserAccess(var1.getDisplayName()).ordinal()
            >= AccessLevel.ADMIN.ordinal()
            || var1.capabilities.isCreativeMode;
    }

    @Override
    public boolean showOnHelp(final EntityPlayer player, final ISpecialAccess mm) {
        return this.canPlayerUse(player, mm);
    }

    @Override
    public List<String> getCmdUses(final EntityPlayer player, final ISpecialAccess mm) {
        final List<String> cmds = new ArrayList<>();
        cmds.add("target <obj> [bool]");
        return cmds;
    }

    @Override
    public boolean canMachineUse(final ISpecialAccess mm) {
        return mm instanceof TileEntityTurretPlatform
            && ((TileEntityTurretPlatform) mm).getTurret(false) instanceof TAutomaticTurret;
    }
}
