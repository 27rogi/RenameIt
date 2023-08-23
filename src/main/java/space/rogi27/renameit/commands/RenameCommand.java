package space.rogi27.renameit.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
public class RenameCommand {
    public static int setName(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendMessage(Text.translatable("text.renameit.empty").formatted(Formatting.YELLOW));
                return 0;
            }
            String name = context.getArgument("name", String.class);
            if (!(name.length() > 0)) {
                context.getSource().getPlayer().getMainHandStack().removeCustomName();
                return 1;
            }

            MutableText itemNewName = TextParserUtils.formatText(name).copy();
            if (!itemNewName.getStyle().isItalic()) itemNewName = itemNewName.fillStyle(Style.EMPTY.withItalic(false));

            context.getSource().getPlayer().getMainHandStack().setCustomName(itemNewName);
            context.getSource().sendMessage(Text.translatable("text.renameit.rename_changed", itemNewName.copy().formatted(Formatting.WHITE)).formatted(Formatting.GREEN));
            return 1;
        } else {
            return 0;
        }
    }
}
