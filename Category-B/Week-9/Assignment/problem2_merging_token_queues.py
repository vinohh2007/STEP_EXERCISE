def mergeTokens(counterA, counterB):
    i = j = 0
    merged = []
    while i < len(counterA) and j < len(counterB):
        if counterA[i] <= counterB[j]:
            merged.append(counterA[i])
            i += 1
        else:
            merged.append(counterB[j])
            j += 1
    merged.extend(counterA[i:])   # leftovers from whichever list remains
    merged.extend(counterB[j:])
    return merged

# Time complexity : O(m + n)
# Space complexity: O(m + n) for the output list
# Join + sort     : O((m + n) log(m + n)) time - slower, because it ignores
#                   the fact that both lists are already sorted.

if __name__ == "__main__":
    print(mergeTokens([3, 8, 15, 20], [5, 8, 12]))  # [3, 5, 8, 8, 12, 15, 20]
    print(mergeTokens([], [4, 9]))                  # [4, 9]
