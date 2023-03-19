package miku.World.MikuWorld;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;

public class MikuTeleporter extends Teleporter {

    public MikuTeleporter(WorldServer worldIn) {
        super(worldIn);
    }

    @Override
    public void placeInPortal(@Nonnull Entity entityIn, float rotationYaw) {
    }

    @Override
    public boolean placeInExistingPortal(@Nonnull Entity entityIn, float rotationYaw) {
        return true;
    }
}
