package latmod.xpt.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockLM;
import ftb.lib.api.item.ODItems;
import latmod.xpt.XPT;
import latmod.xpt.XPTConfig;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTeleporter extends BlockLM
{
	public BlockTeleporter(String s)
	{
		super(s, Material.iron);
		setBlockBounds(0F, 0F, 0F, 1F, 1F / 8F, 1F);
		setHardness(1F);
		setResistance(100000F);
		setCreativeTab(CreativeTabs.tabTransport);
		textureName = "teleporter";
	}
	
	@Override
	public LMMod getMod()
	{ return XPT.mod; }
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta)
	{ return true; }
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileTeleporter(); }
	
	@Override
	public void onPostLoaded()
	{
		XPT.mod.addTile(TileTeleporter.class, "teleporter");
	}
	
	@Override
	public void loadRecipes()
	{
		XPT.mod.recipes.addRecipe(new ItemStack(this), "IEI", "IPI", 'E', "blockEmerald", 'I', ODItems.IRON, 'P', Items.ender_pearl);
	}
	
	@Override
	public void setBlockBoundsForItemRender() { }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) { }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z)
	{
		if(XPTConfig.soft_blocks.getAsBoolean()) return null;
		setBlockBoundsBasedOnState(w, x, y, z);
		return super.getCollisionBoundingBoxFromPool(w, x, y, z);
	}
	
	@Override
	public boolean isOpaqueCube()
	{ return false; }
	
	@Override
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{ return !(entity instanceof EntityDragon || entity instanceof EntityWither); }
	
	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity e)
	{
		if(e != null && !e.isDead && e instanceof EntityPlayerMP)
		{
			TileTeleporter t = (TileTeleporter) w.getTileEntity(x, y, z);
			if(t != null) t.onPlayerCollided((EntityPlayerMP) e);
		}
	}
}