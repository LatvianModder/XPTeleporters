package latmod.xpt;

import ftb.lib.FTBLib;
import ftb.lib.api.config.*;
import latmod.lib.annotations.*;
import net.minecraft.entity.player.EntityPlayer;

import java.io.File;

public class XPTConfig // XPT
{
	public static final ConfigFile configFile = new ConfigFile("xpt");
	
	@NumberBounds(min = 0, max = 200)
	@Info("Levels required to teleport in same dimension")
	public static final ConfigEntryInt levels_for_1000_blocks = new ConfigEntryInt("levels_for_1000_blocks", 20);
	
	@NumberBounds(min = 0, max = 200)
	@Info("Levels required to teleport to another dimension")
	public static final ConfigEntryInt levels_for_crossdim = new ConfigEntryInt("levels_for_crossdim", 30);
	
	@Flags(Flag.SYNC)
	@NumberBounds(min = 1, max = 3600)
	@Info("Teleporter cooldown")
	public static final ConfigEntryInt cooldown_seconds = new ConfigEntryInt("cooldown_seconds", 3);
	
	@Info("Enable crafting recipes")
	public static final ConfigEntryBool enable_crafting = new ConfigEntryBool("enable_crafting", true);
	
	@Info("Only linking will cost levels, teleporting will be free")
	public static final ConfigEntryBool only_linking_uses_xp = new ConfigEntryBool("only_linking_uses_xp", false);
	
	@Flags(Flag.SYNC)
	@Info("Soft blocks are like torches - you can walk trough them, BUT it solved the 'getting stuck in block' issue")
	public static final ConfigEntryBool soft_blocks = new ConfigEntryBool("soft_blocks", true);
	
	@NumberBounds(min = -1, max = 200)
	@Info({"-1 - Disabled", "0+ - Enables Recall Remote crafting and usage"})
	public static final ConfigEntryInt levels_for_recall = new ConfigEntryInt("levels_for_recall", -1);
	
	public static void load()
	{
		configFile.setFile(new File(FTBLib.folderConfig, "/LatMod/XPT.json"));
		configFile.setDisplayName("XPTeleporters");
		configFile.addGroup("general", XPTConfig.class);
		ConfigRegistry.add(configFile);
		configFile.load();
	}
	
	public static boolean canConsumeLevels(EntityPlayer ep, int levels)
	{
		if(levels <= 0 || ep.capabilities.isCreativeMode) return true;
		return ep.experienceLevel >= levels;
	}
	
	public static void consumeLevels(EntityPlayer ep, int levels)
	{
		if(levels <= 0 || ep.capabilities.isCreativeMode) return;
		ep.addExperienceLevel(-levels);
	}
	
	public static int cooldownTicks()
	{ return cooldown_seconds.get() * 20; }
}