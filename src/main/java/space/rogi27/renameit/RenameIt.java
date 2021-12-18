package space.rogi27.renameit;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import space.rogi27.renameit.commands.RenameItCommand;

public class RenameIt implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LogManager.getLogger("RenameIt");

    @Override
    public void onInitialize() {
        RenameItCommand.init();
        LOGGER.info("\n\n[RenameIt:DONE] RenameIt is loaded, have fun!\n");
    }
}
