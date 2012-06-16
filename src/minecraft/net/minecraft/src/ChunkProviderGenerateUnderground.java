package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ChunkProviderGenerateUnderground implements IChunkProvider
{
    /** RNG. */
    private Random rand;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen1;

	
    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen2;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen3;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen4;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves noiseGen5;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;

    /** Reference to the World object. */
    private World worldObj;

    /** are map structures going to be generated (e.g. strongholds) */
    private final boolean mapFeaturesEnabled;
    private double noiseArray[];
    private double stoneNoise[];
    private MapGenBase caveGenerator;

    /** Holds Stronghold Generator */
    private MapGenStronghold strongholdGenerator;

    /** Holds Village Generator */
    private MapGenVillage villageGenerator;

    /** Holds Mineshaft Generator */
    private MapGenMineshaft mineshaftGenerator;

    /** Holds ravine generator */
    private MapGenBase ravineGenerator;
    
    float field_35388_l[];
    int field_914_i[][];

    public ChunkProviderGenerateUnderground(World par1World, long par2, boolean par4)
    {
        stoneNoise = new double[256];
        caveGenerator = new MapGenCavesUnderground();
        strongholdGenerator = new MapGenStronghold();
        villageGenerator = new MapGenVillage(0);
        mineshaftGenerator = new MapGenMineshaftUnderground();
        ravineGenerator = new MapGenRavine();
        field_914_i = new int[32][32];
        worldObj = par1World;
        mapFeaturesEnabled = par4;
        rand = new Random(par2);
        
        mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);
    }

    /**
     * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
     * temperature is low enough
     */
    public void generateTerrain(int par1, int par2, byte par3ArrayOfByte[])
    {
	int i = 0;
       for (int j = 0; j < 16; j++)
        {
            for (int k = 0; k < 16; k++)
            {
                for (int l = 0; l < 256; l++)
                {
				if ( l == 0 | l == 255)
				par3ArrayOfByte[i] = 7;
				else
				par3ArrayOfByte[i] = 1;
				i++;
				}
			}
		}	
    }

 
    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2)
    {
        return provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int par1, int par2)
    {
        rand.setSeed((long)par1 * 0x4f9939f508L + (long)par2 * 0x1ef1565bd5L);
        byte abyte0[] = new byte[16*16*256];
        generateTerrain(par1, par2, abyte0);
        caveGenerator.generate(this, worldObj, par1, par2, abyte0);
        ravineGenerator.generate(this, worldObj, par1, par2, abyte0);

        if (mapFeaturesEnabled)
        {
            mineshaftGenerator.generate(this, worldObj, par1, par2, abyte0);
            villageGenerator.generate(this, worldObj, par1, par2, abyte0);
            strongholdGenerator.generate(this, worldObj, par1, par2, abyte0);
        }

        Chunk chunk = new Chunk(worldObj, abyte0, par1, par2);
  
        chunk.generateSkylightMap();
        return chunk;
    }

 
    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockSand.fallInstantly = true;
        int i = par2 * 16;
        int j = par3 * 16;
        rand.setSeed(worldObj.getSeed());
        long l = (rand.nextLong() / 2L) * 2L + 1L;
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        rand.setSeed((long)par2 * l + (long)par3 * l1 ^ worldObj.getSeed());
        boolean flag = false;

        if (mapFeaturesEnabled)
        {
            mineshaftGenerator.generateStructuresInChunk(worldObj, rand, par2, par3);
            flag = villageGenerator.generateStructuresInChunk(worldObj, rand, par2, par3);
            strongholdGenerator.generateStructuresInChunk(worldObj, rand, par2, par3);
        }

        if (!flag && rand.nextInt(4) == 0)
        {
            int k = i + rand.nextInt(16) + 8;
            int i2 = rand.nextInt(256);
            int i3 = j + rand.nextInt(16) + 8;
            (new WorldGenLakes(Block.waterStill.blockID)).generate(worldObj, rand, k, i2, i3);
        }

        if (!flag && rand.nextInt(8) == 0)
        {
            int i1 = i + rand.nextInt(16) + 8;
            int j2 = rand.nextInt(rand.nextInt(120) + 8);
            int j3 = j + rand.nextInt(16) + 8;

            if (j2 < 63 || rand.nextInt(10) == 0)
            {
                (new WorldGenLakes(Block.lavaStill.blockID)).generate(worldObj, rand, i1, j2, j3);
            }
        }

        for (int j1 = 0; j1 < 32; j1++)
        {
            int k2 = i + rand.nextInt(16) + 8;
            int k3 = rand.nextInt(256);
            int i4 = j + rand.nextInt(16) + 8;

            if (!(new WorldGenDungeons()).generate(worldObj, rand, k2, k3, i4));
        }

        
       
        i += 8;
        j += 8;

        for (int k1 = 0; k1 < 16; k1++)
        {
            for (int l2 = 0; l2 < 16; l2++)
            {
                int l3 = worldObj.getPrecipitationHeight(i + k1, j + l2);

                if (worldObj.isBlockHydratedDirectly(k1 + i, l3 - 1, l2 + j))
                {
                    worldObj.setBlockWithNotify(k1 + i, l3 - 1, l2 + j, Block.ice.blockID);
                }

                if (worldObj.canSnowAt(k1 + i, l3, l2 + j))
                {
                    worldObj.setBlockWithNotify(k1 + i, l3, l2 + j, Block.snow.blockID);
                }
            }
        }

        BlockSand.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public boolean unload100OldestChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(par2, par4);

        if (biomegenbase == null)
        {
            return null;
        }
        else
        {
            return biomegenbase.getSpawnableList(par1EnumCreatureType);
        }
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        if ("Stronghold".equals(par2Str) && strongholdGenerator != null)
        {
            return strongholdGenerator.getNearestInstance(par1World, par3, par4, par5);
        }
        else
        {
            return null;
        }
    }
}
