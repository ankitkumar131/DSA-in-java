# Day 17 — Hashing

## 🎯 Learning Objectives

By the end of today, you should be able to:

- Explain hashing, hash functions, collisions, and buckets
- Use `HashMap`, `HashSet`, `LinkedHashMap`, `TreeMap`
- Pick the right map for the situation
- Solve frequency problems, two sum, group anagrams
- Know the average-case complexity and the worst case

---

# 1. Introduction

Hashing is the most-used technique in interviews. It converts a key into an integer "bucket index" so we can store and look up in average O(1).

---

# 2. Why Do We Need This?

- **Lookup by key**: O(1) average instead of O(n) (array) or O(log n) (BST).
- **Frequency counting**: trivial with `HashMap`.
- **Deduplication**: `HashSet`.

---

# 3. Core Concept — Hash Function and Bucket

```
key → hash(key) → bucket index → store/retrieve entry
```

A **collision** happens when two keys hash to the same bucket. Java's `HashMap` handles collisions with **separate chaining**: each bucket holds a linked list (or tree if too long).

---

# 4. Real-World Analogy

A library's card catalogue: each book has a Dewey-decimal "hash" telling you exactly which shelf it's on. Two books might end up on the same shelf; the librarian keeps a sub-list.

---

# 5. Java Implementation — `HashingDemo.java`

```java
import java.util.*;

public class HashingDemo {

    /** Two Sum using HashMap. */
    static int[] twoSum(int[] a, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            int need = target - a[i];
            if (seen.containsKey(need)) return new int[]{seen.get(need), i};
            seen.put(a[i], i);
        }
        return new int[]{-1, -1};
    }

    /** Frequency count. */
    static Map<Integer, Integer> frequency(int[] a) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int x : a) m.merge(x, 1, Integer::sum);
        return m;
    }

    /** Group anagrams. */
    static List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> m = new HashMap<>();
        for (String w : words) {
            char[] c = w.toCharArray();
            Arrays.sort(c);
            String key = new String(c);
            m.computeIfAbsent(key, k -> new ArrayList<>()).add(w);
        }
        return new ArrayList<>(m.values());
    }

    /** Longest consecutive sequence. */
    static int longestConsecutive(int[] a) {
        Set<Integer> s = new HashSet<>();
        for (int x : a) s.add(x);
        int best = 0;
        for (int x : s) {
            if (!s.contains(x - 1)) {
                int len = 1, cur = x;
                while (s.contains(cur + 1)) { cur++; len++; }
                best = Math.max(best, len);
            }
        }
        return best;
    }

    /** Subarray sum equals k (with negatives). */
    static int subarraySum(int[] a, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(0, 1);
        int sum = 0, count = 0;
        for (int x : a) {
            sum += x;
            count += m.getOrDefault(sum - k, 0);
            m.merge(sum, 1, Integer::sum);
        }
        return count;
    }

    /** Demo of map variants. */
    static void mapVariants() {
        // HashMap:    no order guarantees.
        Map<String, Integer> hash = new HashMap<>();
        // LinkedHashMap: insertion-order.
        Map<String, Integer> linked = new LinkedHashMap<>();
        // TreeMap:    keys sorted by natural order.
        Map<String, Integer> tree = new TreeMap<>();

        for (String k : new String[]{"c", "a", "b"}) {
            hash.put(k, 1); linked.put(k, 1); tree.put(k, 1);
        }
        System.out.println("HashMap        : " + hash.keySet());
        System.out.println("LinkedHashMap  : " + linked.keySet());
        System.out.println("TreeMap        : " + tree.keySet());
    }

    public static void main(String[] args) {
        System.out.println("twoSum         = " + Arrays.toString(twoSum(new int[]{2,7,11,15}, 9)));
        System.out.println("frequency      = " + frequency(new int[]{1,1,2,3,3,3}));
        System.out.println("groupAnagrams  = " + groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println("longestConsec  = " + longestConsecutive(new int[]{100,4,200,1,3,2}));
        System.out.println("subarraySum    = " + subarraySum(new int[]{1,2,3}, 3));
        mapVariants();
    }
}
```

Walkthrough:

- `twoSum`: for each `a[i]`, check if `target - a[i]` is in the map. If yes, return its index and `i`. Otherwise store `a[i]`.
- `frequency`: `merge` is concise for incrementing counts.
- `groupAnagrams`: sorting the chars gives a canonical key; anagrams share the key.
- `longestConsecutive`: only start counting from elements without a predecessor to avoid redundant work.
- `subarraySum`: prefix sum + map of counts (Day 9 revisited).
- `mapVariants`: HashMap is unordered, LinkedHashMap preserves insertion order, TreeMap sorts keys.

---

# 6. Java Map Hierarchy

```
Map (interface)
├── HashMap            O(1) average, no order
│   └── LinkedHashMap  O(1), insertion order
├── TreeMap            O(log n), sorted keys
├── Hashtable          legacy, synchronised
└── ConcurrentHashMap  thread-safe

Set (interface)
├── HashSet            backed by HashMap
├── LinkedHashSet      insertion order
└── TreeSet            sorted
```

---

# 7. Complexity

| Operation        | HashMap avg | HashMap worst | TreeMap |
|------------------|------------:|--------------:|--------:|
| put / get / remove | O(1)      | O(n)*         | O(log n) |
| contains         | O(1)        | O(n)          | O(log n) |
| iterate          | O(n)        | O(n)          | O(n)    |

`*` worst case is rare; in Java 8+ it becomes O(log n) when a bucket's list exceeds 8 entries.

---

# 8. Common Mistakes

1. **Mutable keys in a HashMap** — mutating the key makes it unfindable. Use immutable keys.
2. **HashMap not thread-safe** — use `ConcurrentHashMap` for concurrency.
3. **`HashMap.get(null)` works but only one null key allowed**.
4. **Initial capacity too small** — lots of resizing; pre-size if you know.

---

# 9. Interview Questions

### Q1. How does HashMap work internally?
Array of buckets. Each key is hashed to a bucket. Collisions chain (LinkedList, then Red-Black tree if list > 8).

### Q2. Why is HashMap O(1) average?
Hash function distributes keys uniformly; load factor keeps bucket size small.

### Q3. HashMap vs TreeMap?
HashMap: O(1) but unordered. TreeMap: O(log n) but sorted.

### Q4. What does `equals` and `hashCode` need to satisfy?
If `a.equals(b)`, then `a.hashCode() == b.hashCode()`. Override both together.

---

# 10. Practice Problems

## 🟢 Easy

### 1. Two Sum
**Input:** `[2,7,11,15], t=9` → **Output:** `[0,1]`

### 2. Contains Duplicate
**Input:** `[1,2,3,1]` → **Output:** `true`

### 3. Intersection of Two Arrays
**Input:** `[1,2,2,1]`, `[2,2]` → **Output:** `[2]`

### 4. First Unique Character
**Input:** `"leetcode"` → **Output:** `0`

### 5. Valid Anagram
**Input:** `"anagram", "nagaram"` → **Output:** `true`

## 🟡 Medium

### 6. Group Anagrams
**Input:** `["eat","tea","tan","ate","nat","bat"]` → grouped.

### 7. Top K Frequent
**Input:** `[1,1,1,2,2,3], k=2` → **Output:** `[1,2]`

### 8. Longest Consecutive Sequence
**Input:** `[100,4,200,1,3,2]` → **Output:** `4`

### 9. Subarray Sum Equals K
**Input:** `[1,1,1], k=2` → **Output:** `2`

### 10. 4Sum II (count tuples)
**Input:** `A=[1,2], B=[-2,-1], C=[-1,2], D=[0,2]` → **Output:** `2`

## 🔴 Hard

### 11. LRU Cache
Full implementation.

### 12. Insert Delete GetRandom O(1)
**Input:** multiset supporting insert, remove, getRandom.

### 13. Word Pattern II (backtracking + map)

### 14. Smallest Window with All Chars
**Input:** `s="ADOBECODEBANC", t="ABC"` → **Output:** `"BANC"`

### 15. Substring with Concatenation of All Words

---

# 11. Practice Hints

## Easy
1. HashMap value → index.
2. HashSet.
3. HashSet intersection.
4. Frequency map.
5. Char counts.

## Medium
6. Sorted key.
7. Frequency map + heap or bucket.
8. HashSet, only start from "no predecessor".
9. Prefix sum + map.
10. A+B sum → C+D lookup.

## Hard
11. HashMap + DLL.
12. HashMap + ArrayList.
13. Map of pattern → string.
14. Sliding window + freq.
15. Word-indexed window.

---

# 12. Revision Checklist

- [ ] Can explain hashing and collisions
- [ ] Can use HashMap / HashSet fluently
- [ ] Can pick the right map variant
- [ ] Solved 5 Easy + 5 Medium + 5 Hard

---

# 13. Key Takeaways

- HashMap / HashSet give O(1) average.
- Override `equals` + `hashCode` together.
- LinkedHashMap = insertion order; TreeMap = sorted.
- Always know the worst case: O(n).

Tomorrow: **Binary Trees**.
