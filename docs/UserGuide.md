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

<!-- TOC -->
  * [Getting Started](#getting-started)
  * [Features](#features)
    * [Viewing help : `help`](#viewing-help--help)
    * [Adding a cat: `add`](#adding-a-cat-add)
    * [Listing all cats: `list`](#listing-all-cats-list)
    * [Updating a cat profile : `update`](#updating-a-cat-profile--update)
    * [Locating cats by name, location, traits or health status : `find`](#locating-cats-by-name-location-traits-or-health-status--find)
    * [Deleting a cat : `delete`](#deleting-a-cat--delete)
    * [Clearing all entries : `clear`](#clearing-all-entries--clear)
    * [Exiting the program : `exit`](#exiting-the-program--exit)
    * [Saving the data](#saving-the-data)
    * [Editing the data file](#editing-the-data-file)
    * [Archiving data files `[coming in v2.0]`](#archiving-data-files-coming-in-v20)
  * [FAQ](#faq)
  * [Known issues](#known-issues)
  * [Command summary](#command-summary)
<!-- TOC -->

---

## Getting Started
If you have never used a **Terminal** or **Command Prompt** before, don't worry! This guide will walk you through the process of getting the app running.

---
**Step 1: Check Your Java Version**

Before starting, your computer needs a specific version of **Java** (think of it as the engine) to run this app.

* **On Windows:** Click your **Start** menu, type `cmd`, and press **Enter**. A black window will open. Type `java -version` and press **Enter**.
* **On Mac:** Press **Command + Space**, type `Terminal`, and press **Enter**. Type `java -version` and press **Enter**.
* Result: You should see something like `java version "17.0.1"`. If you see a version number less than 17, or an error message, you will need to install Java 17 or above.

---

**Step 2: Download the App and prepare CatPals folder**
1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-T16-3/tp/releases/tag/branch-release1.0).
2.  **Create a new folder** on your Desktop or in your Documents and name it `CatPals`or any other name you prefer.
3.  **Move** the downloaded file into this new folder.

---

**Step 3: Open the Terminal in Your Folder**
Instead of clicking an icon, we need to tell the computer to "look" inside your `CatPals` folder using text.
 
**For Windows Users:**
1.  Open your folder where the jar file is located.
2.  Click the **Address Bar** at the top of the window (the long white bar that shows the folder path).
3.  Delete everything in that bar, type `cmd`, and press **Enter**.
4.  A black window will pop up that is already "inside" your folder.

**For Mac Users:**
1.  Find your `CatPals` folder icon.
2.  **Right-click** (or Control-click) the folder icon.
3.  Select **New Terminal at Folder** from the menu.

---

**Step 4: Run the Application**
Now that the terminal window is open, simply type the following command exactly as it appears and press **Enter**:

`java -jar catpals.jar`

> **Note:** If the file you downloaded has a different name (like `catpals-v1.2.jar`), make sure you type that exact name after the `-jar` part!

**Step 5: Start Using CatPals!**
If the app runs successfully, you should see a window pop up with a list of cats and a command box at the bottom. You can start typing commands to manage your cat profiles. For example, try typing `help` and pressing Enter to see the help message.

Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/Bowie t/Orange l/Utown h/Vaccinated` : Adds a cat named `Bowie` to the cat notebook.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.
   * `exit` : Exits the app. 
Refer to the [Features](#features) below for details of each command.

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


### Adding a cat: `add`

Adds a cat profile to the cat notebook.

Format: `add n/NAME t/TRAIT [t/MORE_TRAITS]… l/LOCATION [h/HEALTH_STATUS]`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A cat can have up to 3 traits (no duplicates). Health status is optional and defaults to `Unknown` if not provided.
</div>

* `n/NAME`, `t/TRAIT`, and `l/LOCATION` are required.
* `h/HEALTH_STATUS` is optional.
* You can specify up to 3 `t/TRAIT` prefixes, but duplicate traits are not allowed.

Examples:
* `add n/Bowie t/Orange l/Utown h/Vaccinated`
* `add n/Whiskers t/Fluffy t/Playful l/Science`

### Listing all cats: `list`

Shows a list of all cats in the app.

Format: `list`

### Updating a cat profile : `update`

Updates an existing cat in the app.

Format:`update INDEX n/NAME t/TRAIT [t/MORE_TRAITS]… l/LOCATION [h/HEALTH_STATUS]`
or 
`update CURRENT_NAME n/NAME t/TRAIT [t/MORE_TRAITS]… l/LOCATION [h/HEALTH_STATUS]`

* Updates the cat at the specified `INDEX` or `EXISTING_NAME`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
* Existing values will be updated to the input values.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

* `update 1 n/Brownie t/Brown` Updates the name and trait of the 1st cat to be `Brownie` and `Brown` respectively.
* `update Bronwie n/Brownie t/Brown` Updates the name of the cat with current name `Bronwie` to be `Brownie` and its trait to be `Brown`.

### Locating cats by name, location, traits or health status : `find`

The `find` command is a powerful tool to quickly filter the CatPals database. You can now search for cats based on their **Name**, **Location**, **Traits**, or **Health Status**.

Format: `find n/CAT_NAME`, `find l/LOCATION`, `find t/TRAIT`, or `find h/HEALTH_STATUS`

**How it works:**
* The search is case-sensitive. e.g `snowy` will **NOT** match `Snowy`
* The order of the keywords **do not** matter. e.g. `Snowy White` will match `White Snowy`
* Cats matching at least one keyword will be returned (i.e. `OR` search).
  * e.g. `find n/Hans n/Bo` will return `Hans Gruber`, `Bo Yang`
* Search terms cannot contain symbols

**Examples:**

| Category      | Prefix | Example Command | Expected Result                                |
|---------------|--------|-----------------|------------------------------------------------|
| Name          | n/     | find n/Snowy    | Shows cats with the word `Snowy` in their name |
| Location      | l/     | find l/COM3     | Shows all cats that are at `COM3`              |
| Traits        | t/     | find t/Striped  | Shows all cats that have the trait `Striped`   |
| Health Status | h/     | find h/healthy  | Shows all cats that are tagged as `healthy`     |

**Advanced Examples:**

* **Searching for multiple traits:** `find t/Friendly t/Playful`
  Matches any cat that is marked as either Friendly OR Playful.
* **Finding names with multiple keywords:**`find n/Alex n/Li`
  Matches cats named Alex as well as cats with the last name Li (e.g., Jet Li).
* **Combining categories:** `find n/Snowy t/Fluffy`
  Matches any cat that has the name Snowy OR is tagged as Fluffy.
* **Searching by partial location:**`find l/Arts`
  Matches cats at Arts Canteen, Arts Plaza, etc.

>[!IMPORTANT]
> Ensure there is no space between the prefix (like n/) and your search word. For example, use n/Snowy, not n/ Snowy.



### Deleting a cat : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:

* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the app.

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

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME t/TRAIT [t/MORE_TRAITS]… l/LOCATION [h/HEALTH_STATUS]` <br> e.g., `add n/Bowie t/Orange l/Utown h/Vaccinated`
**Clear** | `clear`
**Delete** | `delete INDEX(or NAME)`<br> e.g., `delete 3` or `delete Brown`
**Update** | `update INDEX(or NAME) [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`update 2 n/James Lee e/jameslee@example.com` or `update Brown n/Bigguy t/Purple`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
