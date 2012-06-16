package net.minecraft.src;

public class WorldTypeUnderground extends WorldType 
{

    public WorldTypeUnderground(int par1, String par2Str)
    {
        super(par1, par2Str);
    }
	  public String getTranslateName()
    {
	return "underground";
	}
	
	 public WorldChunkManager getChunkManager(World world)
    {
        
            return new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F, 0.5F);
        
	}
	 public int getSeaLevel(World world)
    {
        return 0;
    }
	public boolean hasVoidParticles(boolean flag)
    {
	return false;
	}
	
	 public IChunkProvider getChunkGenerator(World world)
    {
        
            return new ChunkProviderGenerateUnderground(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
        
    }
}