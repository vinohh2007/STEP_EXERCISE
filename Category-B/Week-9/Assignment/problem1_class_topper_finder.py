def findTopper(marks):
    best_row, best_total = 0, -1
    for i in range(len(marks)):
        total = 0
        for score in marks[i]:
            total += score
        if total > best_total:        # strictly greater -> smallest index wins ties
            best_total = total
            best_row = i
    return (best_row, best_total)

# Time complexity : O(m * n)  (m students, n subjects)
# Extra space     : O(1)

if __name__ == "__main__":
    marks = [[78, 85, 90], [88, 92, 79], [65, 70, 95]]
    print(findTopper(marks))  # (1, 259)
