package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.EditCatDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.CatContainsKeywordsPredicate;
import seedu.address.testutil.CatBuilder;
import seedu.address.testutil.CatUtil;
import seedu.address.testutil.EditCatDescriptorBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Cat cat = new CatBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(CatUtil.getAddCommand(cat));
        assertEquals(new AddCommand(cat), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Cat cat = new CatBuilder().build();
        EditCatDescriptor descriptor = new EditCatDescriptorBuilder(cat).build();
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + CatUtil.getEditCatDescriptorDetails(descriptor));
        assertEquals(new UpdateCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_update_caseInsensitive() throws Exception {
        Cat cat = new CatBuilder().build();
        EditCatDescriptor descriptor = new EditCatDescriptorBuilder(cat).build();
        String args = INDEX_FIRST_PERSON.getOneBased() + " " + CatUtil.getEditCatDescriptorDetails(descriptor);
        UpdateCommand expected = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);
        assertEquals(expected, parser.parseCommand("UPDATE " + args));
        assertEquals(expected, parser.parseCommand("UpDaTe " + args));
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand("EXIT") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        // Define keywords for different fields
        List<String> nameKeywords = Arrays.asList("foo");
        List<String> locationKeywords = Collections.singletonList("baz");
        List<String> traitKeywords = Collections.singletonList("bar");

        // Create the expected predicate with your 4-list constructor
        CatContainsKeywordsPredicate predicate = new CatContainsKeywordsPredicate(
                nameKeywords, locationKeywords, traitKeywords, Collections.emptyList());

        // The command string must now include the prefixes n/ and l/
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/foo l/baz t/bar");

        assertEquals(new FindCommand(predicate), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand("HELP") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
