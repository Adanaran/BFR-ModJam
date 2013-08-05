package adanaran.mods.bfr.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import adanaran.mods.bfr.BFR;
import adanaran.mods.bfr.entities.TileEntityMill;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMill extends BlockContainer {

	private static boolean keepMillInventory;

	@SideOnly(Side.CLIENT)
	private Icon millTop;

	public BlockMill(int id) {
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMill();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityPlayer, int meta, float par7, float par8,
			float par9) {
		if (!world.isRemote) {
			entityPlayer.openGui(BFR.instance, 2, world, i, j, k);
		}
		return true;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("bfr:mill_side");
		this.millTop = par1IconRegister.registerIcon("bfr:mill_top");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		return par1 == 1 ? this.millTop : (par1 == 0 ? this.millTop
				: this.blockIcon);
	}

	public static void updateMillBlockState(boolean isActive, World par1World,
			int par2, int par3, int par4) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
		keepMillInventory = true;

		if (isActive) {
			par1World.setBlock(par2, par3, par4, BFR.idBlockMillActive);
		} else {
			par1World.setBlock(par2, par3, par4, BFR.idBlockMill);
		}

		keepMillInventory = false;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, meta, 2);

		if (tileentity != null) {
			tileentity.validate();
			par1World.setBlockTileEntity(par2, par3, par4, tileentity);
		}
	}
}
