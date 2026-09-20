# DSA in Java — 30-Day Complete Course

A self-contained 30-day course that takes you from "I know some Java" to "I can solve any medium-hard DSA interview problem in Java". Every day has lessons, example code, 15 graded practice problems with full solutions, and a tiny standalone project.

> One repo. 30 days. 450+ runnable Java programs. No external services, no accounts, no internet required.

---

## What's Inside

| Artifact | Where | Count |
| --- | --- | --- |
| Day-by-day lessons (`.md`) | `DSA-Java-30-Days/01..11-.../day-NN-*.md` | 30 |
| Example code (`.java`) | `src/day-NN/*.java` | ≈ 90 |
| Practice solutions (fenced inside each day `.md`) | end of every day lesson | 445 entries |
| Tiny standalone projects | `practical/day-NN/<Project>.java` + `README.md` | 30 pairs |
| 450-question bank with solutions | `DSA-Java-30-Days/14-Question-Bank/450-questions.md` | 450 problems |
| Cheat-sheets | `DSA-Java-30-Days/12-Cheat-Sheets/` | 4 |
| Algorithm patterns | `DSA-Java-30-Days/13-Patterns/` | 12 |
| Roadmap / how to study / error log | `DSA-Java-30-Days/00-Roadmap/` | 5 |
| Progress tracker | `DSA-Java-30-Days/PROGRESS.md` | 1 |

---

## Course Map

```
Day 01–02  → Java Foundations + OOP
Day 03–04  → Complexity Analysis + Math
Day 05–09  → Arrays, Strings, Two-Pointers, Sliding Window, Prefix Sum
Day 10–11  → Searching, Sorting
Day 12–14  → Recursion, Linked Lists
Day 15–17  → Stacks, Queues, Hashing
Day 18–21  → Trees, BST, Heaps
Day 22–23  → Greedy, Backtracking
Day 24–26  → Graphs (BFS/DFS/Shortest Paths/MST)
Day 27–29  → Dynamic Programming (3 tiers)
Day 30     → Mixed Revision + Interview Prep
```

See **[INDEX.md](INDEX.md)** for the full per-day clickable table of contents.

---

## Quick Start

You need **only** the JDK (Java 11+) — no Maven, no Gradle, no internet.

```bash
# run any example
javac src/day-01/HelloWorld.java && java -cp src/day-01 HelloWorld

# run any practical project
javac practical/day-05/StockKadane.java && java -cp practical/day-05 StockKadane
```

All `.java` files use **no package declaration** so they always compile standalone from their own directory.

### No-JDK structure check

If you don't have `javac` handy, run a structural sanity check on every Java file:

```bash
python3 verify_java.py
```

It confirms each `.java` file has matching braces, a `public class <Name>` matching
the filename, a `main(String[] args)` and contains no `package` declaration.

---

## Recommended Study Loop

1. Open the day's lesson `.md` from top to bottom. Skim the examples.
2. Open every `.java` in `src/day-NN/`, compile, run, modify the input, break it, fix it.
3. Solve the 5 Easy problems by hand (no peeking). Then Medium. Then Hard.
4. Build the matching `practical/day-NN/<Project>.java` for the day.
5. Append your notes to `DSA-Java-30-Days/PROGRESS.md`.

Full study method: `DSA-Java-30-Days/00-Roadmap/how-to-study.md`.

---

## Repo Layout

```
.
├── README.md                                   ← you are here
├── INDEX.md                                    ← per-day TOC
├── DSA-Java-30-Days/                           ← course content
│   ├── 00-Roadmap/                             ← meta, study plan, error log
│   ├── 01-Java-Foundations/
│   │   ├── day-01-java-dsa-foundations.md
│   │   └── day-02-oops-java.md
│   ├── 02-Complexity-and-Problem-Solving/
│   ├── 03-Arrays-and-Strings/                  ← days 05–09
│   ├── 04-Searching-and-Sorting/               ← days 10–11
│   ├── 05-Recursion-and-Linked-Lists/          ← days 12–14
│   ├── 06-Stacks-Queues-Hashing/               ← days 15–17
│   ├── 07-Trees-and-Heaps/                     ← days 18–21
│   ├── 08-Greedy-and-Backtracking/             ← days 22–23
│   ├── 09-Graphs/                              ← days 24–26
│   ├── 10-Dynamic-Programming/                 ← days 27–29
│   ├── 11-Final-Revision/
│   │   └── day-30-final-revision-and-interview-preparation.md
│   ├── 12-Cheat-Sheets/                        ← 4 quick-reference files
│   ├── 13-Patterns/                            ← 12 canonical DSA patterns
│   ├── 14-Question-Bank/450-questions.md       ← 450 problems + Solutions
│   └── PROGRESS.md                             ← tick-off checklist
├── src/day-NN/                                  ← example Java sources per day
└── practical/day-NN/                           ← tiny standalone per-day project
```

---

## How Problems are Graded

Every practice question has a difficulty tag and a known source:

| Tag | Meaning | Source examples |
| --- | --- | --- |
| 🟢 Easy | Direct application of one concept | Warm-ups, basics |
| 🟡 Medium | Combines 2+ concepts / needs a trick | Interview staples |
| 🔴 Hard | Multi-step, edge cases, design trade-offs | Top-tier interview / contest |

Distribution per day (except Day 30 which has 10): **5 Easy + 5 Medium + 5 Hard = 15**.

---

## License & Use

MIT-style: copy, fork, remix, build your own course from this one. The only rule is — write the code yourself before you read it.
