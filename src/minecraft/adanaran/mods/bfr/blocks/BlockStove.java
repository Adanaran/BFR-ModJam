package adanaran.mods.bfr.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import adanaran.mods.bfr.BFR;
import adanaran.mods.bfr.entities.TileEntityStove;

/**
 * 
 * @author Adanaran
 */
public class BlockStove extends BlockContainer {
	
	
    /**
     * This flag is used to prevent the stove inventory to be dropped upon block removal, is used internally when the
     * stove block changes from idle to active and vice-versa.
     */
    private static boolean keepStoveInventory;
    
    @SideOnly(Side.CLIENT)
	private Icon stoveIconFront;
    @SideOnly(Side.CLIENT)
	private Icon stoveIconTop;
	private final boolean isActive;

	public BlockStove(int id, boolean active) {
		super(id, Material.rock);
		isActive = active;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityStove();
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("bfr:stove_side");
		this.stoveIconFront = par1IconRegister.registerIcon(this.isActive ? "bfr:stove_front_on" : "bfr:stove_front_off");
        this.stoveIconTop = par1IconRegister.registerIcon(this.isActive ?  "bfr:stove_top_on" : "bfr:stove_top_off");
	}
	
    @SideOnly(Side.CLIENT)
    
	@Override
	public Icon getIcon(int par1, int par2) {
		return par1 == 1 ? this.stoveIconTop : (par1 == 0 ? this.stoveIconTop : (par1 != par2 ? this.blockIcon : this.stoveIconFront));
	    
	}
    
	  @Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
	        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	        if (l == 0)
	        {
	            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
	        }

	        if (l == 1)
	        {
	            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
	        }

	        if (l == 2)
	        {
	            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
	        }

	        if (l == 3)
	        {
	            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
	        }
	}

	/**
     * set a blocks direction
     */
    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
            {
                b0 = 3;
            }

            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
            {
                b0 = 2;
            }

            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
            {
                b0 = 5;
            }

            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
            {
                b0 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }
    
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		par5EntityPlayer.openGui(BFR.instance, 1, par1World, par2, par3, par4);
		return true;
	}


	
	
}
