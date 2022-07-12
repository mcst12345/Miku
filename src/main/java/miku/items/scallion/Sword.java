package miku.items.scallion;

import net.minecraft.item.ItemSword;

public class Sword extends ItemSword {
    public Sword(ToolMaterial material) {
        super(material);
        this.maxStackSize = 1;
        this.setMaxDamage(6);
        this.canRepair = true;
    }

    @Override
    public float getAttackDamage() {
        return 6.0F;
    }

}
