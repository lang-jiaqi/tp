![banner](docs/images/CatPals_banner.png)
[![CI Status](https://github.com/AY2526S2-CS2103T-T16-3/tp/workflows/Java%20CI/badge.svg)](https://github.com/AY2526S2-CS2103T-T16-3/tp/actions)
[![codecov](https://codecov.io/gh/se-edu/addressbook-level3/branch/master/graph/badge.svg)](https://codecov.io/gh/se-edu/addressbook-level3)
![Java](https://img.shields.io/badge/Java-17-informational)
[![Made with Jekyll](https://img.shields.io/badge/Jekyll-3.9-blue?logo=jekyll&logoColor=white)](https://jekyllrb.com "Go to Jekyll homepage")
![GitHub Created At](https://img.shields.io/github/created-at/lang-jiaqi/tp?color=...)
![contributors](https://img.shields.io/badge/contributors-5-brightgreen)
![license](https://img.shields.io/github/license/lang-jiaqi/tp.svg?color=bright-green)
![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg)

![Ui](docs/images/Ui.png)

## Table of Contents

- [Background](#background)
- [Install](#install)
- [Usage](#usage)
- [Contributing](#contributing)
- [Acknowledgements](#acknowledgements)
- [License](#license)

## Background

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

## Install
1. To successfully use our application, it is best to ensure that you are using **Java 17**.
Check your current Java version in terminal by running:
```bash
java -version
```
2. Download the latest version of catpals.jar from our repository.
3. Put the jar file in an empty folder to avoid being affected by other files in the folder.
4. Navigate to the folder in your terminal and run the following command:
```bash
java -jar catpals.jar
```

## Usage

Type a command in the command box and press **Enter** to execute it.

### Command Summary

| Command                   | Format                                                                               | Example                                                 |
|---------------------------|--------------------------------------------------------------------------------------|---------------------------------------------------------|
| **Add** a cat             | `add n/NAME t/TRAIT [t/MORE_TRAITS]… l/LOCATION [h/HEALTH_STATUS]`                   | `add n/Bowie t/Orange l/Utown h/Vaccinated`             |
| **List** all contacts     | `list`                                                                               | `list`                                                  |
| **Update** a contact      | `update INDEX(or NAME) n/NAME t/TRAIT [t/MORE_TRAITS]… l/LOCATION [h/HEALTH_STATUS]` | `update 1 n/Brown ` or `update Brown n/Bigguy t/Purple` |
| **Find** cats | `find n/CAT_NAME` or `find l/LOCATION` or `find t/TRAIT` or `find h/HEALTH_STATUS`    | `find n/Mochi` or `find t/Striped` or `find l/COM3` or `find h/Sick` |
| **Delete** a contact      | `delete INDEX(or NAME)`                                                              | `delete 3` or `delete Brown`                            |
| **Clear** all contacts    | `clear`                                                                              | `clear`                                                 |
| **Help**                  | `help`                                                                               | `help`                                                  |
| **Exit**                  | `exit`                                                                               | `exit`                                                  |

### Notes
- Parameters in `UPPER_CASE` are supplied by the user (e.g. `n/NAME` → `n/Snowy`).
- Items in `[square brackets]` are optional.
- Items followed by `…` can be used multiple times or omitted entirely.
<br>e.g. `[t/TRAIT]…` can be used as `t/white`, `t/white t/small` etc.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.
<br>e.g. if the command specifies `help 123`, it will be interpreted as `help`.

### Data Storage
Contact data is saved automatically to `[JAR location]/data/addressbook.json` after every change — no manual saving required.

## Contributing
This project was developed by a team of five as a brownfield Software Engineering project based on AddressBook Level 3 (AB3).
- [Hongshan](https://github.com/ChenHongshan333)
- [Haofeng](https://github.com/Haofeng125)
- [Jiaqi](https://github.com/lang-jiaqi)
- [Lexi](https://github.com/LexiAKAtiff)
- [Sai](https://github.com/chirlasai)

## Acknowledgements
This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

AddressBook-Level3 project is a **part of the se-education.org** initiative. If you would like to contribute code to this project, see [se-education.org](https://se-education.org/#contributing-to-se-edu) for more info.

## License

MIT License

Copyright (c) 2026 AY2526S2 CS2103T-T16-3

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
