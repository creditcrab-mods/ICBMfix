package icbm.gangshao.terminal.command;

import java.util.ArrayList;
import java.util.List;

import icbm.gangshao.ISpecialAccess;
import icbm.gangshao.access.AccessLevel;
import icbm.gangshao.terminal.ITerminal;
import icbm.gangshao.terminal.TerminalCommand;
import net.minecraft.entity.player.EntityPlayer;

public class CommandUser extends TerminalCommand {
    @Override
    public String getCommandPrefix() {
        return "users";
    }

    @Override
    public boolean processCommand(
        final EntityPlayer player, final ITerminal terminal, final String[] args
    ) {
        if (!args[0].equalsIgnoreCase("users") || args.length <= 1 || args[1] == null
            || !(terminal instanceof ISpecialAccess)) {
            return false;
        }

        final ISpecialAccess turret = terminal;

        if (args[1].equalsIgnoreCase("List")) {
            terminal.addToConsole("");
            terminal.addToConsole("Listing Users");

            for (int i = 0; i < turret.getUsers().size(); ++i) {
                terminal.addToConsole(" " + i + ") " + turret.getUsers().get(i).username);
            }

            return true;
        }

        if (args[1].equalsIgnoreCase("remove") && args.length > 2) {
            if (args[2] == null) {
                terminal.addToConsole("Invalid username.");
                return true;
            }

            if (turret.removeUserAccess(args[2])) {
                terminal.addToConsole("Removed: " + args[2]);
                return true;
            }

            terminal.addToConsole(" User not found.");
            return true;
        } else {
            if (!args[1].equalsIgnoreCase("add") || args.length <= 2) {
                return false;
            }

            if (args[2] == null) {
                terminal.addToConsole("Invalid username.");
                return true;
            }

            if (turret.addUserAccess(args[2], AccessLevel.USER, true)) {
                terminal.addToConsole("Added: " + args[2]);
                return true;
            }

            terminal.addToConsole("User already exists.");
            return true;
        }
    }

    @Override
    public boolean canPlayerUse(final EntityPlayer var1, final ISpecialAccess mm) {
        return mm.getUsers().size() <= 0
            || mm.getUserAccess(var1.getDisplayName()).ordinal()
            >= AccessLevel.ADMIN.ordinal();
    }

    @Override
    public boolean showOnHelp(final EntityPlayer player, final ISpecialAccess mm) {
        return this.canPlayerUse(player, mm);
    }

    @Override
    public List<String> getCmdUses(final EntityPlayer player, final ISpecialAccess mm) {
        final List<String> cmds = new ArrayList<>();
        cmds.add("users list");
        cmds.add("users add [player]");
        cmds.add("users remove [player]");
        return cmds;
    }

    @Override
    public boolean canMachineUse(final ISpecialAccess mm) {
        return mm instanceof ISpecialAccess;
    }
}
