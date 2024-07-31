package space.rogi27.renameit.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

// TODO: Move lore manipulations to another function
public class LoreCommand {

    public static int addLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendMessage(Text.translatable("text.renameit.empty").formatted(Formatting.YELLOW));
                return 0;
            }

            MutableText loreString = TextParserUtils.formatTextSafe(context.getArgument("text", String.class)).copy();
            if (!loreString.getStyle().isItalic()) loreString = loreString.fillStyle(Style.EMPTY.withItalic(false));

            LoreComponent lore = context.getSource().getPlayer().getMainHandStack().getOrDefault(DataComponentTypes.LORE, new LoreComponent(new ArrayList<>()));

            List<Text> loreLines = new ArrayList<>(lore.lines());

            loreLines.add(loreString);

            context.getSource().getPlayer().getMainHandStack().set(DataComponentTypes.LORE, new LoreComponent(loreLines));

            context.getSource().sendMessage(Text.translatable("text.renameit.lore_added", loreString.copy().formatted(Formatting.WHITE)).formatted(Formatting.GREEN));
            return 1;
        } else {
            return 0;
        }
    }

    public static int setLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendMessage(Text.translatable("text.renameit.empty").formatted(Formatting.YELLOW));
                return 0;
            }
            LoreComponent lore = context.getSource().getPlayer().getMainHandStack().get(DataComponentTypes.LORE);

            MutableText loreString = TextParserUtils.formatTextSafe(context.getArgument("text", String.class)).copy();
            if (!loreString.getStyle().isItalic()) loreString = loreString.fillStyle(Style.EMPTY.withItalic(false));

            if (lore == null) {
                context.getSource().sendMessage(Text.translatable("text.renameit.lore_nolore").formatted(Formatting.YELLOW));
                return 0;
            }

            List<Text> loreLines = new ArrayList<>(lore.lines());
            if (loreLines.size() + 1 <= context.getArgument("line", Integer.class)) {
                context.getSource().sendMessage(Text.translatable("text.renameit.lore_noline").formatted(Formatting.RED));
                return 0;
            }

            loreLines.set(context.getArgument("line", Integer.class) - 1, loreString);
            context.getSource().getPlayer().getMainHandStack().set(DataComponentTypes.LORE, new LoreComponent(loreLines));

            context.getSource().sendMessage(Text.translatable("text.renameit.lore_line_changed", context.getArgument("line", Integer.class), loreString.formatted(Formatting.WHITE)).formatted(Formatting.GREEN));
            return 1;
        } else {
            return 0;
        }
    }

    public static int deleteLore(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (context.getSource().getEntity().isPlayer()) {
            if (context.getSource().getPlayer().getMainHandStack().isEmpty()) {
                context.getSource().sendMessage(Text.translatable("text.renameit.empty").formatted(Formatting.YELLOW));
                return 0;
            }
            LoreComponent lore = context.getSource().getPlayer().getMainHandStack().get(DataComponentTypes.LORE);

            if (lore == null) {
                context.getSource().sendMessage(Text.translatable("text.renameit.lore_nolore").formatted(Formatting.YELLOW));
                return 0;
            }

            List<Text> loreLines = new ArrayList<>(lore.lines());
            if (loreLines.size() + 1 <= context.getArgument("line", Integer.class)) {
                context.getSource().sendMessage(Text.translatable("text.renameit.lore_noline").formatted(Formatting.RED));
                return 0;
            }

            loreLines.remove(context.getArgument("line", Integer.class) - 1);
            context.getSource().getPlayer().getMainHandStack().set(DataComponentTypes.LORE, new LoreComponent(loreLines));

            context.getSource().sendMessage(Text.translatable("text.renameit.lore_deleted", context.getArgument("line", Integer.class)).formatted(Formatting.GREEN));
            return 1;
        } else {
            return 0;
        }
    }
}
