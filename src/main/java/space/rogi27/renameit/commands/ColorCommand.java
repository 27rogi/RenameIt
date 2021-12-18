package space.rogi27.renameit.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ColorCommand {
    public static int setColor(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendFeedback(new TranslatableText("text.renameit.color_empty").formatted(Formatting.YELLOW), false);
                return 0;
            }

            int color = context.getArgument("color", Integer.class);
            NbtCompound itemNbt = context.getSource().getPlayer().getMainHandStack().getOrCreateSubNbt("display");

            if (color == 0) {
                itemNbt.remove("color");
            } else {
                itemNbt.putInt("color", color);
            }

            context.getSource().sendFeedback(new TranslatableText("text.renameit.color_changed", new LiteralText(String.valueOf(color)).formatted(Formatting.WHITE)).formatted(Formatting.GREEN), false);
            return 1;
        } else {
            return 0;
        }
    }
}
