package net.minecraft.src;

import java.util.Random;

public class MapGenCavesUnderground extends MapGenBase
{
    public MapGenCavesUnderground()
    {
    }

    /**
     * Generates a larger initial cave node than usual. Called 25% of the time.
     */
    protected void generateLargeCaveNode(long par1, int par3, int par4, byte par5ArrayOfByte[], double par6, double par8, double par10)
    {
		
        generateCaveNode(par1, par3, par4, par5ArrayOfByte, par6, par8, par10, 1.0F + rand.nextFloat() * 8F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

    /**
     * Generates a node in the current cave system recursion tree.
     */
    protected void generateCaveNode(long par1, int par3, int par4, byte par5ArrayOfByte[], double par6, double par8, double par10, float par12, float par13, float par14, int par15, int par16, double par17)
    {
        double d = par3 * 16 + 8;
        double d1 = par4 * 16 + 8;
        float f = 0.0F;
        float f1 = 0.0F;
        Random random = new Random(par1);

        if (par16 <= 0)
        {
            int i = range * 16 - 16;
            par16 = i - random.nextInt(i / 4);
        }

        boolean flag = false;

        if (par15 == -1)
        {
            par15 = par16 / 2;
            flag = true;
        }

        int j = random.nextInt(par16 / 2) + par16 / 4;
        boolean flag1 = random.nextInt(6) == 0;

        for (; par15 < par16; par15++)
        {
            double d2 = 1.5D + (double)(MathHelper.sin(((float)par15 * (float)Math.PI) / (float)par16) * par12 * 1.0F);
            double d3 = d2 * par17;
            float f2 = MathHelper.cos(par14);
            float f3 = MathHelper.sin(par14);
            par6 += MathHelper.cos(par13) * f2;
            par8 += f3;
            par10 += MathHelper.sin(par13) * f2;

            if (flag1)
            {
                par14 *= 0.92F;
            }
            else
            {
                par14 *= 0.7F;
            }

            par14 += f1 * 0.1F;
            par13 += f * 0.1F;
            f1 *= 0.9F;
            f *= 0.75F;
            f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4F;

            if (!flag && par15 == j && par12 > 1.0F && par16 > 0)
            {
                generateCaveNode(random.nextLong(), par3, par4, par5ArrayOfByte, par6, par8, par10, random.nextFloat() * 0.5F + 0.5F, par13 - ((float)Math.PI / 2F), par14 / 3F, par15, par16, 1.0D);
                generateCaveNode(random.nextLong(), par3, par4, par5ArrayOfByte, par6, par8, par10, random.nextFloat() * 0.5F + 0.5F, par13 + ((float)Math.PI / 2F), par14 / 3F, par15, par16, 1.0D);
                return;
            }

            if (!flag && random.nextInt(4) == 0)
            {
                continue;
            }

            double d4 = par6 - d;
            double d5 = par10 - d1;
            double d6 = par16 - par15;
            double d7 = par12 + 2.0F + 16F;

            if ((d4 * d4 + d5 * d5) - d6 * d6 > d7 * d7)
            {
                return;
            }

            if (par6 < d - 16D - d2 * 2D || par10 < d1 - 16D - d2 * 2D || par6 > d + 16D + d2 * 2D || par10 > d1 + 16D + d2 * 2D)
            {
                continue;
            }

            d4 = MathHelper.floor_double(par6 - d2) - par3 * 16 - 1;
            int k = (MathHelper.floor_double(par6 + d2) - par3 * 16) + 1;
            d5 = MathHelper.floor_double(par8 - d3) - 1;
            int l = MathHelper.floor_double(par8 + d3) + 1;
            d6 = MathHelper.floor_double(par10 - d2) - par4 * 16 - 1;
            int i1 = (MathHelper.floor_double(par10 + d2) - par4 * 16) + 1;

            if (d4 < 0)
            {
                d4 = 0;
            }

            if (k > 16)
            {
                k = 16;
            }

            if (d5 < 1)
            {
                d5 = 1;
            }

            if (l > 120)
            {
                l = 120;
            }

            if (d6 < 0)
            {
                d6 = 0;
            }

            if (i1 > 16)
            {
                i1 = 16;
            }

            boolean flag2 = false;

            for (int j1 = (int) d4; !flag2 && j1 < k; j1++)
            {
                for (int l1 = (int) d6; !flag2 && l1 < i1; l1++)
                {
                    for (int i2 = l + 1; !flag2 && i2 >= d5 - 1; i2--)
                    {
                        int j2 = (j1 * 16 + l1) * 128 + i2;

                        if (i2 < 0 || i2 >= 128)
                        {
                            continue;
                        }

                        if (par5ArrayOfByte[j2] == Block.waterMoving.blockID || par5ArrayOfByte[j2] == Block.waterStill.blockID)
                        {
                            flag2 = true;
                        }

                        if (i2 != d5 - 1 && j1 != d4 && j1 != k - 1 && l1 != d6 && l1 != i1 - 1)
                        {
                            i2 = (int) d5;
                        }
                    }
                }
            }

            if (flag2)
            {
                continue;
            }

            for (int k1 = (int) d4; k1 < k; k1++)
            {
                double d8 = (((double)(k1 + par3 * 16) + 0.5D) - par6) / d2;
                label0:

                for (int k2 = (int) d6; k2 < i1; k2++)
                {
                    double d9 = (((double)(k2 + par4 * 16) + 0.5D) - par10) / d2;
                    int l2 = (k1 * 16 + k2) * 128 + l;
                    boolean flag3 = false;

                    if (d8 * d8 + d9 * d9 >= 1.0D)
                    {
                        continue;
                    }

                    int i3 = l - 1;

                    do
                    {
                        if (i3 < d5)
                        {
                            continue label0;
                        }

                        double d10 = (((double)i3 + 0.5D) - par8) / d3;

                        if (d10 > -0.69999999999999996D && d8 * d8 + d10 * d10 + d9 * d9 < 1.0D)
                        {
                            byte byte0 = par5ArrayOfByte[l2];

                            if (byte0 == Block.grass.blockID)
                            {
                                flag3 = true;
                            }

                            if (byte0 == Block.stone.blockID || byte0 == Block.dirt.blockID || byte0 == Block.grass.blockID)
                            {
                                if (i3 < 10)
                                {
                                    par5ArrayOfByte[l2] = (byte)Block.lavaMoving.blockID;
                                }
                                else
                                {
                                    par5ArrayOfByte[l2] = 0;

                                    if (flag3 && par5ArrayOfByte[l2 - 1] == Block.dirt.blockID)
                                    {
                                        par5ArrayOfByte[l2 - 1] = worldObj.getBiomeGenForCoords(k1 + par3 * 16, k2 + par4 * 16).topBlock;
                                    }
                                }
                            }
                        }

                        l2--;
                        i3--;
                    }
                    while (true);
                }
            }

            if (flag)
            {
                break;
            }
        }
    }

    /**
     * Recursively called by generate() (generate) and optionally by itself.
     */
    protected void recursiveGenerate(World par1World, int par2, int par3, int par4, int par5, byte par6ArrayOfByte[])
    {
        int i = rand.nextInt(rand.nextInt(rand.nextInt(60) + 1) + 1);

        if (rand.nextInt(30) != 0)
        {
            i = 0;
        }

        for (int j = 0; j < i; j++)
        {
            double d = par2 * 16 + rand.nextInt(16);
            double d1 = rand.nextInt(rand.nextInt(120) + 8);
            double d2 = par3 * 16 + rand.nextInt(16);
            int k = 1;

            if (rand.nextInt(4) == 0)
            {
                generateLargeCaveNode(rand.nextLong(), par4, par5, par6ArrayOfByte, d, d1, d2);
                k += rand.nextInt(4);
            }

            for (int l = 0; l < k; l++)
            {
                float f = rand.nextFloat() * (float)Math.PI * 4.0F;
                float f1 = ((rand.nextFloat() - 0.5F) * 2.0F) / 4F;
                float f2 = rand.nextFloat() * 4.0F + rand.nextFloat();

                if (rand.nextInt(10) == 0)
                {
                    f2 *= rand.nextFloat() * rand.nextFloat() * 4F + 2.0F;
                }

                generateCaveNode(rand.nextLong(), par4, par5, par6ArrayOfByte, d, d1, d2, f2, f, f1, 0, 0, 1.0D);
            }
        }
    }
}
