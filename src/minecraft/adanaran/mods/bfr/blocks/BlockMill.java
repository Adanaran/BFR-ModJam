package adanaran.mods.bfr.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import adanaran.mods.bfr.entities.TileEntityMill;

public class BlockMill extends BlockContainer {

	public BlockMill(int id) {
		super(id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMill();
	}

	// TODO Texturen!

}
