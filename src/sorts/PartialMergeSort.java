package sorts;

import templates.Sort;
import utils.Delays;
import utils.Highlights;
import utils.Reads;
import utils.Writes;

/*
 * 
MIT License

Copyright (c) 2020 Gaming32

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
 *
 */

final public class PartialMergeSort extends Sort {
    public PartialMergeSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Partial Merge");
        this.setRunAllID("Partial Merge Sort");
        this.setReportSortID("Partial Mergesort");
        this.setCategory("Merge Sorts");
        this.isComparisonBased(true);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.isBogoSort(false);
    }

    private void merge(int[] array, int leftStart, int rightStart, int end) {
        int[] copied = new int[rightStart - leftStart];
        for (int i = 0; i < copied.length; i++) {
            Highlights.markArray(1, i + leftStart);
            Writes.write(copied, i, array[i + leftStart], 1, false, true);
        }

        int left = leftStart;
        int right = rightStart;
        for(int nxt = 0; nxt < end - leftStart; nxt++){
            if(left >= rightStart && right >= end) break;

            Highlights.markArray(1, nxt + leftStart);
            Highlights.markArray(2, right);

            if(left < rightStart && right >= end){
                Highlights.clearMark(2);
                Writes.write(array, nxt + leftStart, copied[(left++) - leftStart], 1, false, false);
            }
            else if(left >= rightStart && right < end){
                Highlights.clearMark(1);
                Writes.write(array, nxt + leftStart, array[right++], 1, false, false);
            }
            else if(Reads.compare(copied[left - leftStart], array[right]) <= 0){
                Writes.write(array, nxt + leftStart, copied[(left++) - leftStart], 1, false, false);
            }
            else{
                Writes.write(array, nxt + leftStart, array[right++], 1, false, false);
            }
        }

        Highlights.clearAllMarks();
    }

    private void mergeRun(int[] array, int start, int mid, int end) {
        if(start == mid) return;

        mergeRun(array, start, (mid+start)/2, mid);
        mergeRun(array, mid, (mid+end)/2, end);

        merge(array, start, mid, end);
    }
    
    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        int start = 0;
        int end = length;
        int mid = start + ((end - start) / 2);
        
        mergeRun(array, start, mid, end);
    }
}