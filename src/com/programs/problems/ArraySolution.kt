package com.programs.problems

import java.util.*
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max


fun main() {
    print(lengthOfLongestSubstring(" "))
}

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 */
fun lengthOfLongestSubstring(s: String): Int {
    val map = hashMapOf<Char,Int>()
    var i = 0
    var j =0
    var max = 0;
    s.forEach{

        if(map.containsKey(it)){
            j = max(j,map[it]!!)
        }
        map[it] = ++i
        max = max(max, i-j)
    }
    return max
}

/**
 * https://leetcode.com/problems/asteroid-collision/
 */
fun asteroidCollision(asteroids: IntArray): IntArray {
    val st = Stack<Int>()

    for(i in asteroids){
        if(i>0){
            st.push(i)
            continue
        }

        while(!st.isEmpty() && st.peek()>0 && st.peek()< abs(i)){
            st.pop();
        }
        if(st.isEmpty() || st.peek()<0){
            st.push(i)
        }
        if(st.peek()== -i){
            st.pop()
        }
    }

    var arr = IntArray(st.size)
    var i = arr.size-1
    while(!st.isEmpty()){
        arr[i--] = st.pop()
    }
    return arr
}

/**
 * Minimum swap required to sort an array of N elements
 * https://www.geeksforgeeks.org/minimum-number-swaps-required-sort-array/
 */
fun minSwapToSortAyyar(arr:IntArray):Int{
    var swap = 0
    val visited = BooleanArray(arr.size)

    for(i in arr.indices){
        if(arr[i]==i-1 || visited[i]) {
            visited[i] = true
            continue
        }
        var cycle = 0
        var j = i
        while( !visited[j]){
            visited[j] = true
            j = arr[j]-1
            cycle++
        }
        if(cycle!=0){
            swap += cycle-1
        }

    }


    return swap
}

/**
 * https://leetcode.com/problems/contiguous-array/
 */
fun findMaxLength(nums: IntArray): Int {
    val map = hashMapOf<Int, Int>()
    var count = 0
    var max = 0
    map[0] = -1
    for (i in nums.indices) {
        if (nums[i] === 0) count-- else count++
        if (map.containsKey(count)) {
            max = max(max, i - map[count]!!)
        } else {
            map[count] = i
        }
    }
    return max
}

/**
 * https://www.geeksforgeeks.org/minimum-swaps-required-bring-elements-less-equal-k-together/
 */
fun getMinSwap(array:IntArray, k:Int ):Int{
     var swap = 0
     var count = 0
     array.forEach {
         if (it <= k) {
             count++
         }
     }

    for(i in 0 until count){
        if(array[i]>k) swap++
    }

    var ans = swap
    var i = 0
    for(j in count until array.size){
         if(array[i]>k) ans--
         if(array[j]>k)ans++
         i++
        swap = min(swap, ans)
    }

    return swap
}
/**
 * https://www.geeksforgeeks.org/smallest-difference-pair-values-two-unsorted-arrays/
 * Sort both array and increase pointer of array which element is smaller
 * Time complexity: Sorting O(NLogN+MLogM) then comparison is O(N+M)
 */
fun minDiff(first: IntArray, second: IntArray): Int {
    first.sort()
    second.sort()
    var i = 0
    var j = 0
    var diff = Int.MAX_VALUE
    while (i < first.size && j < second.size) {
        if (abs(first[i] - second[j]) < diff) {
            diff = abs(first[i] - second[j])
        }
        if (first[i] < second[j]) {
            i++
        } else {
            j++
        }
    }

    return diff
}

/**
 * https://leetcode.com/problems/set-matrix-zeroes/
 * Time - > O(MN(M+N)), Space -> O(1)
 * Check [zeroMatrix] for better time complexity
 */
fun setZero(arr: Array<IntArray>) {

    val def = -10000000
    for (i in arr.indices) {
        for (j in arr[i].indices) {

            if (arr[i][j] == 0) {
                for (k in arr[i].indices) {
                    if (arr[i][k] != 0) {
                        arr[i][k] = def
                    }
                }

                for (k in arr.indices) {
                    if (arr[k][j] != 0) {
                        arr[k][j] = def
                    }
                }
            }
        }
    }


    for (i in arr.indices) {
        for (j in arr[i].indices) {
            if (arr[i][j] == def) {
                arr[i][j] = 0
            }
        }

    }

}

/**
 * https://leetcode.com/problems/set-matrix-zeroes/
 * Time O(MN), Space O(M+N)
 * Check [setZero] for better space complexity
 */
fun zeroMatrix(arr: Array<IntArray>) {

    val rows = BooleanArray(arr.size)
    val cols = BooleanArray(arr[0].size)

    for (i in arr.indices) {
        for (j in arr[i].indices) {
            if (arr[i][j] == 0) {
                rows[i] = true
                cols[j] = true
            }
        }
    }

    for (i in rows.indices) {
        if (rows[i]) {
            setRowsZero(i, arr)
        }
    }

    for (j in cols.indices) {
        if (cols[j]) {
            setColsZero(j, arr)
        }
    }
}

fun setColsZero(j: Int, arr: Array<IntArray>) {
    for (i in arr.indices) {
        arr[i][j] = 0
    }

}

fun setRowsZero(i: Int, arr: Array<IntArray>) {
    for (j in arr[i].indices) {
        arr[i][j] = 0
    }

}

/**
 * https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/
 *
 */
fun minDominoRotations(first: IntArray, second: IntArray): Int {

    if (first.size != second.size) return -1
    // keep count of element if element is duplicated at same index in both array we consider it as 1
    val total = IntArray(7)
    // keep count of each element in first array
    val f = IntArray(7)
    // keep count of each element in second array
    val s = IntArray(7)
    // have kept size of each array to 1 to avoid doing -1 ,
    for (i in first.indices) {
        // if both array has same element in same index , then increment by 1 only
        if (first[i] == second[i]) {
            total[first[i]] = total[first[i]] + 1
        } else {
            total[first[i]] = total[first[i]] + 1
            total[second[i]] = total[second[i]] + 1
        }
        f[first[i]] = f[first[i]] + 1
        s[second[i]] = s[second[i]] + 1

    }
    var min = -1
    // iterate over total array and find minimum if some element count is equal to original array size
    for (i in 1..6) {
        if (total[i] == first.size) {
            // if oen of the array has same element at all index then return 0
            if (f[i] == first.size || s[i] == first.size) return 0
            // find diff otherwise
            val q = Math.min(total[i] - f[i], total[i] - s[i])
            if (min == -1 || min > q) min = q
        }
    }
    return min
}

