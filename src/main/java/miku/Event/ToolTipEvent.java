package miku.Event;

import miku.Items.Miku.MikuItem;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToolTipEvent {
    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof MikuItem) {
            event.getToolTip().set(event.getToolTip().size() - 1, TextFormatting.WHITE + I18n.translateToLocal("attribute.name.generic.attackDamage") + "39");
        }
    }
}
