package icbm.contraption;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.item.ItemStack;

public class SetSignalDisrupterFrequencyPacketHandler
    implements IMessageHandler<SetSignalDisrupterFrequencyPacket, IMessage> {
    @Override
    public IMessage
    onMessage(SetSignalDisrupterFrequencyPacket message, MessageContext ctx) {
        ItemStack handStack
            = ctx.getServerHandler().playerEntity.getCurrentEquippedItem();

        if (handStack.getItem() instanceof ItemSignalDisruptor) {
            ((ItemSignalDisruptor) handStack.getItem())
                .setFrequency(message.freq, handStack);
        }

        return null;
    }
}
