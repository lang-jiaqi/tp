---
layout: page
title: User Guide
---
![banner](images/CatPals_banner.png)
This project is a **brownfield** software engineering project developed by a team of five,
based on [AddressBook Level 3 (AB3)](https://se-education.org/addressbook-level3/),
a sample desktop application provided by CS2103T course team, AY2526 Sem2.
In the sample project, AB3 simulates an ongoing software project with an existing object-oriented codebase, documentation, and architecture, making it a suitable foundation for extension rather than building a product from scratch.
<br>
<br>
Building on this base, our team adapted the original contact-management application into a desktop app for NUS Cate Cafe CCA volunteers to manage information about stray cats on the NUS campus. The app is designed for users who prefer fast keyboard-based interaction, are comfortable with CLI-style workflows, and need an efficient way to identify cats and keep important status details up to date.
<br>
<br>
It is intended for personal or small-team volunteer use, rather than as a veterinary medical system, shelter operations tool, or public registry.

For the detailed documentation of this project, see the **[Cat Pals Website](https://ay2526s2-cs2103t-t16-3.github.io/tp/)**.

## Table of Contents
- [Quick start](#quick-start)
- [Features](#features)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).
3. Copy the file to the folder you want to use as the _home folder_ for your CatPals.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar catpals.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all cats.
   * `add n/Bowie t/Orange l/Utown h/Vaccinated` : Adds a cat named `Bowie` to the CatPals.
   * `delete 1` or `delete Bowie`: Deletes the 1st cat (Bowie) shown in the current list.
   * `find Bowie`: Searches for a cat named `Bowie`.
   * `clear` : Deletes all contacts.
   * `exit` : Exits the app.
6. Refer to the [Features](#features) below for details of each command.

---

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Snowy`.
* Items in curly brackets are optional.<br>
  e.g `n/NAME t/TAG {h/HEALTH_STATUS}` can be used as `n/Snowy t/white h/vaccinated` or as `n/Snowy t/white`.
* Items with `…` after them can be used multiple times (>= 1).<br>
  e.g. `[t/TAG]…` can be used as `t/white`, `t/white t/small` etc.
* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:

* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds cats whose names contain any of the given keywords.

Format: `find [CAT_NAME]`

* The search is case-sensitive. e.g `snowy` will NOT match `Snowy`
* The order of the keywords does not matter. e.g. `Snowy White` will match `White Snowy`
* Partial words will be matched e.g. `Han` will match `Hans`
* Cats matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* Names cannot be empty
* Names cannot contain symbols

Examples:

* `find Snowy` returns `Snowy` and `Snowy white`
* `find Alex Li` returns `Alex`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)


### Finding cats by their traits: `findtrait`
Finds cats whose traits contain any of the given keywords.

Format: `findtrait TRAIT`

* The search is case-sensitive. e.g `orange` will NOT match `Orange`
* The order of the keywords does not matter. e.g. `orange small` will match `small orange` and `orange small`

Examples:

* `findtrait Orange` returns all cats with the trait `Orange`
* `findtrait Orange Small` returns all cats with the trait `Orange` or `Small`

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:

* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command summary


| Command                           | Format                                                                         | Examples                                    |
|-----------------------------------|--------------------------------------------------------------------------------|---------------------------------------------|
| **Add** a cat                     | `n/[NAME] t/[TRAIT]... l/[LOCATION] {h/[HEALTH_STATUS]}`                       | `add n/Bowie t/Orange l/Utown h/Vaccinated` |
| **Delete** a cat by name or index | `delete [CAT_NAME]`or `delete [CAT_NUMBER]`                                    | `delete Snowy` or `delete 3`                |
| **Edit** a cat by name or index   | `Update [CAT_NAME] [UPDATED_STATUS]` or `Update [CAT_NUMBER] [UPDATED_STATUS]` | `Update Snowy l/utown` or `Update 3 l/PGPR` |
| **Find** a cat name               | `find [CAT_NAME]`                                                              | `find Snowy`                                |
| **Findtrait** a cat by trait(s)   | `find [CAT_TRAIT]...`                                                          | `findtrait white small`                     |
| **Clear** all cats                | `clear`                                                                        | `clear`                                     |
| **List** all cats                 | `list`                                                                         | `list`                                      |
| **Help**                          | `help`                                                                         | `help`                                      |
| **Exit**                          | `exit`                                                                         | `exit`                                      |
