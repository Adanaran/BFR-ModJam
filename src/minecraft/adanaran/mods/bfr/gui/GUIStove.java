package adanaran.mods.bfr.gui;

import org.lwjgl.opengl.GL11;

import adanaran.mods.bfr.container.ContainerStove;
import adanaran.mods.bfr.entities.TileEntityStove;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author Adanaran
 * @author Demitreus
 */

public class GUIStove extends GuiContainer {
	
	private TileEntityStove stoveInventory;
	public static final ResourceLocation guiTexture = new ResourceLocation("bfr","gui/container/stove.png");

	public GUIStove(InventoryPlayer invPlayer, TileEntityStove tileEntityStove){
        super(new ContainerStove(invPlayer, tileEntityStove));
        System.out.println("GUI TALKING");
        this.stoveInventory = tileEntityStove;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.func_110434_K().func_110577_a(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1 = 5;
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
		
	}
}
