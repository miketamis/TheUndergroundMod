package net.minecraft.src;

import java.util.Random;

public class MapGenMineshaftUnderground extends MapGenMineshaft
{
    public MapGenMineshaftUnderground()
    {
    }

    protected boolean canSpawnStructureAtCoords(int par1, int par2)
    {
        return rand.nextInt(50) == 0;
    }

    protected StructureStart getStructureStart(int par1, int par2)
    {
        return new StructureMineshaftStart(worldObj, rand, par1, par2);
    }
}
