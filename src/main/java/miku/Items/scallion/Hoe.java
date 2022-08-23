package miku.Items.scallion;

import net.minecraft.item.ItemHoe;

public class Hoe extends ItemHoe {
    public Hoe(ToolMaterial material) {
        super(ToolMaterial.GOLD);
        //material.setRepairItem(new ItemStack(MikuLoader.SCALLION));
        this.maxStackSize = 1;
        this.setMaxDamage(50);
        this.canRepair = true;
        this.setTranslationKey("scallion_hoe");
    }
}
