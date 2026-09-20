# Day 8 — Sliding Window

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Recognise sliding-window problems
- Implement fixed-size windows
- Implement variable-size windows (expand/shrink)
- Solve max-sum subarray of size K, longest substring without repeats, min-size subarray sum
- Use a frequency map inside the window

---

# 1. Introduction

Sliding window is two pointers with a different name when used on **contiguous** subarrays/substrings. It's the right tool whenever the problem says "contiguous" and asks for a min/max over all subarrays satisfying a constraint.

---

# 2. Why Do We Need This?

Brute force on contiguous subarrays: enumerate all O(n²) subarrays, check each in O(n) ⇒ O(n³). Sliding window does it in O(n).

---

# 3. Core Concept

Maintain a window `[left, right]` (inclusive). Expand `right` to add elements. When the constraint breaks, shrink from `left`.

Two flavours:
- **Fixed size**: window length = K. Slide by 1 each step.
- **Variable size**: expand to add; shrink to restore validity. Track best answer.

---

# 4. Real-World Analogy

Imagine you're looking at a strip of film. You hold a frame (the window) and slide it across the film to find the prettiest 1-second clip.

---

# 5. Templates

### Fixed-size

```java
int sum = 0;
for (int r = 0; r < k; r++) sum += a[r];
int best = sum;
for (int r = k; r < a.length; r++) {
    sum += a[r] - a[r - k];
    best = Math.max(best, sum);
}
```

### Variable-size

```java
int left = 0; long sum = 0;
for (int right = 0; right < a.length; right++) {
    sum += a[right];
    while (/* window invalid */) sum -= a[left++];
    best = Math.max(best, right - left + 1);
}
```

---

# 6. Java Implementation — `SlidingWindowDemo.java`

```java
import java.util.*;

public class SlidingWindowDemo {

    /** Fixed: max sum of any subarray of size k. */
    static int maxSumK(int[] a, int k) {
        int sum = 0;
        for (int r = 0; r < k; r++) sum += a[r];
        int best = sum;
        for (int r = k; r < a.length; r++) {
            sum += a[r] - a[r - k];
            best = Math.max(best, sum);
        }
        return best;
    }

    /** Variable: longest substring without repeating chars. */
    static int longestUnique(String s) {
        int[] last = new int[256];
        Arrays.fill(last, -1);
        int best = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (last[s.charAt(i)] >= start) start = last[s.charAt(i)] + 1;
            last[s.charAt(i)] = i;
            best = Math.max(best, i - start + 1);
        }
        return best;
    }

    /** Variable: min length subarray with sum >= target. */
    static int minSubArrayLen(int target, int[] a) {
        int lo = 0, sum = 0, best = Integer.MAX_VALUE;
        for (int hi = 0; hi < a.length; hi++) {
            sum += a[hi];
            while (sum >= target) {
                best = Math.min(best, hi - lo + 1);
                sum -= a[lo++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    /** Frequency-based: minimum window substring. */
    static String minWindow(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int missing = t.length();
        int lo = 0, bestLo = 0, bestHi = Integer.MAX_VALUE;
        for (int hi = 0; hi < s.length(); hi++) {
            if (need[s.charAt(hi)]-- > 0) missing--;
            while (missing == 0) {
                if (hi - lo < bestHi - bestLo) { bestLo = lo; bestHi = hi; }
                if (need[s.charAt(lo)]++ == 0) missing++;
                lo++;
            }
        }
        return bestHi == Integer.MAX_VALUE ? "" : s.substring(bestLo, bestHi + 1);
    }

    /** Fixed: contains all anagram matches of p in s. */
    static List<Integer> findAnagrams(String s, String p) {
        int[] need = new int[26], have = new int[26];
        for (char c : p.toCharArray()) need[c - 'a']++;
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            have[s.charAt(i) - 'a']++;
            if (i >= p.length()) have[s.charAt(i - p.length()) - 'a']--;
            if (i >= p.length() - 1 && Arrays.equals(need, have)) out.add(i - p.length() + 1);
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("maxSumK size 3 = " + maxSumK(new int[]{2,1,5,1,3,2}, 3));
        System.out.println("longestUnique(abcabcbb) = " + longestUnique("abcabcbb"));
        System.out.println("minSubArrayLen(7) = " + minSubArrayLen(7, new int[]{2,3,1,2,4,3}));
        System.out.println("minWindow(ADOBECODEBANC, ABC) = " + minWindow("ADOBECODEBANC", "ABC"));
        System.out.println("findAnagrams(cbaebabacd, abc) = " + findAnagrams("cbaebabacd", "abc"));
    }
}
```

Walkthrough:

- `maxSumK`: build initial window sum, slide: add new right, drop old left.
- `longestUnique`: `last[c]` stores most recent index of char `c`. When char reappears inside window, jump `start` past the previous occurrence.
- `minSubArrayLen`: expand `hi`; while sum ≥ target, try shrinking from `lo`.
- `minWindow`: standard two-pointer with frequency needed.
- `findAnagrams`: fixed window of length `p.length()`; compare 26-slot frequency arrays each step.

---

# 7. Dry Run — `longestUnique("abcabcbb")`

| i | c | last[c] | start | window | best |
|---|---|---------|-------|--------|------|
| 0 | a | 0       | 0     | [0,0]  | 1    |
| 1 | b | 1       | 0     | [0,1]  | 2    |
| 2 | c | 2       | 0     | [0,2]  | 3    |
| 3 | a | 3       | 1 (since last[a]=0 ≥ 0) | [1,3] | 3 |
| 4 | b | 4       | 2     | [2,4]  | 3    |
| 5 | c | 5       | 3     | [3,5]  | 3    |
| 6 | b | 6       | 5     | [5,6]  | 2    |
| 7 | b | 7       | 7     | [7,7]  | 1    |

Result: **3**.

---

# 8. When to Use Sliding Window

| Signal | Use |
|---|---|
| Contiguous subarray/substring | Sliding window |
| Max/min over all windows of size K | Fixed window |
| Longest/shortest satisfying a constraint | Variable window |
| Anagram / permutation match | Fixed window + freq map |
| Min/max window containing all chars of T | Variable + freq map |

---

# 9. Common Mistakes

1. **Off-by-one in window boundaries**.
2. **Not shrinking** when the constraint breaks.
3. **O(n²) inside the shrink loop** — usually shrink should be O(1) amortised.
4. **Wrong initial window size** for fixed-size problems.

---

# 10. Interview Questions

### Q1. Sliding window vs two pointers?
Sliding window is a specialisation of two pointers on contiguous ranges.

### Q2. Why is the inner while-loop amortised O(1)?
Each element enters the window once (when `hi` expands) and leaves once (when `lo` shrinks). Total O(n).

### Q3. When do you need a HashMap inside the window?
When the constraint is about character counts (e.g. anagrams, distinct chars).

---

# 11. Practice Problems

## 🟢 Easy

### 1. Max Sum Subarray of Size K
**Input:** `[2,1,5,1,3,2], k=3` → **Output:** `9`

### 2. Average of Subarray of Size K
**Input:** `[1,12,-5,-6,50,3], k=4` → **Output:** `[12.75, 10.5, 12.5]`

### 3. Contains Duplicate II (within K distance)
**Input:** `nums=[1,2,3,1], k=3` → **Output:** `true`

### 4. Maximum in Sliding Window
**Input:** `[1,3,-1,-3,5,3,6,7], k=3` → **Output:** `[3,3,5,5,6,7]`

### 5. Number of Subarrays of Size K with Avg ≥ Threshold
**Input:** `[2,2,2,2,5,5,5,8], k=3, threshold=4` → **Output:** `3`

## 🟡 Medium

### 6. Longest Substring Without Repeating Chars
**Input:** `"abcabcbb"` → **Output:** `3`

### 7. Longest Repeating Character Replacement
**Input:** `"AABABBA", k=1` → **Output:** `4`

### 8. Permutation in String
**Input:** `s="cbaebabacd", p="abc"` → **Output:** `[0,6]`

### 9. Minimum Size Subarray Sum
**Input:** `target=7, [2,3,1,2,4,3]` → **Output:** `2`

### 10. Fruit Into Baskets
**Input:** `[1,2,1]` → **Output:** `3`

## 🔴 Hard

### 11. Minimum Window Substring
**Input:** `s="ADOBECODEBANC", t="ABC"` → **Output:** `"BANC"`

### 12. Sliding Window Maximum (Deque)
**Input:** `[1,3,-1,-3,5,3,6,7], k=3` → **Output:** `[3,3,5,5,6,7]`

### 13. Substring with Concatenation of All Words
**Input:** `s="barfoothefoobarman", words=["foo","bar"]` → **Output:** `[0,9]`

### 14. Minimum Number of Flips to Make Binary String Alternating
**Input:** `"111000"` → **Output:** `2`

### 15. Longest Substring with At Most K Distinct
**Input:** `"eceba", k=2` → **Output:** `3` ("ece")

---

# 12. Practice Hints

## Easy
1. Initial window sum, slide.
2. Sum then divide.
3. HashMap of value → index.
4. Deque of indices.
5. Sum window / k vs threshold.

## Medium
6. Last-index array.
7. windowLen − maxCount ≤ k.
8. Sliding freq.
9. Expand/shrink.
10. Two distinct max.

## Hard
11. Two-pointer + freq.
12. Monotonic deque.
13. HashMap word count + window.
14. Sliding-window count of mismatches.
15. HashMap of char counts, shrink when > k distinct.

---

# 13. Revision Checklist

- [ ] Recognise sliding-window problems
- [ ] Can do fixed-size window in O(n)
- [ ] Can do variable window with frequency map
- [ ] Solved 5 Easy + 5 Medium + 5 Hard

---

# 14. Key Takeaways

- Sliding window = two pointers on contiguous ranges.
- Fixed size: window length = K.
- Variable size: shrink when invalid, expand when valid.
- Frequency map inside window handles character-count constraints.

Tomorrow: **Prefix Sum**.
