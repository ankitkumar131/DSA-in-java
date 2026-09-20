"""Verify every .java file under src/ and practical/ is structurally sound.

This is a pure-Python checker (no JDK needed). It asserts each file:

1. Has at least one `class <Name>` or `public class <Name>` declaration.
2. The class name on disk matches the filename (e.g. Foo.java -> class Foo).
3. Has a `public static void main(String[] args)` method on at least one class.
4. Has balanced braces `{ }`, parens `( )`, and brackets `[ ]`
   (excluding string literals, char literals and comments — all proper escape
   handling).
5. Has no `package` declaration (course convention).

Usage:
    python3 verify_java.py
"""
import os
import re
import sys

ROOT = os.path.dirname(os.path.abspath(__file__))
DIRS = (os.path.join(ROOT, "src"), os.path.join(ROOT, "practical"))

errors: list[str] = []
checked = 0


def balance_check(src: str) -> tuple[bool, str]:
    """Return (ok, message). ok=False if unmatched delimiters found."""
    pairs = {')': '(', '}': '{', ']': '['}
    stack: list[tuple[str, int]] = []
    i = 0
    n = len(src)
    line = 1
    while i < n:
        c = src[i]
        if c == '\n':
            line += 1
        if c == '/' and i + 1 < n and src[i + 1] == '/':
            while i < n and src[i] != '\n':
                if src[i] == '\n':
                    line += 1
                i += 1
            continue
        if c == '/' and i + 1 < n and src[i + 1] == '*':
            i += 2
            while i + 1 < n and not (src[i] == '*' and src[i + 1] == '/'):
                if src[i] == '\n':
                    line += 1
                i += 1
            i += 2
            continue
        if c == '"':
            i += 1
            while i < n and src[i] != '"':
                if src[i] == '\\' and i + 1 < n:
                    i += 2
                    if i - 1 < n and src[i - 1] == '\n':
                        line += 1
                    continue
                if src[i] == '\n':
                    line += 1
                i += 1
            if i < n:
                i += 1
            continue
        if c == "'":
            i += 1
            while i < n and src[i] != "'":
                if src[i] == '\\' and i + 1 < n:
                    i += 2
                    continue
                if src[i] == '\n':
                    line += 1
                i += 1
            if i < n:
                i += 1
            continue
        if c in '({[':
            stack.append((c, line))
        elif c in ')}]':
            if not stack:
                return False, f"unmatched {c} at line {line}"
            o, _ln = stack.pop()
            if o != pairs[c]:
                return False, f"mismatch {o} vs {c} at line {line}"
        i += 1
    if stack:
        return False, f"unclosed delimiters: {len(stack)} remaining (last: {stack[-1]})"
    return True, ""


def check_file(path: str) -> list[str]:
    issues: list[str] = []
    with open(path, encoding='utf-8', errors='replace') as f:
        src = f.read()
    base = os.path.basename(path)
    cls_re = re.compile(r'\b(?:public\s+)?(?:final\s+|abstract\s+|static\s+|strictfp\s+)*class\s+(\w+)')
    classes = cls_re.findall(src)
    if not classes:
        issues.append(f"{base}: no class declaration found")
        return issues
    expected = base[:-5]
    if expected not in classes:
        issues.append(
            f"{base}: class name should be '{expected}', found: {classes[:5]}"
        )
    if re.search(r'^\s*package\s+[\w.]+\s*;', src, re.M):
        issues.append(f"{base}: contains 'package' declaration (course convention is no-package)")
    main_re = re.compile(r'public\s+static\s+void\s+main\s*\(\s*String\s*\[\s*\]\s*\w+\s*\)')
    if not main_re.search(src):
        issues.append(f"{base}: no 'public static void main(String[] args)' found")
    ok, msg = balance_check(src)
    if not ok:
        issues.append(f"{base}: {msg}")
    return issues


for d in DIRS:
    if not os.path.isdir(d):
        continue
    for root, _dirs, files in os.walk(d):
        for fn in sorted(files):
            if not fn.endswith('.java'):
                continue
            p = os.path.join(root, fn)
            checked += 1
            for iss in check_file(p):
                errors.append(iss)

print(f"checked {checked} java files")
if errors:
    print(f"\nFAILURES ({len(errors)}):")
    for e in errors:
        print("  *", e)
    sys.exit(1)
print("All files OK.")
