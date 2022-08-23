package miku.Items.scallion;

import net.minecraft.item.ItemSword;

public class Sword extends ItemSword {
    public Sword(ToolMaterial material) {
        super(material);
        //material.setRepairItem(new ItemStack(MikuLoader.SCALLION));
        this.maxStackSize = 1;
        this.setMaxDamage(50);
        this.canRepair = true;
    }

    @Override
    public float getAttackDamage() {
        return 6.0F;
    }

}
