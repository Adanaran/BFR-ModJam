package adanaran.mods.bfr.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import adanaran.mods.bfr.BFR;
import adanaran.mods.bfr.entities.TileEntityMill;

public class BlockMill extends BlockContainer {

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

	// TODO Texturen!

}
